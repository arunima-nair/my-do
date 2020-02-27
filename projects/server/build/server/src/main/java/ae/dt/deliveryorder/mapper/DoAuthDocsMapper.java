package ae.dt.deliveryorder.mapper;


import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.dto.DoAuthDocDTO;

/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(uses = {DoAuthRequestMapper.class,DocumentMapper.class})
public interface DoAuthDocsMapper {
	 @Mapping(target="doAuthRequest", ignore=true)
	 DoAuthDoc dTOtoDoman(DoAuthDocDTO dto);
	 @Mapping(target="doAuthRequest", ignore=true)
	 DoAuthDocDTO domaintoDTO(DoAuthDoc destination);
	 
	 Set<DoAuthDocDTO> domaintoDTOset(Set<DoAuthDoc> dTOset);
	 Set<DoAuthDoc> dtotoDomainset(Set<DoAuthDocDTO> dTOset);
	 /*	@Mappings({ 
	        @Mapping(target = "doAuthDocs", ignore = true)
	    })
		DoAuthRequestDTO domaintoDTO(DoAuthRequest destination);

	    @Mappings({ 
	        @Mapping(target = "doAuthDocs", ignore = true)
	    })
	    DoAuthRequest dTOtoDoman(DoAuthRequestDTO dto);*/
	
}
