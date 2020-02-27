package ae.dt.deliveryorder.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.domain.DoAuthRequest;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface DoAuthDocRepository extends CrudRepository<DoAuthDoc, Long>, JpaSpecificationExecutor<DoAuthRequest>  {

	DoAuthDoc findByDoAuthDocsId(Long Id);

	@Query(value = "SELECT SEQ_DO_REFNO.nextval FROM dual", nativeQuery = true)
	Long getNextDoRefNo();

	void deleteByDoAuthRequestDoAuthRequestId(Long doAuthRequestId);
	
}
