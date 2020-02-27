package ae.dt.deliveryorder.controller;

import ae.dt.common.constants.Constants;
import ae.dt.common.controller.BaseController;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.PaymentDTO;
import ae.dt.deliveryorder.facade.DeliveryOrderFacade;
import ae.dt.deliveryorder.service.AuthoriseDeliveryOrderService;

import com.pcfc.dw.cepg.signature.PaymentInitSignData;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kamala.Devi on 4/10/2019.
 */
@Controller
public class PaymentController  extends BaseController {



    Logger logger = LoggerFactory.getLogger(DeliveryOrderController.class);

    @Value("${rosoom.payinit.keyStore.location}")
    private String rosoomKeyStoreLocation;
    @Value("${rosoom.payinit.keyStore.keyStorePassword}")
    private String rosoomKeyStorePassword;
    @Value("${rosoom.payinit.keyStore.keyAlias}")
    private String rosoomKeyAlias;


    @Autowired
    DeliveryOrderFacade deliveryOrderFacade;
    @Autowired
    AuthoriseDeliveryOrderService authoriseDeliveryOrderService;
    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @RequestMapping("/app/api/public/responseFromRosoomPaymentApp")
    public String responseFromRosoomPaymentApp(HttpServletRequest request) {
        String payload = "soTransactionID::" + request.getParameter("soTransactionID") + "||cepgReferenceNumber::"
                + request.getParameter("cepgReferenceNumber") + "||status::" + request.getParameter("status")
                + "||message::" + request.getParameter("message") + "||fiReferenceNumber::"
                + request.getParameter("fiReferenceNumber") + "||serviceCost::" + request.getParameter("serviceCost")
                + "||processingCharges::" + request.getParameter("processingCharges") + "||paymentInstrument::"
                + request.getParameter("paymentInstrument") + "||fiDate::" + request.getParameter("fiDate")
                + "||customerReferenceNumber::" + request.getParameter("customerReferenceNumber") + "||";
  
        String decodedStr = (String) request.getParameter("signature");
        byte[] decodedBytes = Base64.decodeBase64(decodedStr.getBytes());
        PaymentInitSignData signData = new PaymentInitSignData();
        boolean verified = false;
        String successMsg="";
        request.getParameterNames().nextElement();
        try {
            verified = signData.verify(payload, decodedBytes, rosoomKeyStoreLocation, rosoomKeyStorePassword,
                    rosoomKeyAlias);
        } catch (Exception e) {
            return "Sign Data Error ";
        }
        if (!verified) {
            // return "VERIFICATION FAILED !!";
        }
       
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setGatewayTransId(request.getParameter("cepgReferenceNumber"));
        paymentDTO.setFiDate(request.getParameter("fiDate"));
        paymentDTO.setFiTransactionId(request.getParameter("fiReferenceNumber"));
        paymentDTO.setFiInstrument(request.getParameter("paymentInstrument"));
        paymentDTO.setGatewayTransId(request.getParameter("cepgReferenceNumber"));
        paymentDTO.setRemarks(request.getParameter("message"));
        paymentDTO.setPaymentAmount(request.getParameter("serviceCost"));
        paymentDTO.setSoTransactionId(request.getParameter("soTransactionID"));
        String statusParam = request.getParameter("status");
        String custRefNo = request.getParameter("customerReferenceNumber");
        paymentDTO.setCustRefNo(custRefNo);
        ModelAndView mav = new ModelAndView("payment.confirm");
        if (statusParam.equals("1")) {
            paymentDTO.setPaymentStatus(Constants.PAY_SUCCESS);
        } else if (statusParam.equals("0")) {
            paymentDTO.setPaymentStatus(Constants.PAY_FAILED);

        }
        logger.info("-----" + custRefNo);
        UserDTO userDTO= (UserDTO) request.getAttribute("userDTO");
        //DoAuthRequest status=  deliveryOrderFacade.updateCollectionRosoom(paymentDTO, userDTO);
        DoAuthRequest status = authoriseDeliveryOrderService.updateAfterRosoomPayment(paymentDTO, userDTO);
        if (statusParam.equals("1")) {
        	if(status.getBol().getStatus().equalsIgnoreCase(Constants.DO_STATUS.PARTIAL_PAYMENT.value)) {
        		successMsg="DO request created Successfully and  it is partial payment.DO request will processed only after complete payment.The DO Reference No is "+status.getDoRefNo()+"&transactionId="+ EncryptionUtil.encrypt(paymentDTO.getSoTransactionId());
        	}else if(status.getStatus().equalsIgnoreCase(Constants.DO_STATUS.PENDING_FOR_APPROVAL.value)) {
             successMsg="DO request created Successfully and it is pending for approval.The DO Reference No is "+status.getDoRefNo()+"&transactionId="+ EncryptionUtil.encrypt(paymentDTO.getSoTransactionId());
        	}else{
        	    successMsg="DO request created Successfully with DO Reference No "+status.getDoRefNo()+"&transactionId="+ EncryptionUtil.encrypt(paymentDTO.getSoTransactionId());
            }
        	return "redirect:/confirm?success=true&data="+successMsg;
        }
        else {
            successMsg="Payment Failed!."+"&transactionId=null";
            return "redirect:/confirm?success=false&data="+successMsg;
        }
    }
}

