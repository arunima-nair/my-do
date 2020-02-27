package ae.dt.common.service;

import java.util.List;

import ae.dt.deliveryorder.domain.DTUserView;

public interface IndexService {

	List<DTUserView> getDTUserList(String agentType, String agentCode, String userName);

}
