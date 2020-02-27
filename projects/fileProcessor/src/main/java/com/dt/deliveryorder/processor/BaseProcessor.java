package com.dt.deliveryorder.processor;

import com.dt.deliveryorder.util.InpFileType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public abstract class BaseProcessor implements Processor {

    protected InpFileType getFileType(Exchange exchange){
        return (InpFileType) exchange.getIn().getHeader("FILE_TYPE");
    }
}
