package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;

import ae.dt.deliveryorder.domain.Document;
import ae.dt.deliveryorder.dto.DocumentDTO;

@Mapper(componentModel = "spring",uses = {DoAuthDocsMapper.class})  
public interface DocumentMapper {
	
	Document dTOtoDoman(DocumentDTO dto);
	DocumentDTO domaintoDTO(Document destination);
	  
	  
}
