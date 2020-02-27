package ae.dt.deliveryorder.dto;

import java.math.BigDecimal;

import org.mapstruct.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class PaymentOfProofDTO {
	private Long paymentOfProofId;
	
	private String encrPaymentOfProofId;

	private BigDecimal paymentAmount;

	private String reference;
	
	private String refNumber;
	
	private String encrReference;
	
	private String transactionId;
	 
	private CollectionDTO collection;

	private String createdBy;
}
