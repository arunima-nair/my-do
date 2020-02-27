package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.Credit;
import ae.dt.deliveryorder.dto.CreditDTO;

@Mapper(uses = {CollectionsMapper.class})
public interface CreditMapper {
	
	  Credit creditdTOtoDoman(CreditDTO dto);
	  @Mapping(target = "collection", ignore = true)
	  CreditDTO creditdomaintoDTO(Credit destination);
	 
	 
}
