package ae.dt.deliveryorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class BolChannelDTO {

	private Long channelId;
   
	private String reference;

	private ChannelTypeDTO channelType;
	
	private String createdBy;
	
	private BoLDTO bol;
	
	private Date createdDate;
	private Long isValid= 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;
}
