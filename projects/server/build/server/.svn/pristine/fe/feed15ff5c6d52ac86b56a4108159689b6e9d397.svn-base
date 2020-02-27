package ae.dt.deliveryorder.dto;

import java.util.Date;
import java.util.Set;

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
public class ShippingAgentDTO {
	
	private Long shippingAgentId;
	
	private String shippingAgentCode;
	
	private String shippingAgentName;
	
	private Set<ShippingAgentAttributesDTO> shippingAgentAttributes;
	
	private Set<SAInitiatorInvoiceTypeDTO> saInitiatorInvoiceType;
	
	private Date createdDate;
	private Long isValid= 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;

}
