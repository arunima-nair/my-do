package ae.dt.deliveryorder.specification;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import ae.dt.common.constants.Constants;
import ae.dt.common.domain.DTAgentView;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.DateUtil;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.RequestBolInvoice;
import ae.dt.deliveryorder.dto.SearchDTO;

@Component
public class BillOfLaddingSpecification {

	/**
	 * @param searchDTO
	 * @param userDTO
	 * @param sortorder
	 * @param sortCol
	 * @return Bol
	 */
	public static Specification<Bol> getFilter(SearchDTO searchDTO, UserDTO userDTO, String sortorder, String sortCol) {
		if (userDTO.getUserType().equalsIgnoreCase("A")) {
			if (searchDTO.getToDate() == null)
				searchDTO.setToDate("");
			if (searchDTO.getFrmDate() == null)
				searchDTO.setFrmDate("");
			return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(bolInvoiceContains(searchDTO.getBolInvoiceNumber(), sortorder, sortCol))
					.and(bolStatusContains(searchDTO.getStatus(), sortorder, sortCol))
					.and(agentcodecontains(userDTO.getAgentCode(), sortorder, sortCol))
					.and(frmdateContains(searchDTO.getFrmDate(), sortorder, sortCol))
					.and(todateContains(searchDTO.getToDate(), sortorder, sortCol));

		} else if (userDTO.getUserType().equalsIgnoreCase("IN")) {
			String status = "";
			status = searchDTO.getStatus();
			
			return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(bolInvoiceContains(searchDTO.getBolInvoiceNumber(), sortorder, sortCol))
					.and(bolStatusContains(status, sortorder, sortCol))
					.and(shippingLinenameContains(searchDTO.getShippingLineName(), sortorder, sortCol))
					.and(frmdateContains(searchDTO.getFrmDate(), sortorder, sortCol))
					.and(todateContains(searchDTO.getToDate(), sortorder, sortCol));
		}else{
			String status = "";
			status = searchDTO.getStatus();
			
			return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(bolInvoiceContains(searchDTO.getBolInvoiceNumber(), sortorder, sortCol))
					.and(bolStatusContains(status, sortorder, sortCol))
					.and(shippingLinenameContains(searchDTO.getShippingLineName(), sortorder, sortCol));
					//.and(importercodecontains(searchDTO.getImpCode(), sortorder, sortCol));
		}

	}
	public static Specification<Bol> getFilterInternal(SearchDTO searchDTO, UserDTO userDTO) {
		 String sortorder ="";
		 String sortCol = "";
		return where(bolStatusContains(searchDTO.getStatus(), sortorder, sortCol))
				.and(agentcodecontains(searchDTO.getShippingAgentCode(), sortorder, sortCol))
				.and(initiatorcodecontains(searchDTO.getImpCode(), sortorder, sortCol))
				.and(frmdateContains(searchDTO.getFrmDate(), sortorder, sortCol))
				.and(todateContains(searchDTO.getToDate(), sortorder, sortCol));
	}

	private static Specification<Bol> bolStatusContains(String status, String sortorder, String sortCol) {

		return userAttributeContains("status", status, sortorder, sortCol);
	}

	public static Specification<Bol> bolNumberContains(String bolNo, String sortorder, String sortCol) {
		return userAttributeContains("bolNumber", bolNo, sortorder, sortCol);
	}

	public static Specification<Bol> bolInvoiceContains(String bolinvNo, String sortorder, String sortCol) {

		return userAttributeContains("bolInvoices", bolinvNo, sortorder, sortCol);
	}

	private static Specification<Bol> shippingLinenameContains(String shippingLineName, String sortorder,
			String sortCol) {
		return userAttributeContains("bolDetails", shippingLineName, sortorder, sortCol);
	}

	private static Specification<Bol> agentcodecontains(String impCode, String sortorder, String sortCol) {
		return userAttributeContains("all", impCode, sortorder, sortCol);
	}
	private static Specification<Bol> initiatorcodecontains(String agentCode, String sortorder,
			String sortCol) {
		return userAttributeContains("importerCode", agentCode, sortorder, sortCol);
	}
	private static Specification<Bol> todateContains(String createdDate, String sortorder, String sortCol) {
		return userAttributeContains("to", createdDate, sortorder, sortCol);
	}

	private static Specification<Bol> frmdateContains(String createdDate, String sortorder, String sortCol) {
		return userAttributeContains("frm", createdDate, sortorder, sortCol);
	}
	private static Specification<Bol> importercodecontains(String impCode, String sortorder, String sortCol) {
		return userAttributeContains("importerCode", impCode, sortorder, sortCol);
	}

