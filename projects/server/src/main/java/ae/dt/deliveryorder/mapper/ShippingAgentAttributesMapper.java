package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.ShippingAgentAttributes;
import ae.dt.deliveryorder.dto.ShippingAgentAttributesDTO;

@Mapper(componentModel = "spring",uses = {ShippingAgentMapper.class})

public interface ShippingAgentAttributesMapper {
	@Mapping(target = "shippingAgent", ignore = true)
	ShippingAgentAttributes shippingAgentAttributesDTOtoDoman(ShippingAgentAttributesDTO dto);
	@Mapping(target = "shippingAgent", ignore = true)
	ShippingAgentAttributesDTO shippingAgentAttributesDomaintoDTO(ShippingAgentAttributes destination);
	  
	  
}
