package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.ReturnRemark;


/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface ReturnRemarkRepository extends CrudRepository<ReturnRemark,String> {

	@Query("select r from ReturnRemark r where r.returnRemarksId = :returnRemarksId")
	ReturnRemark findById(@Param("returnRemarksId")Long returnRemarksId);
	
	 	
}
