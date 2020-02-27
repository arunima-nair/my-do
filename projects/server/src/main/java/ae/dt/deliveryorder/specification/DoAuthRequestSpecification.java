package ae.dt.deliveryorder.specification;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import ae.dt.deliveryorder.domain.Bol;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.DateUtil;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.SearchDTO;

@Component
public class DoAuthRequestSpecification {

	public static Specification<DoAuthRequest> getFilter(SearchDTO searchDTO, UserDTO userDTO, String sortorder,
			String sortCol) {

		if (searchDTO.getToDate() == null)
			searchDTO.setToDate("");
		if (searchDTO.getFrmDate() == null)
			searchDTO.setFrmDate("");
		if(userDTO.getUserType().equalsIgnoreCase("IN")) {
			return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(doRefContains(searchDTO.getDoRefNo(), sortorder, sortCol))
					.and(doPartyContains(searchDTO.getDoParty(), sortorder, sortCol))
					.and(statusContains(searchDTO.getStatus(), sortorder, sortCol))
					.and(initiatorcodecontains(searchDTO.getImpCode(), sortorder, sortCol))
					.and(agentcodecontains(searchDTO.getShippingAgentCode(), sortorder, sortCol));
		}
		else if ((StringUtils.isNotEmpty(searchDTO.getFrmDate()) && (StringUtils.isNotEmpty(searchDTO.getToDate())))) {
			if (userDTO.getUserType().equalsIgnoreCase("A")) {
				return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
						.and(doRefContains(searchDTO.getDoRefNo(), sortorder, sortCol))
						.and(reqPartyContains(searchDTO.getReqParty(), sortorder, sortCol))
						.and(reqPartyEmailContains(searchDTO.getReqPartyEmail(), sortorder, sortCol))
						.and(doPartyContains(searchDTO.getDoParty(), sortorder, sortCol))
						.and(dateContains(searchDTO.getFrmDate(), searchDTO.getToDate(), sortorder, sortCol))
						.and(statusContains(searchDTO.getStatus(), sortorder, sortCol))
						.and(agentcodecontains(userDTO.getAgentCode(), sortorder, sortCol));
			} else {
				return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
						.and(doRefContains(searchDTO.getDoRefNo(), sortorder, sortCol))
						.and(reqPartyContains(searchDTO.getReqParty(), sortorder, sortCol))
						.and(reqPartyEmailContains(searchDTO.getReqPartyEmail(), sortorder, sortCol))
						.and(doPartyContains(searchDTO.getDoParty(), sortorder, sortCol))
						.and(dateContains(searchDTO.getFrmDate(), searchDTO.getToDate(), sortorder, sortCol))
						.and(initiatorcodecontains(userDTO.getAgentCode(), sortorder, sortCol))
						.and(statusContains(searchDTO.getStatus(), sortorder, sortCol));

			}
		} else if (userDTO.getUserType().equalsIgnoreCase("A")) {
			return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(doRefContains(searchDTO.getDoRefNo(), sortorder, sortCol))
					.and(reqPartyContains(searchDTO.getReqParty(), sortorder, sortCol))
					.and(reqPartyEmailContains(searchDTO.getReqPartyEmail(), sortorder, sortCol))
					.and(doPartyContains(searchDTO.getDoParty(), sortorder, sortCol))
					.and(frmdateContains(searchDTO.getFrmDate(), sortorder, sortCol))
					.and(todateContains(searchDTO.getToDate(), sortorder, sortCol))
					.and(statusContains(searchDTO.getStatus(), sortorder, sortCol))
					.and(invoiceStatusContains(searchDTO.getInvoiceStatus(), sortorder, sortCol))
					.and(agentcodecontains(userDTO.getAgentCode(), sortorder, sortCol));
		} else {
			return where(bolNumberContains(searchDTO.getBolNumber(), sortorder, sortCol))
					.and(doRefContains(searchDTO.getDoRefNo(), sortorder, sortCol))
					.and(reqPartyContains(searchDTO.getReqParty(), sortorder, sortCol))
					.and(reqPartyEmailContains(searchDTO.getReqPartyEmail(), sortorder, sortCol))
					.and(doPartyContains(searchDTO.getDoParty(), sortorder, sortCol))
					.and(frmdateContains(searchDTO.getFrmDate(), sortorder, sortCol))
					.and(todateContains(searchDTO.getToDate(), sortorder, sortCol))
					.and(invoiceStatusContains(searchDTO.getInvoiceStatus(), sortorder, sortCol))
					.and(statusContains(searchDTO.getStatus(), sortorder, sortCol))
					.and(initiatorcodecontains(userDTO.getAgentCode(), sortorder, sortCol));

		}

	}



	private static Specification<DoAuthRequest> dateContains(String frm, String to, String sortorder, String sortCol) {
		return userAttributeDateContains("frmto", frm, to, sortorder, sortCol);
	}

	private static Specification<DoAuthRequest> todateContains(String createdDate, String sortorder, String sortCol) {
		return userAttributeContains("to", createdDate, sortorder, sortCol);
	}

	private static Specification<DoAuthRequest> frmdateContains(String createdDate, String sortorder, String sortCol) {
		return userAttributeContains("frm", createdDate, sortorder, sortCol);
	}

	private static Specification<DoAuthRequest> reqPartyEmailContains(String reqEmailParty, String sortorder,
			String sortCol) {
		return userAttributeContains("reqEmail", reqEmailParty, sortorder, sortCol);
	}

	private static Specification<DoAuthRequest> doPartyContains(String doParty, String sortorder, String sortCol) {
		return userAttributeContains("doPartyName", doParty, sortorder, sortCol);
	}

