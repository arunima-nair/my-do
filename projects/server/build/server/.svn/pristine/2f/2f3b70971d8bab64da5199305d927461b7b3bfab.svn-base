package ae.dt.deliveryorder.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.AlertNotification;
import ae.dt.deliveryorder.domain.Bol;


/**
 * Created by Arunima.Nair on 4/2/2019.
 */
public interface AlertNotificationRepository extends CrudRepository<AlertNotification,String> {

	AlertNotification findByBolIdAndEmailId(String bolNo,String email);

	List<AlertNotification> findByBolId(String bolNumber);
	
	String getEmailIdByBolId(String bolNo);
	
	 	
}
