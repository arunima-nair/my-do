package com.dt.deliveryorder.camel.route;

import com.dt.deliveryorder.exception.CamelExceptionUtils;
import com.dt.deliveryorder.exception.RecoverableException;
import com.dt.deliveryorder.processor.ExceptionProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.springframework.beans.factory.annotation.Value;

public  abstract class BaseRouteBuilder extends RouteBuilder {

//    private String errorPath;
//    private String errorPostURL;

    @Value("${server.to.error.filepath}")
    private String errorPath;

    @Value("${error.publish.url}")
    private String errorPostURL;

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }



    public void setErrorPostURL(String errorPostURL) {
        this.errorPostURL = errorPostURL;
    }

    @Override
    public void configure() throws Exception {
        ModelCamelContext camelContext= this.getContext();
        camelContext.getGlobalOptions().put("FILE_ERROR_PATH",this.errorPath);
        camelContext.getGlobalOptions().put("FILE_ERROR_POST_URL",this.errorPostURL);
        onException(RecoverableException.class)
                .maximumRedeliveries(3)
                .backOffMultiplier(2)
                .redeliveryDelay(25000)
                .process(new ExceptionProcessor());

        onException(Exception.class)
                .maximumRedeliveries(1)
                .backOffMultiplier(1)
                .redeliveryDelay(15000)
                .process(new ExceptionProcessor());
    }
}
