package com.dt.deliveryorder.camel.route;

import com.dt.deliveryorder.exception.RecoverableException;
import com.dt.deliveryorder.util.FileMappingUtil;
import com.dt.deliveryorder.util.Utils;
import lombok.Data;
import lombok.ToString;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.dt.deliveryorder.util.Utils.getFileSystemPathSeperator;
import static com.dt.deliveryorder.util.Utils.getRandomNumber;
import static java.io.File.separator;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

@Component
public class PDFFileRoute  extends BaseRouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(PDFFileRoute.class);
    @Value("${server.to.pdf.filepath}")
    private String invoiceDestFolder;

    @Autowired
    FileMappingUtil fileMappingUtil;



    @Value("${invoice.publish.url}")
    private String invoicePublishEndPoint;

    @Value("${redelivery.attemps}")
    private Integer redeliveryAttempt;

    @Value("${redelivery.duration.ms}")
    private Integer redeliveryDelay;

    @Value("${server.to.error.filepath}")
    private String errorPath;

    @Value("${error.publish.url}")
    private String errorPostURL;

    @Override
    public void configure() throws Exception {

        super.configure();

        from("file:"+"{{server.from.filepath}}"+"?readLock=changed&readLockMinLength=0&readLockMinAge=45s&recursive=true" +
                "&probeContentType=true&move={{server.to.processed.filepath}}/${date:now:dd-MM-yyyy}/${header.CamelFileName} " +
                "&moveFailed={{server.to.error.filepath}}/${date:now:dd-MM-yyyy}/${header.CamelFileName}&include=.*.pdf")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        //GetLineCode from source FOlder
                        String randomTxt = getRandomNumber();
                        String channel=  splitPreserveAllTokens((String)exchange.getIn().getHeader("CamelFileRelativePath"), getFileSystemPathSeperator())[0];
                        String agentCode=  splitPreserveAllTokens((String)exchange.getIn().getHeader("CamelFileRelativePath"), getFileSystemPathSeperator())[1];
                        String actualFileName=splitPreserveAllTokens((String)exchange.getIn().getHeader("CamelFileRelativePath"), getFileSystemPathSeperator())[2];
                        String newFileName=randomTxt+"_"+actualFileName;
                        exchange.getIn().setHeader("channel", channel);
                        exchange.getIn().setHeader("agentCode", agentCode);
                        exchange.getIn().setHeader("actualFileName", actualFileName);
                        String destFolder = invoiceDestFolder +  Utils.getTodayDate() + getFileSystemPathSeperator() + agentCode;
                        exchange.getIn().setHeader("destFolder", destFolder);
                        exchange.getIn().setHeader("pdfFileName", newFileName);
                        RestTemplate restTemplate = new RestTemplate();
                        PDFDTO pdfdto = new PDFDTO();
                       Map lineConf =   fileMappingUtil.getLineMap(agentCode);
                       String invoiceNumber = null;

                       String originalFileName = StringUtils.remove(actualFileName,".pdf");
                        if (lineConf.get("pdfFileNamingStructure") != null) {
                            Map pdfConf = (Map) lineConf.get("pdfFileNamingStructure");
                            String seperator =  (String)pdfConf.get("seperator");
                            String position = pdfConf.get("invpos") !=null ? (String)pdfConf.get("invpos"): "0";
                            String bolPos = (String)pdfConf.get("bolpos");
                            if (!isEmpty(bolPos))
                                pdfdto.setBolNumber(splitPreserveAllTokens(originalFileName,seperator)[Integer.parseInt(bolPos)]);
                            invoiceNumber=  splitPreserveAllTokens(originalFileName,seperator)[Integer.parseInt(position)];
                        }
                        pdfdto.setInvoiceNumber(invoiceNumber);
                        pdfdto.setShippingAgentCode(agentCode);
                        pdfdto.setPath(destFolder+ getFileSystemPathSeperator()+newFileName);
                        logger.debug("-----WS PDF INVOKE with ----- "+ pdfdto.toString());
                        try {
                            restTemplate.postForObject(invoicePublishEndPoint, pdfdto, Object.class);
                        } catch (Exception ex){
                            logger.error(" --- ERROR invoking PDF publish Service ",ex);
                            throw new RecoverableException(ex.getMessage(), ex);
                        }
                    }
                })
        .recipientList(simple("file:${header.destFolder}/?fileName=${header.pdfFileName}"))
        .end();

    }

    @Data
    @ToString
    private class PDFDTO {
        private String path;
        private String shippingAgentCode;
        private String invoiceNumber;
        private String bolNumber;
    }
}
