package ae.dt.deliveryorder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.DoAuthRequest;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
/**
 * @author ARUNIMA NAIR
 *
 */
public interface DoAuthRequestRepository
		extends JpaRepository<DoAuthRequest, String>, JpaSpecificationExecutor<DoAuthRequest> {
	@Query("select d from DoAuthRequest d where d.bol.bolNumber = :bolNumber")
	Page<DoAuthRequest> findByBolnumber(@Param("bolNumber") String bolNumber, Pageable pageable);

	@Query("select d from DoAuthRequest d where d.doAuthRequestId = :doAuthRequestId")
	DoAuthRequest findById(@Param("doAuthRequestId") Long doAuthRequestId);
	
	DoAuthRequest findByDoAuthRequestId(Long doAuthRequestId);

	Bol findBolByDoAuthRequestId(Long doAuthRequestId);

	DoAuthRequest findDoAuthRequestByDoRefNo(String doRefNo);

}
