package ae.dt.deliveryorder.specification;

import ae.dt.common.constants.Constants;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.dto.SearchDTO;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoLInvoiceSpecification {

    public static Specification<BolInvoice> getFilter(String bolNo,String invoiceNumber) {
        final List<Predicate> predicateList = new ArrayList<>();
        return ((root, query, cb) -> {
            predicateList.add(cb.equal(root.get("invoiceNumber"), invoiceNumber));
            Join<BolInvoice, Bol> bol = root.join("bol");
            predicateList.add(cb.equal(bol.get("bolNumber"), bolNo));
            query.distinct(true);
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        });

    }

    public static Specification<BolInvoice> getInvoiceDetails(String shippingAgentCode,String invoiceNumber, String bolNO) {
        final List<Predicate> predicateList = new ArrayList<>();
        return ((root, query, cb) -> {
            predicateList.add(cb.equal(root.get("invoiceNumber"), invoiceNumber));
//            predicateList.add(cb.equal(root.get("isValid"), Constants.ACTIVE_VAL));
            Join<BolInvoice, Bol> bol = root.join("bol");
            Join<Bol, BolDetail> bolDetails = bol.join("bolDetails");
            if(!StringUtils.isEmpty(bolNO))
            predicateList.add(cb.equal(bol.get("bolNumber"), bolNO));
            predicateList.add(cb.equal(bolDetails.get("shippingAgentCode"), shippingAgentCode));
            query.distinct(true);
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        });

    }

}
