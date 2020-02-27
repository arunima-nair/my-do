package ae.dt.deliveryorder.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;


/**
 * Created by Arunima. Nair on 20/06/2019.
 */
public interface SAInitiatorInvoiceTypeRepository extends CrudRepository<SAInitiatorInvoiceType,String>{

	List<SAInitiatorInvoiceType> findByIsActiveAndShippingAgentShippingAgentCodeAndIsActiveAndInitiatorInitiatorTypeAndIsValid(Long isActiveSAInitiator,String agentCode,
			Long isActiveSA,String initiatorType,Long isActiveInitiator);

	List<SAInitiatorInvoiceType> findByIsActiveAndShippingAgentShippingAgentCodeAndIsActive(Long isActive,String sAID,Long isActiveSA);



	

 
}
