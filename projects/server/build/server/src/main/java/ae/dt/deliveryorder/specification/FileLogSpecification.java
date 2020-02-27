package ae.dt.deliveryorder.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import ae.dt.deliveryorder.domain.FileLogDetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.DateUtil;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.dto.SearchDTO;

public class FileLogSpecification {
	
	//Using JPA Specifications Method
	public static Specification<FileLog> getFilterData(SearchDTO searchDTO,String sortOrder,String sortcol, UserDTO userDTO) {

		final List<Predicate> predicateList = new ArrayList<>();
		return ((root, query, cb) -> {
			if (sortOrder.equalsIgnoreCase("DSC"))
				query.orderBy(cb.desc(root.get(sortcol)));
			else if (sortOrder.equalsIgnoreCase("ASC"))
				query.orderBy(cb.asc(root.get(sortcol)));
			else
				query.orderBy(cb.asc(root.get("createdDate")));
			
			if(StringUtils.isNotEmpty(searchDTO.getRefNo())){
				predicateList.add(cb.equal(root.get("referenceNumber"), searchDTO.getRefNo()));
			}
			if(StringUtils.isNotEmpty(searchDTO.getFrmDate()) || StringUtils.isNotEmpty(searchDTO.getToDate())){
				predicateList.add(cb.between(
						cb.function("TRUNC", java.util.Date.class, root.get("createdDate")),
						DateUtil.parseDate(searchDTO.getFrmDate(), DateUtil.DATE_FORMAT),
						DateUtil.parseDate(searchDTO.getToDate(), DateUtil.DATE_FORMAT)));
			}
			if(StringUtils.isNotEmpty(searchDTO.getBolNumber())){
				Join<FileLog, FileLogDetail> fileLogDetailJoin = root.join("fileLogDetails");
				predicateList.add( cb.equal(fileLogDetailJoin.get("bolNumber"), searchDTO.getBolNumber()));
			}
			if(StringUtils.isNotEmpty(searchDTO.getInvoiceNumber())) {
				//predicateList.add(cb.isMember(searchDTO.getInvoiceNumber(), root.get("fileLogDetails").get("invoiceNumber")));
				Join<FileLog, FileLogDetail> fileLogDetailJoin = root.join("fileLogDetails");
				predicateList.add( cb.equal(fileLogDetailJoin.get("invoiceNumber"), searchDTO.getInvoiceNumber()));
			}
			if(StringUtils.isNotEmpty(searchDTO.getShippingAgentCode())) {
				predicateList.add(cb.equal(root.get("shippingAgentCode"), searchDTO.getShippingAgentCode()));
			}
			query.distinct(true);
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		});

	}


}
