package ae.dt.deliveryorder.repository;

import ae.dt.common.domain.DTAgentView;
import ae.dt.deliveryorder.domain.DTAdminEmailView;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Kamala.Devi on 4/9/2019.
 */
public interface DTAdminEmailViewRepository extends CrudRepository<DTAdminEmailView,String>{

    List<DTAdminEmailView> findDistinctByAgentCodeAndBusinessSubIdIn(String agentCode,List<String> d);

	String findDistinctByAgentCodeAndBusinessSubId(String agentCode, String agentType);
	
	List<DTAdminEmailView> findDistinctByAgentCode(String agentCode);
	
	DTAdminEmailView getAgentNameByAgentCodeAndBusinessSubIdIn(String agentCode, List<String> subIds);
}
