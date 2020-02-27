package ae.dt.deliveryorder.mapper;


import org.mapstruct.Mapper;

import ae.dt.common.domain.DTAgentView;
import ae.dt.common.dto.DTAgentViewDTO;

/**
 * Created by Arunima on 3/14/2019.
 */
@Mapper 
public interface DTAgentViewMapper {
	 
	DTAgentView dTOtoDoman(DTAgentViewDTO dto);
	
	DTAgentViewDTO domaintoDTO(DTAgentView destination);
	
	 
}
