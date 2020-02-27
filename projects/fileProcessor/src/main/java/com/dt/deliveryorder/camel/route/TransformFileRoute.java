package com.dt.deliveryorder.camel.route;

import com.dt.deliveryorder.dto.ErrorDTO;
import com.dt.deliveryorder.exception.CamelExceptionUtils;
import com.dt.deliveryorder.marshaller.EDIFileMarshaller;
import com.dt.deliveryorder.marshaller.ExcelFileMarshaller;
import com.dt.deliveryorder.processor.FileTypeIdentifier;
import com.dt.deliveryorder.processor.TxtFileProcessor;
import com.dt.deliveryorder.util.InpFileType;
import com.dt.deliveryorder.util.Utils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static com.dt.deliveryorder.util.Utils.*;
import static com.dt.deliveryorder.util.Utils.getRandomNumber;
import static java.io.File.pathSeparator;
import static java.io.File.separator;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

@Component
public class TransformFileRoute extends BaseRouteBuilder {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    FileTypeIdentifier fileTypeIdentifier;

    @Autowired
    TxtFileProcessor txtFileProcessor;

    @Autowired
    ExcelFileMarshaller excelFileMarshaller;

    @Autowired
    EDIFileMarshaller ediFileMarshaller;

    @Value("${server.to.interim.filepath}")
    private String interimFilePath;

    @Value("${server.to.processed.filepath}")
    private String processedPath;

    @Value("${redelivery.attemps}")
    private Integer redeliveryAttempt;

    @Value("${redelivery.duration.ms}")
    private Integer redeliveryDelay;



    @Override
    public void configure() throws Exception {

        final String  FILE_TYPE="FILE_TYPE";
        final String  DIRECT_PROCESS_DATA="direct:processData";
        super.configure();
        from("file:"+"{{server.from.filepath}}"+"?readLock=changed&readLockMinLength=0&readLockMinAge=30s&recursive=true" +
                "&move={{server.to.processed.filepath}}/${date:now:dd-MM-yyyy}/${header.newFileName}" +
                "&moveFailed={{server.to.error.filepath}}/${date:now:dd-MM-yyyy}/${header.newFileName}&antExclude=**/*.zip,**/*.pdf")
                .threads(5)
                .process(fileTypeIdentifier)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String randomTxt = getRandomNumber();
                        String channel=  splitPreserveAllTokens((String)exchange.getIn().getHeader("CamelFileRelativePath"), getFileSystemPathSeperator())[0];
                        String agentCode=  splitPreserveAllTokens((String)exchange.getIn().getHeader("CamelFileRelativePath"), getFileSystemPathSeperator())[1];
                        String originalFileName= splitPreserveAllTokens((String)exchange.getIn().getHeader("CamelFileRelativePath"), getFileSystemPathSeperator())[2];
                        String generatedFileName = randomTxt+"_"+originalFileName;
                        String newFileName=channel+getFileSystemPathSeperator()+agentCode+getFileSystemPathSeperator()+ generatedFileName;
                        exchange.getIn().setHeader("channel", channel);
                        exchange.getIn().setHeader("agentCode", agentCode);
                        exchange.getIn().setHeader("actualFileName", originalFileName);
                        exchange.getIn().setHeader("IntFileName", randomTxt+".txt");
                        exchange.getIn().setHeader("newFileName",newFileName );
                        exchange.getIn().setHeader("generatedFileName", generatedFileName);
                        exchange.getIn().setHeader("ProcessedFilePath", processedPath+ getTodayDate() + getFileSystemPathSeperator()
                                                                                +channel+getFileSystemPathSeperator()+agentCode
                                                                                +getFileSystemPathSeperator()+ randomTxt+"_"+originalFileName);
                        String destFolder = interimFilePath+ getTodayDate()+ getFileSystemPathSeperator()+channel+ getFileSystemPathSeperator()+agentCode;
                        if (destFolder != null)
                             throw new RuntimeException(" HELLO ");
                        exchange.getIn().setHeader("destFolder",destFolder);
                    }
                })
                .choice()
                    .when(header(FILE_TYPE).isEqualTo(InpFileType.TXT))
                        .unmarshal(ediFileMarshaller).to(DIRECT_PROCESS_DATA)
                    .when(header(FILE_TYPE).isEqualTo(InpFileType.EDI))
                         .unmarshal(ediFileMarshaller).to(DIRECT_PROCESS_DATA)
                    .when(header(FILE_TYPE).isEqualTo(InpFileType.XLS ))
                        .unmarshal(excelFileMarshaller).to(DIRECT_PROCESS_DATA)
                    .when(header(FILE_TYPE).isEqualTo(InpFileType.XLSX))
                        .unmarshal(excelFileMarshaller).to(DIRECT_PROCESS_DATA)
                    .when(header(FILE_TYPE).isEqualTo(InpFileType.CSV))
                        .unmarshal(excelFileMarshaller).to(DIRECT_PROCESS_DATA)
        .end();


        from(DIRECT_PROCESS_DATA)
                .recipientList(simple("file:${header.destFolder}/?fileName=${header.IntFileName}"))
//                .to("file:"+"{{server.to.interim.filepath}}/?fileName=${header.IntFileName}")
                .log(" Finished Transformation")
        .end();
    }
}
