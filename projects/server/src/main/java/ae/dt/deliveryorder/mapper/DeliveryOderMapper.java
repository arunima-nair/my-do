package ae.dt.deliveryorder.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.DeliveryOrder;
import ae.dt.deliveryorder.dto.DeliveryOrderDTO;

/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(componentModel = "spring",uses = {DoAuthRequestMapper.class}) 
public interface DeliveryOderMapper {
	 @Mapping(target="doAuthRequest", ignore=true)
	 DeliveryOrder dTOtoDoman(DeliveryOrderDTO dto);
	 @Mapping(target="doAuthRequest", ignore=true)
	 DeliveryOrderDTO domaintoDTO(DeliveryOrder destination);
}
