package ae.dt.common.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.DocumentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kamala.Devi on 2/19/2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewDTO<T> {

	private String bolNumber;
	private String bolType;
	private String bolTypeDisplayName;
	private String bolStatus;
	private String bolCreatedDate;

	/* BOL DETAIL */
	private String importerCode;

	private String shippingAgentCode;

	private String shippingAgentName;

	private Date vesselEtaAta;

	private String vesselName;

	private String voyageNumber;
	@JsonIgnore
	private Long bolDetailsId;

	/* BOL INVOICE */
	@JsonIgnore
	private Long bolInvoiceId;

	private Date invoiceDate;

	private String invoiceNumber;

	private Double invoiceValue;

	private Date invoiceValidityDate;

	private String uploadDoc;

	private String path;

	private String createdBy;

	private String status;
	@JsonIgnore
	private Long bolId;

	private String bolNumberEncr;

	private String currency;

	/* DoRequest */
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

	private String dostatus;

	private Boolean isPay;

	private Boolean isCredit;

	private String authLetter;

	private String bolLetter;

	private String emiratesIdCopy;

	private String payMethod;

	private String doRefNo;

	private List payingInvoice;

	private Integer payingAmt;

	private String partialPay;

	private String blPartyCode;

	private String doPartyCode;

	private String doCreatedDate;
	
	private String doCreatedBy;

	private Date doAuthCreatedDate;

	private String doPartyType;

	private String blPartyType;

	private String isReturned;
	
	private String reqType;
	
	private String reqCode;

	/* DOauthDocs */
	@JsonIgnore
	private Long doAuthDocsId;
	private String encrDoAuthDocsId;

	private String documentPath;

	private DocumentDTO documentBean;
	/* DeliveryOrder */
	@JsonIgnore
	private Long deliveryOrderId;
	private BolInvoiceDTO bolInvoice;

	private Date approvedDate;
	private String approvedBy;

	/* Collection */
	private String transactionId;
	private String amount;
	private String paymentStatus;

	/* Rosoom */
	private String fiInstrument;

	private Date createdDate;
	private String convertedDate;
	private String formatedCreatedDate;
	private Long isValid = 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;

	private int size = 10;
	private int totalPages;
	private long totalElements;
	private int pageNumber;

	private int version;
	private boolean pathFlag;

	private String[] action;

	private String rejectedRemark;

	private String returnedRemark;
}