	private static Specification<DoAuthRequest> reqPartyContains(String reqParty, String sortorder, String sortCol) {
		return userAttributeContains("reqPartyName", reqParty, sortorder, sortCol);
	}

	public static Specification<DoAuthRequest> statusContains(String status, String sortorder, String sortCol) {
		return userAttributeContains("status", status, sortorder, sortCol);
	}

	public static Specification<DoAuthRequest> bolNumberContains(String bolNo, String sortorder, String sortCol) {
		return userAttributeContains("bol", bolNo, sortorder, sortCol);
	}

	public static Specification<DoAuthRequest> doRefContains(String doRefNo, String sortorder, String sortCol) {

		return userAttributeContains("doRefNo", doRefNo, sortorder, sortCol);
	}

	public static Specification<DoAuthRequest> agentcodecontains(String agtCode, String sortorder, String sortCol) {
		return userAttributeContains("all", agtCode, sortorder, sortCol);
	}
	public static Specification<DoAuthRequest> invoiceStatusContains(String status, String sortorder, String sortCol) {
		return userAttributeContains("invoiceStatus", status, sortorder, sortCol);
	}
	private static Specification<DoAuthRequest> initiatorcodecontains(String agentCode, String sortorder,
			String sortCol) {
		return userAttributeContains("initiatorCode", agentCode, sortorder, sortCol);
	}
	private static Specification<DoAuthRequest> userAttributeDateContains(String string, String frm, String to,
			String sortorder, String sortCol) {
		final List<Predicate> predicateList = new ArrayList<>();
		return (root, query, cb) -> {
			if (sortorder.equalsIgnoreCase("DSC"))
				query.orderBy(cb.desc(root.get(sortCol)));
			else if (sortorder.equalsIgnoreCase("ASC"))
				query.orderBy(cb.asc(root.get(sortCol)));
			else
				query.orderBy(cb.asc(root.get("createdDate")));
			if (StringUtils.isEmpty(frm)) {
				return null;
			}
			if (StringUtils.isEmpty(to)) {
				return null;
			}
			try {
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date dateValueFrm = formatter.parse(frm);
				Date dateValueTo = formatter.parse(to);
				predicateList.add(cb.between(cb.function("TRUNC", Date.class, root.get("createdDate")), dateValueFrm, dateValueTo));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		};
	}

	private static Specification<DoAuthRequest> userAttributeContains(String attribute, String value, String sortorder,
			String sortCol) {
		final List<Predicate> predicateList = new ArrayList<>();
		return (root, query, cb) -> {
		
			if (sortorder.equalsIgnoreCase("DSC")) {
				if (sortCol.equalsIgnoreCase("bolNumber"))
					query.orderBy(cb.desc(root.join(attribute).get(sortCol)));
				else
					query.orderBy(cb.desc(root.get(sortCol)));
			} else if (sortorder.equalsIgnoreCase("ASC")) {
				if (sortCol.equalsIgnoreCase("bolNumber"))
					query.orderBy(cb.asc(root.join(attribute).get(sortCol)));
				else
					query.orderBy(cb.asc(root.get(sortCol)));
			} else {
				query.orderBy(cb.desc(root.get("isReturned")),(cb.desc(root.get("createdDate"))));
			}
			/*
			 * if (attribute.equals("invoiceStatus")){ if(value!=null)
			 * query.groupBy(root.join("bol").join("bolInvoices").get("invoiceNumber")); }
			 */
			query.distinct(true);
			if (StringUtils.isEmpty(value)) {
				return null;
			}

			if (attribute.equals("bol")) {

				predicateList.add(cb.equal(cb.lower(root.join(attribute).get("bolNumber")), value.toLowerCase())

				);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else 	if (attribute.equals("invoiceStatus")) {

				predicateList.add(cb.equal((root.join("bol").join("bolInvoices").get("status")), value)


				);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} 
			else if (attribute.equals("all")) {
				predicateList.add(cb.equal(

						root.join("bol").join("bolDetails").get("shippingAgentCode"), value

				));
				// System.out.println(predicateList.size());
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equals("status")) {
			
				/*predicateList.add(cb.equal(

						root.get(attribute), value

				));*/

				List<String> stsList = Arrays.asList(value.split("\\s*,\\s*"));
				predicateList.add(

						stsList.isEmpty() ? cb.conjunction() : root.get(attribute).in(stsList)

				);
				
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equalsIgnoreCase("frm")) {

				if (StringUtils.isEmpty(value)) {
					return null;
				}
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				//Date dateValue = formatter.parse(value);
				Date dateValue =DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
				predicateList.add(cb.greaterThanOrEqualTo
						(cb.function("TRUNC", Date.class, root.get("createdDate")), dateValue));
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			} else if (attribute.equalsIgnoreCase("to")) {
				if (StringUtils.isEmpty(value)) {
					return null;
				}
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				//Date dateValue = formatter.parse(value);
				Date dateValue =DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
				predicateList.add(cb.lessThanOrEqualTo(cb.function("TRUNC", Date.class, root.get("createdDate")), 
						dateValue));
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


	public static Specification<DoAuthRequest> getspecification(String doReqId) {
		final List<Predicate> predicateList = new ArrayList<>();
		return ((root, query, cb) -> {
			predicateList.add( cb.equal(root.get("doAuthRequestId"), doReqId));
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		});
	}
	
	public static Specification<DoAuthRequest> getspecification(Long doAuthReqId) {
		final List<Predicate> predicateList = new ArrayList<>();
		return ((root, query, cb) -> {
			predicateList.add( cb.equal(root.get("doAuthRequestId"), doAuthReqId));
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		});
	}


}
