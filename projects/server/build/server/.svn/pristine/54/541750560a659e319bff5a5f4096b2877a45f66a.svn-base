package ae.dt.common.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ae.dt.common.service.IndexService;
import ae.dt.deliveryorder.domain.DTUserView;

@Service("indexFacade")
public class IndexFacadeImpl implements IndexFacade{
	@Autowired
	IndexService indexService;

	@Override
	public List<DTUserView> getDTUserList(String agentType, String agentCode, String userName) {
		return indexService.getDTUserList(agentType,agentCode,userName);
	}

}
