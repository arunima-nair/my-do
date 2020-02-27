package com.dt.deliveryorder.processor;

import com.dt.deliveryorder.exception.CamelExceptionUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ExceptionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        CamelExceptionUtils.handleException(exchange);
    }
}
