package ae.dt.common.config;


import ae.dt.deliveryorder.wsclient.RosoomWSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;


@Configuration
public class SoapClientConfig {

    @Autowired
    private Environment env;

    @Value("${rosoom.enquiry.url}")
    private String rsmEnqUrl;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths("com.pcfc.dw.cepg.ws.payinq.types", "com.oracle.webservices.internal.literal");
        return marshaller;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_11);
        //messageFactory.afterPropertiesSet();
        return messageFactory;
    }

    @Bean
    public RosoomWSClient rosoomWSClient() {
        RosoomWSClient client = new RosoomWSClient();
        client.setMarshaller(marshaller());
        client.setUnmarshaller(marshaller());
        client.setDefaultUri(rsmEnqUrl);
        return client;
    }

}
