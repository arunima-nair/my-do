package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.Document;


/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface DocumentRepository extends CrudRepository<Document,String> {
	
	  @Query("select d from Document d where d.documentId = :documentId") 
	  Document findById(@Param("documentId")Long documentId);

	Document findByDocumentValue(String value);


	//Long findDocumentIdByDocumentValue(String value);
	 

	 	
}
