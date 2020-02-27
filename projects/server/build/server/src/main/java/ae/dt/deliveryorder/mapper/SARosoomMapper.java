package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.SARosoom;
import ae.dt.deliveryorder.dto.SARosoomDTO;



@Mapper(componentModel = "spring",uses = {ShippingAgentMapper.class})
public interface SARosoomMapper {
	@Mapping(target = "shippingAgent", ignore = true)
	SARosoom saRosoomDTOtoDoman(SARosoomDTO dto);
	@Mapping(target = "shippingAgent", ignore = true)
	SARosoomDTO saRosoomDomaintoDTO(SARosoom destination);
	  
	  
}
