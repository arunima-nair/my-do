package ae.dt.deliveryorder.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import ae.dt.common.constants.Constants;
import ae.dt.common.dto.ViewDTO;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;

/**
 * @author ARUNIMA NAIR
 *
 */
@Mapper(componentModel = "spring", uses = { BolDetailsMapper.class, BolInvoiceMapper.class, DoAuthRequestMapper.class })
public interface BoLMapper {
	BoLMapper INSTANCE = Mappers.getMapper(BoLMapper.class);

	@Mapping(source = "encBolNumber", target = "bolNumber", qualifiedByName = "getDecryptedBol")
	@Mapping(source = "bolIdStr", target = "bolId", qualifiedByName = "getDecryptedBolId")
	Bol bolDTOtoDoman(BoLDTO dto);

	@Mapping(source = "bolNumber", target = "encBolNumber", qualifiedByName = "getEncryptedBol")
	@Mapping(source = "bolId", target = "bolIdStr", qualifiedByName = "getEncryptedBolId")
	BoLDTO bolDomaintoDTO(Bol destination);

	List<BoLDTO> bolDomaintoDTO(List<Bol> bolList);

	List<BoLDTO> bolDomaintoDTO(Page<Bol> pagebollist);

	List<ViewDTO> bolDomaintoViewDTO(Page<Bol> pagebollist);

	List<ViewDTO> bolDomaintoViewDTOList(List<Bol> bolList);

	Set<BolInvoiceDTO> bolDomaintoDTO(Set<BolInvoice> bolInvoicebyBolNumber);

	@Named("getEncryptedBol")

	default String getEncryptedBol(String bol) {

		return EncryptionUtil.encrypt(bol);

	}

	@Named("getDecryptedBol")

	default String getDecryptedBol(String bolEncr) {

		return EncryptionUtil.decrypt(bolEncr);

	}
	@Named("getEncryptedBolId")

	default String getEncryptedBolId(Long bol) {

		return EncryptionUtil.encrypt(Long.toString(bol));

	}

	@Named("getDecryptedBolId")

	default Long getDecryptedBolId(String bolIdEncr) {
		if(bolIdEncr!=null)
			return Long.valueOf(EncryptionUtil.decrypt(bolIdEncr));
	return null;

		//return EncryptionUtil.decrypt(Long.bolEncr);

	}
}
