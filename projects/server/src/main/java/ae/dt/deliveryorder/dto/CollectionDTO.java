package ae.dt.deliveryorder.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import ae.dt.deliveryorder.domain.BolInvoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ARUNIMA. NAIR on 2/14/2019.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDTO {
	@JsonIgnore
	private Long collectionId;
	
	private String encrCollectionId;

	private String amount;

	private String collectionType;

	private String invoiceNumber;
	
	private String encrInvoiceNumber;

	private String receiptNo;

	private String status;

	private String transactionId;
	
	private String encrTransactionId;

	private DoAuthRequestDTO doAuthRequest;

	private Set<CreditDTO> credits;

	private Set<PaymentOfProofDTO> paymentOfProofs;
	
	private String encrPaymentOfProofId;

	private Set<RosoomDTO> rosooms;
	
	private String createdBy;
	
	private BolInvoice bolInvoice;
	
	private Integer payStatus;
	
	private String refNumber;

}
