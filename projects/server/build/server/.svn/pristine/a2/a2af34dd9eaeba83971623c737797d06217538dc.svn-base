package ae.dt.deliveryorder.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import ae.dt.common.dto.ViewDTO;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;

/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(componentModel = "spring", uses = {

		DeliveryOderMapper.class, DoAuthDocsMapper.class, PaymentLogMapper.class, CollectionsMapper.class,ApprovalLogMapper.class })
public interface DoAuthRequestMapper {
	@Mapping(target = "bol", ignore = true)
	@Mapping(source = "doAuthRequestIdEncr", target = "doAuthRequestId", qualifiedByName = "getDecryptedId")
	DoAuthRequest dTOtoDoman(DoAuthRequestDTO dto);

	@Mapping(target = "bol", ignore = true)
	@Mapping(source = "doAuthRequestId", target = "doAuthRequestIdEncr", qualifiedByName = "getEncryptedId")
	DoAuthRequestDTO domaintoDTO(DoAuthRequest destination);
	
	List<DoAuthRequestDTO> domainlisttoDTO(Page<DoAuthRequest> pagelist);
	
	List<ViewDTO> doAuthRequestDomaintoViewDTO(Page<DoAuthRequest> pagelist);
	
	List<DoAuthRequestDTO> domainlisttoDTOlist(List<DoAuthRequest> domainlist);

	List<DoAuthRequestDTO> dtolisttoDomainlist(List<DoAuthRequest> doAuthRequestList);


	@Named("getEncryptedId")

	default String getEncryptedId(Long id) {

		return EncryptionUtil.encrypt(id.toString());

	}

	@Named("getDecryptedId")

	default Long getDecryptedId(String id) {
if(id!=null)
		return Long.valueOf(EncryptionUtil.decrypt(id));
return null;

	}


}
