package ae.dt.deliveryorder.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.dto.PaymentOfProofDTO;


/**
 * Created by Arunima on 2/10/2019.
 */
@Mapper(componentModel = "spring",uses = {CollectionsMapper.class})  
public interface PaymentOfProofMapper {
	
	PaymentOfProof paymentOfProofdTOtoDoman(PaymentOfProofDTO dto);
	 @Mapping(target = "collection", ignore = true)
	PaymentOfProofDTO paymentOfProofdomaintoDTO(PaymentOfProof destination);
	
	
	 
}
