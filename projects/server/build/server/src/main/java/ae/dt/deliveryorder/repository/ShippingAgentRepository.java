package ae.dt.deliveryorder.repository;


import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.ShippingAgent;

/**
 * Created by Arunima. Nair on 17/06/2019.
 */
public interface ShippingAgentRepository extends CrudRepository<ShippingAgent,String>{

	ShippingAgent findByShippingAgentCodeAndIsActive(String agentCode,Long isActive);

 
}
