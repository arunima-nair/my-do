package ae.dt.deliveryorder.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.ApprovalLog;
import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.dto.ApprovalLogDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;

/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(componentModel = "spring", uses = { DoAuthRequestMapper.class,RejectionRemarksMapper.class,ReturnRemarksMapper.class })
public interface ApprovalLogMapper {
	@Mapping(target = "doAuthRequest", ignore = true)
	ApprovalLog dTOtoDoman(ApprovalLogDTO dto);

	@Mapping(target = "doAuthRequest", ignore = true)
	ApprovalLogDTO domaintoDTO(ApprovalLog destination);

	
	Set<ApprovalLogDTO> domaintoDTOset(Set<ApprovalLog> dTOset);

	Set<ApprovalLog> dTOtoDomanset(Set<ApprovalLogDTO> dTOset);

}
