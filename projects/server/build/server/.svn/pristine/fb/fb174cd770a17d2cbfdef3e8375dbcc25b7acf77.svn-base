package ae.dt.deliveryorder.repository;

import ae.dt.deliveryorder.domain.InvoiceType;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceTypeRepository extends CrudRepository<InvoiceType,String> {

    InvoiceType findByInvoiceTypeNameAndIsActive(String invoiceTypeName,Long isValid);
    InvoiceType findByInvoiceTypeIdAndIsActive(Long invoiceTYpeId,Long isValid);
}
