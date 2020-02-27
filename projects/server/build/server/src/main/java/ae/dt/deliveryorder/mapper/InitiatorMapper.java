package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;

import ae.dt.deliveryorder.domain.Initiator;
import ae.dt.deliveryorder.dto.InitiatorDTO;

@Mapper 
public interface InitiatorMapper {
	
	Initiator initiatorDTOtoDoman(InitiatorDTO dto);
	InitiatorDTO InitiatorDomaintoDTO(Initiator destination);
	  
	  
}
