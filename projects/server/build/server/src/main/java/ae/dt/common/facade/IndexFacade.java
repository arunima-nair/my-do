package ae.dt.common.facade;

import java.util.List;

import ae.dt.deliveryorder.domain.DTUserView;

public interface IndexFacade {

	List<DTUserView> getDTUserList(String agentType, String agentCode, String userName);

}
