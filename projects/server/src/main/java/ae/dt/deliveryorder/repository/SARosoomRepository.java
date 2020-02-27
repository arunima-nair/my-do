package ae.dt.deliveryorder.repository;


import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.SARosoom;


/**
 * Created by Arunima. Nair on 30/06/2019.
 */
public interface SARosoomRepository extends CrudRepository<SARosoom,String>{

	SARosoom findByCurrencyAndShippingAgentShippingAgentId(String currency,Long shippingAgentId);



	

 
}
