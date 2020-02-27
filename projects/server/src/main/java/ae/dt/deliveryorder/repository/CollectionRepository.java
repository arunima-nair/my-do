package ae.dt.deliveryorder.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.DoAuthRequest;


/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface CollectionRepository extends CrudRepository<Collection,String> {

	Collection findByDoAuthRequest(Set<DoAuthRequest> doAuthRequests);
	
	@Query(value = "SELECT SEQ_DO_RECEIPT.nextval FROM dual", nativeQuery = true)
	Long getNextReceiptNo();
	
	@Query("select SUM(c.amount) from Collection c where c.doAuthRequest.doAuthRequestId = :doAuthRequestId")
	Double findAmountByDoAuthRequestDoAuthRequestId(@Param("doAuthRequestId")Long doAuthRequestId);

	Collection findByInvoiceNumber(String invoiceNo);

	List<Collection> findByTransactionId(String transactionId);	
}
