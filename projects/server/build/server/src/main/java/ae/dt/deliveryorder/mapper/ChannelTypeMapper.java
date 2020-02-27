package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;

import ae.dt.deliveryorder.domain.ChannelType;
import ae.dt.deliveryorder.dto.ChannelTypeDTO;

@Mapper 
public interface ChannelTypeMapper {
	
	  ChannelType channelTypeDTOtoDoman(ChannelTypeDTO dto);
	  ChannelTypeDTO channelTypeDomaintoDTO(ChannelType destination);
	  
	  
}
