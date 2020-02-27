package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.domain.ShippingAgent;

import java.util.List;


/**
 * Created by Kamala.Devi on 1/28/2019.
 */

public interface PaymentLogRepository extends JpaRepository<PaymentLog,String> {
	
	@Query("select p from PaymentLog p where p.transactionId = :soTransactionId")
	List<PaymentLog> findByTransactionId(@Param("soTransactionId")String soTransactionId);
	@Query("select p from PaymentLog p where  (60*24* (sysdate- p.createdDate) ) > :enquireduration and " +
			"p.status = :status")
	List<PaymentLog> fetchRosoomPendingPayments (@Param("enquireduration") Double enquireduration,@Param("status") String status);
	PaymentLog findFirstByDoAuthRequestDoAuthRequestIdOrderByCreatedDateDesc(Long doAuthRequestId);
	
	@Query(value = "SELECT SEQ_DO_TRANXNO.nextval FROM dual", nativeQuery = true)
	Long getNextTranxNo();

	
	List<PaymentLog> getShippingAgentByDoAuthRequestDoAuthRequestId(Long doAuthRequestId);

	List<PaymentLog> findByTransactionIdAndStatus(String transactionId,String status);
	
	PaymentLog findByPaymentLogId(Long paymentLogId);
	
	PaymentLog findByBolInvoiceBolInvoiceId(Long invoiceId);
	PaymentLog findByBolInvoiceBolInvoiceIdAndDoAuthRequestBolBolId(Long bolInvoiceId, Long bolId);
	PaymentLog findByDoAuthRequestDoAuthRequestIdAndBolInvoiceInvoiceNumber(Long doAuthRequestId, String invoiceNo);
	PaymentLog findByDoAuthRequestDoAuthRequestIdAndBolInvoiceInvoiceNumberAndStatus(Long doAuthRequestId,
			String invoiceNo, String status);

	
	
}
