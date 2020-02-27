package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;
import ae.dt.deliveryorder.dto.InvoiceTypeDTO;

@Mapper(componentModel = "spring", uses = {SAInitiatorInvoiceTypeMapper.class,BolInvoiceMapper.class}) 
public interface InvoiceTypeMapper {
	
	InvoiceType invoiceTypeDTOtoDoman(InvoiceTypeDTO dto);
	InvoiceTypeDTO invoiceTypeDomaintoDTO(InvoiceType destination);
	  
	  
}
