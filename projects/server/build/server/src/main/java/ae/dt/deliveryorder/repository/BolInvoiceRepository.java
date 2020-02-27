package ae.dt.deliveryorder.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.BolInvoice;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface BolInvoiceRepository extends JpaRepository<BolInvoice,String> , JpaSpecificationExecutor<BolInvoice> {
//	 @Query("select i from BolInvoice i where i.invoiceNumber = :invoiceno")
//	BolInvoice findById(@Param("invoiceno")String invoiceno);

	BolInvoice findByInvoiceNumber(String invNo);

	List<BolInvoice> findByBolBolNumberAndInvoiceTypeInvoiceTypeId(String bolNo, Long invType);

	Date findInvoiceValidityDateByInvoiceNumber(String invValue);
	
	List<BolInvoice> findByBolBolNumber(String bolNo);
	
	List<BolInvoice> findByBolBolNumberAndPathIsNull(String bolNo);

	BolInvoice findByBolInvoiceId(Long bolInvoiceId);

	BolInvoice findByInvoiceNumberAndBolBolNumber(String invoiceNumber, String bolNumber);
	

}
