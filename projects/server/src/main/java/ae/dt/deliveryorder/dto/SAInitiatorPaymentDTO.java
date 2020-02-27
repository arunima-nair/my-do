package ae.dt.deliveryorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties
@Data
@Builder
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class SAInitiatorPaymentDTO {
	
	private Long saInitiatorPaymentId;
	
	private ShippingAgentDTO shippingAgent;
	
	private InitiatorDTO initiator;
	
	private String paymentAllowed;
	
	private Date createdDate;
	private Long isValid= 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;

}
