package ae.dt.deliveryorder.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.DTUserView;

/**
 * @author ARUNIMA NAIR
 *
 */
public interface DTUserViewRepository extends CrudRepository<DTUserView, String> {

	List<DTUserView> findDistinctByAgentCodeAndUserNameAndBusinessSubIdIn(String agentCode, String userName,
			List<String> subIds);
	
}
