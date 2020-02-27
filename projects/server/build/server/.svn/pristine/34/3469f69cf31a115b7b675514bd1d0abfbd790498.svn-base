package ae.dt.deliveryorder.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.PaymentOption;



public interface PaymentOptionRepository  extends CrudRepository<PaymentOption,String> {

	PaymentOption findByShippingAgentCode(String agentCode);

}
