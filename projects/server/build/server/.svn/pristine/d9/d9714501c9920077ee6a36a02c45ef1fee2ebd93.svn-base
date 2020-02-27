package ae.dt.deliveryorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.common.domain.DTAgentView;


/**
 * Created by Arunima on 3/14/2019.
 */
public interface DTAgentViewRepository extends CrudRepository<DTAgentView,String>, JpaSpecificationExecutor<DTAgentView> {

	List<DTAgentView> findDistinctFirst10ByAgentCodeContainingIgnoreCaseAndBusinessSubIdIn(String searchQueryParam,List<String> businessSubId);
    List<DTAgentView> findDistinctFirst10ByAgentNameContainingIgnoreCaseAndBusinessSubIdIn(String searchQueryParam,List<String> businessSubId);
    DTAgentView findByAgentCodeContainingIgnoreCaseAndBusinessSubId(String name,String businessSubId );
    DTAgentView findByAgentCodeIgnoreCaseAndBusinessSubId(String name,String businessSubId );
    DTAgentView findByAgentCode(String blPartyCode);
	String findAgentNameByAgentCodeContainingIgnoreCaseAndBusinessSubIdIn(String agentCode, List<String> subIds);


}
