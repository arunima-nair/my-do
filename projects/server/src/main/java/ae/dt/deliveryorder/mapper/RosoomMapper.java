package ae.dt.deliveryorder.mapper;


import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.Rosoom;
import ae.dt.deliveryorder.dto.RosoomDTO;

/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper 
public interface RosoomMapper {
	  @Mapping(target = "collection", ignore = true)
	Rosoom rosoomdTOtoDoman(RosoomDTO dto);
	  @Mapping(target = "collection", ignore = true)
	RosoomDTO rosoomdomaintoDTO(Rosoom destination);
	
	 
}
