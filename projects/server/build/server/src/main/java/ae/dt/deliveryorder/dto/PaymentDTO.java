package ae.dt.deliveryorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

/**
 * Created by Kamala.Devi on 2/13/2019.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private static final long serialVersionUID = 1L;

    private String fiDate;
    private String fiTransactionId;
    private String fiInstrument;
    private String paymentAmount;
    private String paymentStatus;
    private String gatewayTransId;
    private String remarks;
    private String soTransactionId;
    private Long passId;
    private Long paymentId;
    private Long paymentLogId;
    private String paymentMode;
    private String subStatus;
    private Date subStartDate;
    private Date subEndDate;
    private String billingMethod;
    private String billingPeriod;
    private String paymentDate;
    private String paymentTransId;
    private String fiName;
    private String chequeNumber;
    private String chequeDate;
    private String bankName;
    private String nextDueDate;
    private Long collectionId;
    private String collectionType;
    private String passFee;
    private String paidBy;
    private Double paidAmount;
    private String startDateStr;
    private String endDateStr;
    private Date payDate;
    private String productId;
    private String cityName;
    private String prodStartDate;
    private String paidByName;
    private String paymentType;
    private String totalAmount;
    private String totalVatAmount;
    private String createdBy;
    private String custRefNo;
}
