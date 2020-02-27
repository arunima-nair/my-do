package ae.dt.deliveryorder.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import ae.dt.deliveryorder.domain.RequestBolInvoice;
import ae.dt.deliveryorder.dto.RequestBolInvoiceDTO;


/**
 * Created by Arunima on 2/18/2019.
 */
@Mapper(componentModel = "spring",uses = {BoLMapper.class})  
public interface RequestBolInvoiceMapper {
	
	RequestBolInvoice requestBolInvoicedTOtoDoman(RequestBolInvoiceDTO requestBolInvoiceDTO);
	
	RequestBolInvoiceDTO requestBolInvoicedomaintoDTO(RequestBolInvoice destination);

	List<RequestBolInvoiceDTO> requestBolInvoicedomaintoDTO(List<RequestBolInvoice> content);
	
	 
}
