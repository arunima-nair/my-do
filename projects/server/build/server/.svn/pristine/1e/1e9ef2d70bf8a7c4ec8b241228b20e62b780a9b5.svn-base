package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ae.dt.deliveryorder.domain.RequestBolInvoice;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface RequestBolInvoiceRepository extends JpaRepository<RequestBolInvoice, String>, JpaSpecificationExecutor<RequestBolInvoice> {

	RequestBolInvoice findByBolNoAndShippingCode(String bol, String agentCode);

	RequestBolInvoice findByBolInvoiceNoAndShippingCode(String inv, String agentCode);
	

	 	
}
