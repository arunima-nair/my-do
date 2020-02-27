package ae.dt.deliveryorder.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.dto.PaymentLogDTO;

/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(componentModel = "spring", uses = { DoAuthRequestMapper.class,BolInvoiceMapper.class ,ShippingAgentMapper.class})
public interface PaymentLogMapper {
	@Mapping(target = "doAuthRequest", ignore = true)
	@Mapping(target = "bolInvoice", ignore = true)
	@Mapping(target = "shippingAgent", ignore = true)
	PaymentLog paylogdTOtoDoman(PaymentLogDTO dto);
	@Mapping(target = "bolInvoice", ignore = true)
	@Mapping(target = "doAuthRequest", ignore = true)
	@Mapping(target = "shippingAgent", ignore = true)
	PaymentLogDTO paylogdomaintoDTO(PaymentLog destination);

	
	Set<PaymentLogDTO> paylogDomaintoDTOset(Set<PaymentLog> paylogDTOset);

	Set<PaymentLog> paylogDTOtoDomanset(Set<PaymentLogDTO> paylogDTOset);

}
