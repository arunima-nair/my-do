package ae.dt.deliveryorder.wsclient;


import ae.dt.deliveryorder.facade.DeliveryOrderFacade;
import ae.dt.deliveryorder.facade.DeliveryOrderFacadeImpl;
import com.pcfc.dw.cepg.ws.payinq.types.InquirePaymentStatusElement;
import com.pcfc.dw.cepg.ws.payinq.types.InquirePaymentStatusResponseElement;
import com.pcfc.dw.cepg.ws.payinq.types.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;



public class RosoomWSClient extends WebServiceGatewaySupport {

    @Autowired
    DeliveryOrderFacade deliveryOrderFacade;
//    @Value("${rosoom.licenseKey}")
//    private String rosoomLicenseKey;
//    @Value("${rosoom.serviceownerid}")
//    private String serviceOwnerId;
//    @Value("${rosoom.serviceid}")
//    private String rosoomServiceId;

    public InquirePaymentStatusResponseElement fetchTransactionStatus(String soTransactionId){

        // Create Username request against which need to featch User Information
        ObjectFactory rosoomObjectFactory = new ObjectFactory();
        InquirePaymentStatusElement inquirePaymentStatusElement = rosoomObjectFactory.createInquirePaymentStatusElement();
        inquirePaymentStatusElement.setLicenseKey("kSn8b1JYwF");
        inquirePaymentStatusElement.setServiceID("1452");
        inquirePaymentStatusElement.setServiceOwnerID("BU0000687");
        inquirePaymentStatusElement.setSoTransactionID(soTransactionId);
        return (InquirePaymentStatusResponseElement) getWebServiceTemplate().marshalSendAndReceive(inquirePaymentStatusElement);

    }

}
