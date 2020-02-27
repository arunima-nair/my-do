package ae.dt.deliveryorder.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DoAuthRequestDTO {
	@JsonIgnore
	private Long doAuthRequestId;

	private String doAuthRequestIdEncr;

	private String bolContactNumber;

	private String bolContactPerson;

	private String bolEmail;

	private String bolPartyName;

	private String doContactNumber;

	private String doContactPerson;

	private String doEmail;

	private String doPartyName;

	private String reqContactNumber;

	private String reqContactPerson;

	private String reqEmail;

	private String reqPartyName;

	private String status;

	private String doRefNo;

	private Set<DoAuthDocDTO> doAuthDocs;

	private BoLDTO bol;
	@JsonIgnore
	private DeliveryOrderDTO deliveryOrder;

	private Set<PaymentLogDTO> paymentLogs;

	private Set<CollectionDTO> collections;

	private Set<ApprovalLogDTO> approvalLog;

	private Set<DocDTO> docDTO;

	private Boolean isOnline = false;

	private Boolean isPayImporter = false;

	private Boolean isPay = false;

	private Boolean choosePay = false;

	private Boolean isCredit = false;

	private Boolean isAmend = false;

	private Boolean isPayProof = false;

	private String uploadProof;

	private String authLetter;
	private String encrAuthLetterPath;

	private String bolLetter;
	private String encrBolLetterPath;

	private String othDoc;
	private String encrOthDocPath;

	private String emiratesIdCopy;
	private String encrEmiratesIdCopyPath;

	private String uploadDo;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private Boolean paid = false;

	private int version;

	private List payingInvoice;

	private Double payingAmt;

	private String partialPay;

	private double paidAmt;

	private Boolean paidStatus = false;

	private String tempCreatedDate;

	private String tempmodifiedDate;
	// private Date tempCreatedDate;
	//
	// private Date tempmodifiedDate;

	private String transactionId;

	private String approvedViewed;

	private String viewedBy;

	private Date viewedDate;

	private Long initiatorId;

	private String initiatorCode;

	private String initiatorType;

	private String initiatedBy;

	private List<String> payInvoiceList;

	private String shippingAgentCode;

	private String bolNumber;

	private Boolean isPartialPayment = false;

	private double totalAmount;

	private String refNumber;

	private String doPartyCode;

	private String doPartyType;

	private String blPartyCode;

	private String blPartyType;
	
	private String isReturned;

}
