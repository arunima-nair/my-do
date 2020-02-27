package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;

import ae.dt.deliveryorder.domain.SAInitiatorPayment;
import ae.dt.deliveryorder.dto.SAInitiatorPaymentDTO;



@Mapper(componentModel = "spring",uses = {ShippingAgentMapper.class,InitiatorMapper.class})
public interface SAInitiatorPaymentMapper {
	
	SAInitiatorPayment saInitiatorPaymentDTOtoDoman(SAInitiatorPaymentDTO dto);
	SAInitiatorPaymentDTO saInitiatorPaymentDomaintoDTO(SAInitiatorPayment destination);
	  
	  
}
