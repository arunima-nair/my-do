package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.dto.CollectionDTO;


/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(componentModel = "spring",uses = {CreditMapper.class,RosoomMapper.class,PaymentOfProofMapper.class})  
public interface CollectionsMapper {

Collection collectiondTOtoDoman(CollectionDTO dto);
@Mapping(target = "doAuthRequest", ignore = true)
@Mapping(target = "bolInvoice", ignore = true)
CollectionDTO collectiondomaintoDTO(Collection destination);

//Set<CollectionDTO> collectionDomaintoDTOset(Set<Collection> collectionDTOset);

//Set<Collection> collectiontoDomanset(Set<CollectionDTO> collectionDTOset);
	 
}
