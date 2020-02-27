package ae.dt.deliveryorder.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.domain.ShippingAgentAttributes;

/**
 * Created by Arunima. Nair on 17/06/2019.
 */
public interface ShippingAgentAttributesRepository extends CrudRepository<ShippingAgentAttributes,String>{

	List<ShippingAgentAttributes> findByShippingAgentShippingAgentCodeAndIsActive(Long agentId,Long isActive);

	List<ShippingAgentAttributes> findByShippingAgentAndIsActive(ShippingAgent shippingAgent,Long isActive);

	List<ShippingAgentAttributes> findShippingAgentAttributesByAttributeNameAndIsActiveAndShippingAgentShippingAgentCode(String attributeName,Long isActive,String agentCode);

}
