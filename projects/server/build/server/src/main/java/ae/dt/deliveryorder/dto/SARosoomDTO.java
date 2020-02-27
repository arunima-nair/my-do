package ae.dt.deliveryorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SARosoomDTO {
	
	private Long saRosoomId;
	
	private String currency;

	private String serviceOwnerId;
	
	private String rosoomLicenseKey;
	
	private String rosoomServiceId;
	
	private ShippingAgentDTO shippingAgent;
	
	private Date createdDate;
	private Long isValid= 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;

	private String keyStoreLoc;
	private String keyStorePassword;
	private String keyPassword;
	private String keyAlias;

}
