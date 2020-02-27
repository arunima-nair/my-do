package ae.dt.deliveryorder.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.ShippingAgent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLogDTO {
	@JsonIgnore
	private Long paymentLogId;

	private Double amount;

	private String status;
	
	private String transactionId;
	
	private String encrTransactionId;
	
	private DoAuthRequestDTO doAuthRequest;

	private String createdBy;
	
	private String paidBy;
	
	private BolInvoice bolInvoice;
	
	private Integer payStatus;
	
	private ShippingAgent shippingAgent;
	
	private String invoiceNumber;
	
	private Long initiatorId;
	
	private String currency;
	
	private String initiatedBy;

}
