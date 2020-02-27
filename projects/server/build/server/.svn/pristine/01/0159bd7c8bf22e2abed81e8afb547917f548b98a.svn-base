package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;

@JsonIgnoreProperties
@Mapper(componentModel = "spring",uses = {PaymentLogMapper.class,CollectionsMapper.class})
public interface BolInvoiceMapper {
	@Mapping(target = "bol", ignore = true)
	@Mapping(target = "invoiceType", ignore = true)
	BolInvoice bolinvoiceDTOtoDoman(BolInvoiceDTO invdto);
	 @Mapping(target = "bol", ignore = true)
	 @Mapping(target = "invoiceType", ignore = true)
	BolInvoiceDTO bolinvoiceDomaintoDTO(BolInvoice destination);
	//Set<BolInvoice> bolinvoiceDTOtoDomanSet(Set<BolInvoiceDTO> bolinvoiceDTOset);

	//Bol bolDTOtoDoman(BoLDTO invdto);

}
