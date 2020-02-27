package ae.dt.deliveryorder.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class BolInvoiceDTO {
	//@JsonIgnore
	private Long bolInvoiceId;
	
	private String encrBolInvoiceId;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date invoiceDate;
	
	private String invoiceNumber;
	
	private String encrInvoiceNumber;
	
	private Double invoiceValue;

	private String createdBy;
	
	private String path;
	
	private BoLDTO bol;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date invoiceValidityDate;
		
	private InvoiceTypeDTO invoiceType;
	
	private Set<PaymentLogDTO> paymentLogs;

	private  Set<CollectionDTO> collections;
	
	private String currency;
	
	private String paidStatus;
	
	private Date createdDate;
	private Long isValid;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;


	//forr file processing
//	private String invoiceNumber;
	private String invoiceTypeStr;
	private String invoiceIssueDate;
	private String invoiceDueDate;
	private String base64Invoice;
	
	private String fileName;

	private String status;
}
