package com.dt.deliveryorder.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_METHOD;

@Component
public class PublishFileRoute   extends BaseRouteBuilder {

    @Value("${publish.url}")
    private String publishUrl;

    @Value("${publish.username}")
    private String username;

    @Value("${publish.password}")
    private String password;

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

        from("file:"+"{{server.to.interim.filepath}}"+"?readLock=changed&readLockMinLength=0&readLockMinAge=50s&recursive=true" +
                "&probeContentType=true&moveFailed={{server.to.error.filepath}}/${date:now:dd-MM-yyyy}/${header.CamelFileName} "+
                "&move={{server.to.publish.filepath}}/${date:now:dd-MM-yyyy}/${header.CamelFileName} ")
                .threads(5)
                .setHeader(HTTP_METHOD, simple("POST"))
                .setHeader(CONTENT_TYPE, constant("application/json"))
                .setHeader("username",constant(username))
                .setHeader("password",constant(password))
                .to(publishUrl)
        .end();
    }
}
