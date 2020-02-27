package ae.dt.deliveryorder.processors;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ae.dt.deliveryorder.service.AuthoriseDeliveryOrderService;
import com.oracle.webservices.internal.literal.MapEntry;
import com.pcfc.dw.cepg.ws.payinq.types.InquirePaymentStatusResponseElement;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import ae.dt.common.constants.Constants;
import ae.dt.common.util.DateUtil;
import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.dto.PaymentDTO;
import ae.dt.deliveryorder.service.DeliveryOrderService;
import ae.dt.deliveryorder.wsclient.RosoomWSClient;


/**
 * Created by Kamala.Devi on 4/2/2019.
 */
@Service("rosoomEnquiryProcessor")
public class RosoomEnquiryProcessor  implements Processor {
    private static Logger logger = LoggerFactory.getLogger(RosoomEnquiryProcessor.class);
    public RosoomEnquiryProcessor() {
    }
    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Autowired
    AuthoriseDeliveryOrderService authoriseDeliveryOrderService;


    @Autowired
    private RosoomWSClient rosoomWSClient;
    @Scheduled(cron = "${enquiry.retry.time}")
    public void process() {

        logger.info("RosoomEnquiryProcessor - [process]:Start job @"+ DateUtil.getCurrentTimeAsString());

        List<PaymentLog> pendingList=deliveryOrderService.fetchRosoomPendingPayments();
        logger.debug("Fetched Rosoom Pending Transactions");
        for(PaymentLog paymentLog:pendingList){
            try {
                logger.debug("Calling Rosoom Webservice");
                InquirePaymentStatusResponseElement response = rosoomWSClient.fetchTransactionStatus(paymentLog.getTransactionId());
                List<MapEntry> respValues=  response.getResult().getMapEntry();
                PaymentDTO payment = new PaymentDTO();
                payment.setSoTransactionId(paymentLog.getTransactionId());

                String keyStr = null;
                String valueStr = null;
                for (MapEntry mapEntry : respValues) {
                    keyStr =  mapEntry.getKey().toString();
                    valueStr= mapEntry.getValue().toString();
                    if (StringUtils.equals(keyStr,
                            Constants.ROSOOM_TRANS_ID))
                        payment.setGatewayTransId(valueStr);
                    else if (StringUtils.equals(keyStr,
                            Constants.FI_DATE))
                        payment.setFiDate(valueStr);
                    else if (StringUtils.equals(keyStr,
                            Constants.PAYMENT_INSTRUMENT))
                        payment.setFiInstrument(valueStr);
                    else if (StringUtils.equals(keyStr,
                            Constants.SERVICE_COST))
                        payment.setPaymentAmount(valueStr);
                    else if (StringUtils.equals(keyStr,
                            Constants.PROC_CHARGES))
                        ;
                    else if (StringUtils.equals(keyStr,
                            Constants.AMOUNT))
                        ;
                    else if (StringUtils.equals(keyStr,
                            Constants.FI_REF_NO))
                        payment.setFiTransactionId(valueStr);
                    else if (StringUtils.equals(keyStr,
                            Constants.FI_MESSAGE))
                        payment.setRemarks(valueStr);
                    else if (StringUtils.equals(keyStr,
                            Constants.STATUS)) {
                        String statusParam = valueStr;
                        if (StringUtils.equals(statusParam, "1")) {
                            payment.setPaymentStatus(Constants.PAY_SUCCESS);
                        } else if (StringUtils.equals(statusParam, "0")
                                || StringUtils.equals(statusParam, "-3")
                                || StringUtils.equals(statusParam, "-4")) {
                            payment.setPaymentStatus(Constants.PAY_FAILED);
                        } else {
                            payment.setPaymentStatus(Constants.PAY_PENDING);
                        }
                    }

                }

                if(payment.getPaymentStatus().equals(Constants.PAY_FAILED) || payment.getPaymentStatus().equals(Constants.PAY_SUCCESS)){
                    logger.info("RosoomEnquiryProcessor - [process]:updating payment status for Delog id ######"+paymentLog.getPaymentLogId() +" to status "+ payment.getPaymentStatus());

                    authoriseDeliveryOrderService.updateAfterRosoomPayment(payment,null);
                }
                logger.debug("Updated Rosoom Pending Transaction");
            }catch (Exception ex){
                logger.error("",ex);
                logger.error("RosoomEnquiryProcessor - [process]:An Error occurred while processing job for transaction id: "+paymentLog.getTransactionId() +" "+ex.getMessage());
            }
        }


    }

}
