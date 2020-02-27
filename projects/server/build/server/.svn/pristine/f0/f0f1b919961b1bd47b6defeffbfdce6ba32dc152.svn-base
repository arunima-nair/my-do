package ae.dt.deliveryorder.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.SAInitiatorPayment;


/**
 * Created by Arunima. Nair on 26/06/2019.
 */
public interface SAInitiatorPaymentRepository extends CrudRepository<SAInitiatorPayment,String>{

	SAInitiatorPayment findPaymentAllowedByShippingAgentShippingAgentCodeAndInitiatorInitiatorType(String agentCode, String impType);


	SAInitiatorPayment findByShippingAgentShippingAgentIdAndInitiatorInitiatorId(Long shippingAgentId,Long initiatorId);

	

 
}
