package ae.dt.deliveryorder.repository;

import ae.dt.deliveryorder.domain.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Kamala.Devi on 3/18/2019.
 */
public interface DeliveryOrderRepository extends CrudRepository<DeliveryOrder, String>, JpaSpecificationExecutor<DeliveryOrder> {
    @Query("select d from DeliveryOrder d where d.doAuthRequest.doAuthRequestId = :docId")
    DeliveryOrder findByAuthReqId(@Param("docId")Long docId);

	
}