	private static Specification<Bol> userAttributeContains(String attribute, String value, String sortorder,
			String sortCol) {
		final List<Predicate> predicateList = new ArrayList<>();
		return (root, query, cb) -> {
			if (sortorder.equalsIgnoreCase("DSC"))
				query.orderBy(cb.desc(root.get(sortCol)));
			else if (sortorder.equalsIgnoreCase("ASC"))
				query.orderBy(cb.asc(root.get(sortCol)));
			else
				query.orderBy(cb.desc(root.get("createdDate")));
			if (StringUtils.isEmpty(value)) {
				return null;
			}

			if (attribute.equals("bolInvoices")) {
				predicateList.add(cb.equal(root.join(attribute).get("invoiceNumber"), value
				// cb.lower(root.join(attribute).get("invoiceNumber")), value.toLowerCase()

				));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

			} else if (attribute.equals("bolDetails")) {
				predicateList.add(cb.equal(

						root.join(attribute).get("shippingAgentCode"), value

				));

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equals("all")) {
				predicateList.add(cb.equal(

						root.join("bolDetails").get("shippingAgentCode"), value

				));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equals("importerCode")) {
				predicateList.add(cb.equal(

						root.join("doAuthRequests").get("initiatorCode"), value

				));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equals("status")) {

				List<String> stsList = Arrays.asList(value.split("\\s*,\\s*"));
				predicateList.add(

						// root.get(attribute).in(stsList), root
						stsList.isEmpty() ? cb.conjunction() : root.get(attribute).in(stsList)

				);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} 
			else if (attribute.equalsIgnoreCase("frm")) {

				if (StringUtils.isEmpty(value)) {
					return null;
				}
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				//Date dateValue = formatter.parse(value);
			//	predicateList.add(cb.lessThanOrEqualTo(cb.function("TRUNC", Date.class, root.get("creationDate")) ,

					//	DateUtil.parseDate(toDate, DateUtil.WEB_DATE_FORMAT)));​
				Date dateValue =DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
				predicateList.add(cb.greaterThanOrEqualTo(cb.function("TRUNC", Date.class, root.get("createdDate"))
						, dateValue));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equalsIgnoreCase("to")) {
				if (StringUtils.isEmpty(value)) {
					return null;
				}
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				//Date dateValue = formatter.parse(value);
				Date dateValue =DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
				//predicateList.add(cb.lessThanOrEqualTo(

					//	root.get("createdDate"), dateValue));
				predicateList.add(cb.lessThanOrEqualTo(cb.function("TRUNC", Date.class, root.get("createdDate"))
						, dateValue));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
			else {

				predicateList.add(cb.equal(

						root.get(attribute), value

				));

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	/**
	 * @param name
	 * @param businessSubId
	 * @return
	 */
	public Specification<DTAgentView> getAgentFilter(String name, String businessSubId) {
		final List<Predicate> predicateList = new ArrayList<>();
		return (root, query, cb) -> {
			if (name == null) {
				return null;
			} else {
				query.distinct(true);
				predicateList.add(cb.and((cb.like((cb.upper(root.get("agentName"))), "%" + name.toUpperCase() + "%")),
						cb.like(root.get("businessSubId"), businessSubId)));
				// predicateList.add( cb.like(root.get("agentName"), name.toUpperCase()+"%") );
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	
	public Specification<RequestBolInvoice> getFilterReqBolInv(SearchDTO searchDTO, UserDTO userDTO, String sortorder,
			String sortCol) {
		if (userDTO.getUserType().equalsIgnoreCase("IN")) {
			if (searchDTO.getToDate() == null)
				searchDTO.setToDate("");
			if (searchDTO.getFrmDate() == null)
				searchDTO.setFrmDate("");
			return where(bolNumberRequestContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(bolInvoiceRequestContains(searchDTO.getBolInvoiceNumber(), sortorder, sortCol))
					.and(agentcodeRequestcontains(userDTO.getAgentCode(), sortorder, sortCol))
					.and(frmdateRequestContains(searchDTO.getFrmDate(), sortorder, sortCol))
					.and(reqTypeRequestContains(searchDTO.getReqType(), sortorder, sortCol))
					.and(todateRequestContains(searchDTO.getToDate(), sortorder, sortCol));

		}
		return null;
	}
	private Specification<RequestBolInvoice> reqTypeRequestContains(String reqType, String sortorder, String sortCol) {
		return userAttributeRequestContains("reqType", reqType, sortorder, sortCol);
	}
	private Specification<RequestBolInvoice> todateRequestContains(String toDate, String sortorder, String sortCol) {
		return userAttributeRequestContains("to", toDate, sortorder, sortCol);
	}
	private Specification<RequestBolInvoice> frmdateRequestContains(String frmDate, String sortorder, String sortCol) {
		return userAttributeRequestContains("frm", frmDate, sortorder, sortCol);
	}
	private Specification<RequestBolInvoice> agentcodeRequestcontains(String agentCode, String sortorder,
			String sortCol) {
		return userAttributeRequestContains("shippingCode", agentCode, sortorder, sortCol);
	}
	private Specification<RequestBolInvoice> bolInvoiceRequestContains(String bolInvoiceNumber, String sortorder,
			String sortCol) {
		return userAttributeRequestContains("bolInvoiceNo", bolInvoiceNumber, sortorder, sortCol);
	}
	private Specification<RequestBolInvoice> bolNumberRequestContains(String bolNumber, String sortorder, String sortCol) {
		return userAttributeRequestContains("bolNo", bolNumber, sortorder, sortCol);
	}
	private static Specification<RequestBolInvoice> userAttributeRequestContains(String attribute, String value, String sortorder,
			String sortCol) {
		final List<Predicate> predicateList = new ArrayList<>();
		return (root, query, cb) -> {
			if (sortorder.equalsIgnoreCase("DSC"))
				query.orderBy(cb.desc(root.get(sortCol)));
			else if (sortorder.equalsIgnoreCase("ASC"))
				query.orderBy(cb.asc(root.get(sortCol)));
			else
				query.orderBy(cb.desc(root.get("createdDate")));
			if (StringUtils.isEmpty(value)) {
				return null;
			}

			 if (attribute.equalsIgnoreCase("frm")) {

				if (StringUtils.isEmpty(value)) {
					return null;
				}
				Date dateValue =DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
				predicateList.add(cb.greaterThanOrEqualTo(cb.function("TRUNC", Date.class, root.get("createdDate"))
						, dateValue));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equalsIgnoreCase("to")) {
				if (StringUtils.isEmpty(value)) {
					return null;
				}
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date dateValue =DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
				predicateList.add(cb.lessThanOrEqualTo(cb.function("TRUNC", Date.class, root.get("createdDate"))
						, dateValue));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
			else {

				predicateList.add(cb.equal(

						root.get(attribute), value

				));

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
}
