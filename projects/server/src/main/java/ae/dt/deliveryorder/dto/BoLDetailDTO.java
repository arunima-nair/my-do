package ae.dt.deliveryorder.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import ae.dt.deliveryorder.domain.Bol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoLDetailDTO {

	private String importerCode;

	private String shippingAgentCode;

	private String shippingAgentName;

	private Date vesselEta;

	private String vesselName;

	private String voyageNumber;

	private BigDecimal channelId;

	private String createdBy;
	
	private BoLDTO bol;

	private Date vesselAta;
	//private BolChannelDTO bolChannelDTO;

	private String consigneeName;
	
	private String containerCount;
	
	private Long bolDetailsId;

	private Date createdDate;
	private Long isValid;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;

}
