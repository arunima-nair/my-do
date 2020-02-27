package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.RejectionRemark;


/**
 * Created by Arunima.Nair on 3/16/2019.
 */
public interface RejectionRemarkRepository extends CrudRepository<RejectionRemark,String> {
	@Query("select r from RejectionRemark r where r.rejectionRemarksId = :rejectionRemarksId")
	RejectionRemark findById(@Param("rejectionRemarksId")Long rejectionRemarksId);
	
	 	
}
