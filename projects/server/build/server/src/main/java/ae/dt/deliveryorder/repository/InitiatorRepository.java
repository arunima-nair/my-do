package ae.dt.deliveryorder.repository;


import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.Initiator;

/**
 * Created by Arunima. Nair on 05/08/2019.
 */
public interface InitiatorRepository extends CrudRepository<Initiator,String>{

	Initiator findByInitiatorTypeAndIsActive(String agentType, Long activeVal);

	

 
}
