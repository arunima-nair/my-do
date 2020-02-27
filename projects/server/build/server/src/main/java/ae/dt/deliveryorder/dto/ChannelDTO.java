package ae.dt.deliveryorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {

	private long channelId;
   
	private String reference;
	
	private ChannelTypeDTO channelType;
	
	

}
