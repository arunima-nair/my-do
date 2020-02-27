package ae.dt.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ae.dt.deliveryorder.domain.DTUserView;
import ae.dt.deliveryorder.repository.DTUserViewRepository;
@Service("indexService")
public class IndexServiceImpl implements IndexService {
	@Autowired
	DTUserViewRepository dTUserViewRepository;
	@Override
	public List<DTUserView> getDTUserList(String agentType, String agentCode, String userName) {
		List<String> subIds=new ArrayList();
		subIds.add(agentType);
		return dTUserViewRepository.findDistinctByAgentCodeAndUserNameAndBusinessSubIdIn(agentCode, userName, subIds);
	}

}
