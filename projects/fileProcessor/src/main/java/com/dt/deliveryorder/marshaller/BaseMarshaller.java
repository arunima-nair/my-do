package com.dt.deliveryorder.marshaller;

import com.dt.deliveryorder.util.FileMappingUtil;
import com.dt.deliveryorder.util.InpFileType;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Map;

import static com.dt.deliveryorder.util.Utils.getRandomNumber;

public abstract class BaseMarshaller implements DataFormat {



    protected  String getAgentCode(Exchange exchange) {
        return (String)exchange.getIn().getHeader("agentCode");
    }
    protected InpFileType getFileType(Exchange exchange){
        return (InpFileType) exchange.getIn().getHeader("FILE_TYPE");
    }
    protected File getFileFromExchange(Exchange exchange) {
        return exchange.getIn().getBody(File.class);
    }

    protected void updateFileNameInHeader(Exchange exchange) {
        String outPutFileName = getRandomNumber()+".txt";
        exchange.getIn().setHeader("IntFileName", outPutFileName);
    }

    protected String getProcessedFilePath(Exchange exchange){
        return (String) exchange.getIn().getHeader("ProcessedFilePath");
    }

    protected String getUploadType(Exchange exchange){
        return (String) exchange.getIn().getHeader("channel");
    }

    protected String getShippingAgentCode(Exchange exchange){
        return (String) exchange.getIn().getHeader("agentCode");
    }


}
