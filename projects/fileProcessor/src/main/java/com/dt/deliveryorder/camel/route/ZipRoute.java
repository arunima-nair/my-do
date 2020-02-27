package com.dt.deliveryorder.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.ZipFileDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ZipRoute  extends BaseRouteBuilder {

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

        ZipFileDataFormat zipFile = new ZipFileDataFormat();
        zipFile.setUsingIterator(true);



        from("file:"+"{{server.from.filepath}}"+"?readLock=changed&readLockMinLength=0&readLockMinAge=10s&recursive=true" +
                "&probeContentType=true&move={{server.to.processed.filepath}}/${date:now:dd-MM-yyyy}/${header.CamelFileName} " +
                "&moveFailed={{server.to.error.filepath}}/${date:now:dd-MM-yyyy}/${header.CamelFileName}&include=.*.zip")
                .threads(5)
                .unmarshal(zipFile)
                .split(bodyAs(Iterator.class))
                .streaming()
                .recipientList(simple("file:${header.CamelFileParent}/"))
        .end();
    }
}
