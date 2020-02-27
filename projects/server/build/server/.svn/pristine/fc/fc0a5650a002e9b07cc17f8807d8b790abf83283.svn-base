package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.dto.BoLDetailDTO;

@Mapper(componentModel = "spring")
public interface BolDetailsMapper {
	 @Mapping(target = "bol", ignore = true)
	BolDetail boldetailDTOtoDoman(BoLDetailDTO dto);
	 @Mapping(target = "bol", ignore = true)
	BoLDetailDTO boldetailDomaintoDTO(BolDetail destination);


}
