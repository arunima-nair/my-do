package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;
import ae.dt.deliveryorder.dto.SAInitiatorInvoiceTypeDTO;

@Mapper(componentModel = "spring",uses = {ShippingAgentMapper.class,InitiatorMapper.class,InvoiceTypeMapper.class})
public interface SAInitiatorInvoiceTypeMapper {
	
	@Mapping(target = "shippingAgent", ignore = true)
	@Mapping(target = "invoiceType", ignore = true)
	@Mapping(target = "initiator", ignore = true)
	SAInitiatorInvoiceType importerInvoiceTypeDTOtoDoman(SAInitiatorInvoiceTypeDTO dto);
	
	@Mapping(target = "shippingAgent", ignore = true)
	@Mapping(target = "invoiceType", ignore = true)
	@Mapping(target = "initiator", ignore = true)
	SAInitiatorInvoiceTypeDTO importerInvoiceTypeTypeDomaintoDTO(SAInitiatorInvoiceType destination);
	  
	  
}
