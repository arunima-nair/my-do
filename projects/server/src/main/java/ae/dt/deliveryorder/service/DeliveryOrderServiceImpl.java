package ae.dt.deliveryorder.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ae.dt.common.util.DateUtil;
import ae.dt.common.util.EncryptionUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ae.dt.common.constants.Constants;
import ae.dt.common.domain.DTAgentView;
import ae.dt.common.dto.DataDto;
import ae.dt.common.dto.MailDTO;
import ae.dt.common.dto.PartyDetailsDTO;
import ae.dt.common.dto.ResponseRosoomDto;
import ae.dt.common.dto.SelectDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.dto.ViewDTO;
import ae.dt.common.util.Utilities;
import ae.dt.deliveryorder.domain.ApprovalLog;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.CodeMaster;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.Credit;
import ae.dt.deliveryorder.domain.DTAdminEmailView;
import ae.dt.deliveryorder.domain.DTUserView;
import ae.dt.deliveryorder.domain.DeliveryOrder;
import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.DoAuthRequestAudit;
import ae.dt.deliveryorder.domain.Document;
import ae.dt.deliveryorder.domain.Initiator;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.domain.PaymentOption;
import ae.dt.deliveryorder.domain.RejectionRemark;
import ae.dt.deliveryorder.domain.ReturnRemark;
import ae.dt.deliveryorder.domain.Rosoom;
import ae.dt.deliveryorder.domain.SAInitiatorPayment;
import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.domain.ShippingAgentAttributes;
import ae.dt.deliveryorder.dto.ApprovalLogDTO;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.CollectionDTO;
import ae.dt.deliveryorder.dto.CreditDTO;
import ae.dt.deliveryorder.dto.DeliveryOrderDTO;
import ae.dt.deliveryorder.dto.DoAuthDocDTO;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.DocDTO;
import ae.dt.deliveryorder.dto.DocumentDTO;
import ae.dt.deliveryorder.dto.FileProcessDTO;
import ae.dt.deliveryorder.dto.InvoiceTypeDTO;
import ae.dt.deliveryorder.dto.PaymentDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
import ae.dt.deliveryorder.dto.PaymentOfProofDTO;
import ae.dt.deliveryorder.dto.RejectionRemarksDTO;
import ae.dt.deliveryorder.dto.ReturnRemarksDTO;
import ae.dt.deliveryorder.dto.RosoomDTO;
import ae.dt.deliveryorder.dto.SARosoomDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import ae.dt.deliveryorder.facade.BillOfLaddingFacade;
import ae.dt.deliveryorder.mapper.BoLMapper;
import ae.dt.deliveryorder.mapper.CollectionsMapper;
import ae.dt.deliveryorder.mapper.DeliveryOderMapper;
import ae.dt.deliveryorder.mapper.DoAuthDocsMapper;
import ae.dt.deliveryorder.mapper.DoAuthRequestMapper;
import ae.dt.deliveryorder.mapper.DocumentMapper;
import ae.dt.deliveryorder.mapper.PaymentLogMapper;
import ae.dt.deliveryorder.mapper.RejectionRemarksMapper;
import ae.dt.deliveryorder.mapper.ReturnRemarksMapper;
import ae.dt.deliveryorder.mapper.SARosoomMapper;
import ae.dt.deliveryorder.repository.ApprovalLogRepository;
import ae.dt.deliveryorder.repository.BolDetailsRepository;
import ae.dt.deliveryorder.repository.BolInvoiceRepository;
import ae.dt.deliveryorder.repository.BolRepository;
import ae.dt.deliveryorder.repository.CodeMasterRepository;
import ae.dt.deliveryorder.repository.CollectionRepository;
import ae.dt.deliveryorder.repository.DTAdminEmailViewRepository;
import ae.dt.deliveryorder.repository.DTAgentViewRepository;
import ae.dt.deliveryorder.repository.DTUserViewRepository;
import ae.dt.deliveryorder.repository.DeliveryOrderRepository;
import ae.dt.deliveryorder.repository.DoAuthDocRepository;
import ae.dt.deliveryorder.repository.DoAuthRequestAuditRepository;
import ae.dt.deliveryorder.repository.DoAuthRequestRepository;
import ae.dt.deliveryorder.repository.DocumentRepository;
import ae.dt.deliveryorder.repository.InitiatorRepository;
import ae.dt.deliveryorder.repository.PaymentLogRepository;
import ae.dt.deliveryorder.repository.PaymentOptionRepository;
import ae.dt.deliveryorder.repository.PaymentProofRepository;
import ae.dt.deliveryorder.repository.RejectionRemarkRepository;
import ae.dt.deliveryorder.repository.ReturnRemarkRepository;
import ae.dt.deliveryorder.repository.SAInitiatorPaymentRepository;
import ae.dt.deliveryorder.repository.SARosoomRepository;
import ae.dt.deliveryorder.repository.ShippingAgentAttributesRepository;
import ae.dt.deliveryorder.repository.ShippingAgentRepository;
import ae.dt.deliveryorder.specification.BillOfLaddingSpecification;
import ae.dt.deliveryorder.specification.DoAuthRequestSpecification;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *  2/10/2019.
 */

/**
 * @author ARUNIMA NAIR
 *
 */
@Service("deliveryOrderService")
// @Transactional
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

	@Autowired
	DoAuthRequestMapper doAuthRequestMapper;
	@Autowired
	DocumentMapper documentMapper;
	@Autowired
	CollectionsMapper collectionMapper;
	@Autowired
	RejectionRemarksMapper rejectionRemarksMapper;
	@Autowired
	BoLMapper bolMapper;
	@Autowired
	DoAuthRequestRepository doAuthRequestRepository;
	@Autowired
	DoAuthDocRepository doAuthDocRepository;
	@Autowired
	DoAuthRequestAuditRepository doAuthRequestAuditRepository;
	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	PaymentLogRepository paymentLogRepository;
	@Autowired
	PaymentLogMapper paymentLogMapper;
	@Autowired
	CollectionRepository collectionRepository;
	@Autowired
	ApprovalLogRepository approvalLogRepository;
	@Autowired
	RejectionRemarkRepository rejectionRemarkRepository;
	@Autowired
	ReturnRemarkRepository returnRemarkRepository;
	@Autowired
	DoAuthDocsMapper doAuthDocsMapper;
	@Autowired
	ReturnRemarksMapper returnRemarksMapper;
	@Autowired
	BolRepository bolRepository;
	@Autowired
	BolInvoiceRepository bolInvoiceRepository;
	@Autowired
	DeliveryOrderRepository deliveryOrderRepository;
	@Autowired
	DTAgentViewRepository dTAgentViewRepository;
	@Autowired
	DTAdminEmailViewRepository dTAdminEmailViewRepository;
	@Autowired
	PaymentOptionRepository paymentOptionRepository;
	@Autowired
	BillOfLaddingService bolService;
	@Autowired
	EmailNotificationService emailNotificationService;
	@Autowired
	PaymentProofRepository paymentProofRepository;
	@Autowired
	ShippingAgentRepository shippingAgentRepository;
	@Autowired
	SARosoomRepository sARosoomRepository;
	@Autowired
	SARosoomMapper sARosoomMapper;

	@Autowired
	CodeMasterRepository codeMasterRepository;
	@Autowired
	InitiatorRepository initiatorRepository;
	@Autowired
	SAInitiatorPaymentRepository sAInitiatorPaymentRepository;

	@Autowired
	DTUserViewRepository dTUserViewRepository;
	@Autowired
	BolDetailsRepository bolDetailRepository;

	@Value("${enquire.pending.request.time}")
	private String enquirePendingTime;

	@Value("${paymentProof}")
	private String paymentProof;

	@Value("${cc.email}")
	private String ccEmail;

	@Value("${DOfilePath}")
	private String dOfilePath;

	@Autowired
	private BillOfLaddingFacade billOfLaddingFacade;

	@Autowired
	private DeliveryOderMapper deliveryOderMapper;

	@Autowired
	ShippingAgentAttributesRepository shippingAgentAttributesRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<DoAuthRequest> getDOrequestDetails(SearchDTO searchDTO, Pageable pageable, UserDTO userDTO,
			String sortorder, String sortCol) {
		Page<DoAuthRequest> pageDoAuthRequestlist = doAuthRequestRepository
				.findAll(new DoAuthRequestSpecification().getFilter(searchDTO, userDTO, sortorder, sortCol), pageable);

		return pageDoAuthRequestlist;
	}

	@Override
	@Transactional
	public DoAuthRequest updateAfterPayment(PaymentDTO paymentDTO, String custRefNo, String statusParam,
			String agentCode) {

		List<PaymentLog> paylog = paymentLogRepository.findByTransactionId(paymentDTO.getSoTransactionId());
		DoAuthRequest dr = null;
		if (paylog != null) {
			DoAuthRequest doAuthRequest = paylog.get(0).getDoAuthRequest();

			paymentDTO.setCreatedBy(doAuthRequest.getCreatedBy());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Bol savedBol = doAuthRequest.getBol();
			Set<BolInvoice> savedBolInvoice = savedBol.getBolInvoices();
			Set<Collection> oldCollectionSet = doAuthRequest.getCollections();
			Double payingAmt = 0D;
			String partialPayAllow = "N";
			Double paidAmount = 0D;
			List<BolInvoice> savedBolInvSet = bolInvoiceRepository.findByBolBolNumber(savedBol.getBolNumber());

			SAInitiatorPayment sAInitiatorPayment = sAInitiatorPaymentRepository
					.findByShippingAgentShippingAgentIdAndInitiatorInitiatorId(
							paylog.get(0).getShippingAgent().getShippingAgentId(), paylog.get(0).getInitiatorId());
			if (sAInitiatorPayment != null)
				partialPayAllow = sAInitiatorPayment.getPaymentAllowed();

			if (statusParam.equals("1")) {

				Set<Collection> collectionset = new HashSet();
				List<BolInvoice> bolInvList = new ArrayList();

				Long receiptSeq = collectionRepository.getNextReceiptNo();
				String receiptSeqVal = new StringBuffer(Constants.RECEIPT_PREFIX).append(receiptSeq).toString();
				for (PaymentLog payLog : doAuthRequest.getPaymentLogs()) {
					if (paymentDTO.getSoTransactionId().equals(payLog.getTransactionId())) {
						if (oldCollectionSet != null)
							if (oldCollectionSet.stream().anyMatch(col -> col.getBolInvoice().getBolInvoiceId()
									.equals(payLog.getBolInvoice().getBolInvoiceId()))) {
								continue;
							} else
								bolInvList.add(payLog.getBolInvoice());

						Collection collection = new Collection();
						collection.setStatus("SUCCESS");
						collection.setDoAuthRequest(doAuthRequest);
						collection.setCollectionType("ONLINE_PAYMENT");

						collection.setReceiptNo(receiptSeqVal);
						collection.setTransactionId(paymentDTO.getSoTransactionId());
						collection.setAmount(payLog.getBolInvoice().getInvoiceValue().toString());
						payingAmt = payingAmt + payLog.getBolInvoice().getInvoiceValue();
						collection.setCreatedBy(paymentDTO.getCreatedBy());
						Rosoom rosoom = new Rosoom();
						rosoom.setFiInstrument(paymentDTO.getFiInstrument());
						try {
							rosoom.setFiDate(formatter.parse(paymentDTO.getFiDate()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						rosoom.setFiTransactionId(paymentDTO.getFiTransactionId());
						rosoom.setGatewayTransactionId(paymentDTO.getGatewayTransId());
						rosoom.setPaymentAmount(payLog.getBolInvoice().getInvoiceValue());
						rosoom.setCreatedBy(collection.getCreatedBy());
						rosoom.setCollection(collection);
						Set<Rosoom> rs = new HashSet();
						rs.add(rosoom);
						collection.setRosooms(rs);
						collection.setBolInvoice(payLog.getBolInvoice());
						collection.setInvoiceNumber(payLog.getBolInvoice().getInvoiceNumber());
						collection.setPayStatus(payLog.getPayStatus());
						// if(!doAuthRequest.getStatus().equalsIgnoreCase(Constants.DO_STATUS.PARTIAL_PAYMENT.value))

						doAuthRequest.getPaymentLogs().stream().map(paymentLog -> {
							for (PaymentLog paymentlog : paylog) {
								if (paymentlog.getPaymentLogId() == paymentLog.getPaymentLogId()) {
									paymentLog.setStatus("SUCCESS");
									// paymentLog.setPaidBy(agentCode);
									paymentLog.setModifiedBy(paymentDTO.getCreatedBy());
									paymentLog.setModifiedDate(new Date());
									if (savedBol.getBolInvoices().stream().map(savedBolInv -> {
										if (savedBolInv.getBolInvoiceId()
												.equals(paymentLog.getBolInvoice().getBolInvoiceId()))
											savedBolInv.setStatus(Constants.BOLINVOICE_STATUS.PAYMENT_SUCCESS.value);
										return savedBolInv;
									}).collect(Collectors.toSet()) != null)
										;
								}
							}
							return paymentLog;
						}).collect(Collectors.toSet());
						collectionset.add(collection);
					}
					doAuthRequest.setCollections(collectionset);

				}
				if (partialPayAllow.equalsIgnoreCase("N")) {
					Boolean isPartialPayment = isPartialPayment(doAuthRequest, payingAmt, paidAmount);
					if (!isPartialPayment) {
						doAuthRequest.setStatus((Constants.DO_STATUS.PENDING_FOR_APPROVAL.value));
						savedBol.setStatus(Constants.BOL_STATUS.INITIATED.value);
					} else {
						Boolean payImpFlag = false;
						for (BolInvoice bolInvoice : savedBolInvSet) {
							if (bolInvoice.getStatus() != null)
								if (bolInvoice.getStatus().equalsIgnoreCase(
										Constants.BOLINVOICE_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value)) {
									payImpFlag = true;
									break;
								}
						}
						if (payImpFlag) {

							doAuthRequest.setStatus(Constants.DO_STATUS.DO_INITIATED.value);

						} else {
							doAuthRequest.setStatus(Constants.DO_STATUS.PARTIAL_PAYMENT.value);
							savedBol.setStatus(Constants.BOL_STATUS.PARTIAL_PAYMENT.value);
						}

					}
				} else {
					doAuthRequest.setStatus((Constants.DO_STATUS.PENDING_FOR_APPROVAL.value));
					savedBol.setStatus(Constants.BOL_STATUS.INITIATED.value);
				}
			} else {
				doAuthRequest.setStatus(Constants.DO_STATUS.DO_INITIATED.value);
				savedBol.setStatus(Constants.BOL_STATUS.INITIATED.value);
				Iterator<PaymentLog> iterator = doAuthRequest.getPaymentLogs().iterator();
				while (iterator.hasNext()) {
					PaymentLog element = iterator.next();
					for (PaymentLog paymentlog : paylog) {
						if (paymentlog.getPaymentLogId() == element.getPaymentLogId()) {
							element.setStatus("FAILED");
							element.setModifiedBy(paymentDTO.getCreatedBy());
							element.setModifiedDate(new Date());
							if (savedBol.getBolInvoices().stream().map(savedBolInv -> {
								if (savedBolInv.getBolInvoiceId().equals(paymentlog.getBolInvoice().getBolInvoiceId()))
									savedBolInv.setStatus(Constants.BOLINVOICE_STATUS.PAYMENT_FAILED.value);
								savedBolInv.setModifiedBy(paymentDTO.getCreatedBy());
								savedBolInv.setModifiedDate(new Date());
								return savedBolInv;
							}).collect(Collectors.toSet()) != null)
								;
						}

					}

				}

			}

			doAuthRequest.setModifiedBy(paymentDTO.getCreatedBy());
			doAuthRequest.setModifiedDate(new Date());
			/****************************************/

			Set<DoAuthRequest> doAuthRequestSet = Stream.of(doAuthRequest)
					.collect(Collectors.toCollection(HashSet::new));
			savedBol.setDoAuthRequests(doAuthRequestSet);
			Bol bol = bolRepository.save(savedBol);
			bolRepository.flush();

			if (bol != null) {
				dr = bol.getDoAuthRequests().stream().filter(doauth -> doauth != null).map(doauth -> doauth).findAny()
						.orElse(null);

				if (dr != null) {

					if (dr.getStatus().equalsIgnoreCase("PENDING_FOR_APPROVAL")) {
						try {
							Map<String, String> mailContentMap = new HashMap<String, String>();
							String image = getClass().getResource("/images/email_header.png").toString();
							mailContentMap.put("imageURL", image);
							String head = "Request Delivery Order with Do Reference No:  " + dr.getDoRefNo()
									+ " initiated.";
							mailContentMap.put("head", head);
							mailContentMap.put("detail", "");
							mailContentMap.put("bol", dr.getBol().getBolNumber());

							List<String> subIds = new ArrayList<>();
							subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);
							subIds.add(Constants.AGENT_TYPE.SHIPPING_AGENT.value);

							String shippingAgentCode = dr.getBol().getBolDetails().stream()
									.filter(detail -> detail.getShippingAgentCode() != null)
									.map(detail -> detail.getShippingAgentCode()).findAny().orElse(null);
							String bolPartyEmail = dr.getBolEmail();
							String doPartyEmail = dr.getDoEmail();
//							String shippingAgentEmailId = getAdminEmailByAgentCode(shippingAgentCode, subIds);
							String shippingAgentEmailId = billOfLaddingFacade.getShippingAgentEmailId(agentCode);
							String[] listofCCMail = ccEmail.split(";");
							if (!shippingAgentEmailId.isEmpty()) {
								String emails = bolPartyEmail;
								String reqParty = dr.getReqPartyName();
								mailContentMap.put("reqParty", reqParty);
								mailContentMap.put("detail", "Please proceed to further action.");
								if (doPartyEmail != null) {
									emails = emails + "," + doPartyEmail;
								}

								if (shippingAgentEmailId != null) {
									emailNotificationService.sendMail(new MailDTO().builder()
											.toAddress(shippingAgentEmailId).ccAddress(listofCCMail)
											.subject("Request DO").mailContentMap(mailContentMap).build(),
											"DO_REQUEST");
								} else {
									emailNotificationService.sendMail(
											new MailDTO().builder().toAddress(emails).ccAddress(listofCCMail)
													.subject("Request DO").mailContentMap(mailContentMap).build(),
											"DO_REQUEST");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return dr;
	}

	@Transactional
	public Boolean isPartialPayment(DoAuthRequest doAuthRequest, Double payingAmt, double paidAmount) {
		Double totalAmt = 0D;
		for (BolInvoice bolInvoice : doAuthRequest.getBol().getBolInvoices()) {
			totalAmt = totalAmt + Double.valueOf(bolInvoice.getInvoiceValue());
		}
		if (paidAmount == 0D)
			paidAmount = 0D;
		if (totalAmt - (paidAmount + payingAmt) > 0)
			return true;
		else
			return false;

	}

	@Override
	@Transactional
	public String approveDO(DoAuthRequest req) {

		DoAuthRequest dreq = doAuthRequestRepository.save(req);
		if (dreq != null)
			return "success";
		else
			return null;

	}

	@Override
	public DoAuthRequest updateCollectionRosoom(PaymentDTO paymentDTO, String custRefNo, String statusParam,
			String agentCode) {

		return updateAfterPayment(paymentDTO, custRefNo, statusParam, agentCode);

	}

	@Override
	@Transactional
	public String updateLog(String id, String remarkId, String otherComments, UserDTO userDTO) {
		DoAuthRequest dreq = doAuthRequestRepository.findById(Long.valueOf(id));
		String status = dreq.getStatus();

		ApprovalLog approvalLog = null;
		if (status.equalsIgnoreCase("REJECTED")) {
			RejectionRemark remark = new RejectionRemark();
			remark = rejectionRemarkRepository.findById(Long.valueOf(remarkId));
			approvalLog = new ApprovalLog().builder().createdBy(userDTO.getUserName()).status(status)
					.rejectionRemark(remark).comments(otherComments).doAuthRequest(dreq).build();
		} else if (status.equalsIgnoreCase("RETURNED")) {
			ReturnRemark remark = new ReturnRemark();
			remark = returnRemarkRepository.findById(Long.valueOf(remarkId));
			approvalLog = new ApprovalLog().builder().createdBy(userDTO.getUserName()).status(status)
					.returnRemark(remark).comments(otherComments).doAuthRequest(dreq).build();
		} else {
			approvalLog = new ApprovalLog().builder().createdBy(userDTO.getUserName()).status(status)
					.doAuthRequest(dreq).build();
		}
		if (approvalLog != null) {
			ApprovalLog log = approvalLogRepository.save(approvalLog);
			approvalLogRepository.flush();
			if (log != null) {
				if (dreq != null) {
					if (dreq.getBol().getStatus().equalsIgnoreCase(Constants.BOL_STATUS.COMPLETED.value)) {
						sendEmailForApprovedStatus(dreq);
					} else if (dreq.getBol().getStatus().equalsIgnoreCase(Constants.BOL_STATUS.RETURNED.value)
							|| dreq.getBol().getStatus().equalsIgnoreCase(Constants.BOL_STATUS.REJECTED.value)) {
						sendEmailForReturnRejectionStatus(dreq);
					} else {
						Map<String, String> mailContentMap = new HashMap<String, String>();
						String image = getClass().getResource("/images/email_header.png").toString();
						mailContentMap.put("imageURL", image);
						String head = "Request Delivery Order with Do Reference No:  " + dreq.getDoRefNo() + " is "
								+ dreq.getStatus();
						mailContentMap.put("head", head);
						mailContentMap.put("bol", dreq.getBol().getBolNumber());
						mailContentMap.put("doref", dreq.getDoRefNo());
						mailContentMap.put("status", dreq.getBol().getStatus());
						String email = dreq.getReqEmail();
						String cc = dreq.getBolEmail();
						if (dreq.getDoEmail() != null)
							cc = dreq.getBolEmail() + ";" + dreq.getDoEmail();
						String[] ccList = cc.split(";");
						String subject = "Approve DO";
						String[] listofCCMail = ccEmail.split(";");

						if (dreq.getStatus().equalsIgnoreCase("COMPLETED")
								|| dreq.getStatus().equalsIgnoreCase("PENDING_DO")) {

							if (dreq.getDeliveryOrder().getDocumentPath() != null) {
								mailContentMap.put("detail", "Approved. Please download DO.");
								head = "Request Delivery Order with Do Reference No:  " + dreq.getDoRefNo()
										+ " is Approved";
								mailContentMap.put("head", head);
								subject = "Approve DO";
							} else {
								head = "Request Delivery Order with Do Reference No:  " + dreq.getDoRefNo()
										+ " is Approved with pending DO Attachment";
								mailContentMap.put("head", head);
								mailContentMap.put("detail", "Approved. DO attachment pending.");
								subject = "Pending DO";
							}
							try {
								mailContentMap.put("remarks", "");
								mailContentMap.put("comments", "");

								mailContentMap.put("doref", dreq.getDoRefNo());
								emailNotificationService.sendMail(new MailDTO().builder().toAddress(email)
										.ccAddress(listofCCMail).subject(subject).ccAddress(ccList)
										.mailContentMap(mailContentMap).build(), "DO_PROCESSED");
							} catch (Exception e) {

							}
							return head;
						} else {
							mailContentMap.put("detail", dreq.getStatus());
							mailContentMap.put("remarks", "");
							mailContentMap.put("comments", "");
							try {
								if (dreq.getStatus().equalsIgnoreCase("RETURNED")) {
									subject = "Return DO";
									for (ApprovalLog alog : dreq.getApprovalLog()) {
										if (alog.getReturnRemark().getReturnRemarksId() != null) {
											mailContentMap.put("remarks",
													"REMARKS: " + alog.getReturnRemark().getRemarks());
											mailContentMap.put("comments", "COMMENTS: " + alog.getComments());
										}
									}

								}
								if (dreq.getStatus().equalsIgnoreCase("REJECTED")) {
									subject = "Reject DO";
									for (ApprovalLog alog : dreq.getApprovalLog()) {
										if (log.getRejectionRemark().getRejectionRemarksId() != null) {
											mailContentMap.put("remarks",
													"REMARKS: " + alog.getRejectionRemark().getRemarks());
											mailContentMap.put("comments", "COMMENTS: " + alog.getComments());
										}
									}
									// mailContentMap.put("remarks",
									// req.getApprovalLog().stream().map(remaks->remarks));
								}
								emailNotificationService
										.sendMail(
												new MailDTO().builder().toAddress(email).subject(subject)
														.ccAddress(ccList).mailContentMap(mailContentMap).build(),
												"DO_PROCESSED");
							} catch (Exception e) {
								e.printStackTrace();
							}
							return dreq.getStatus();
						}

					}
				}
				return log.getStatus();
			} else
				return "FAILED";
		}
		return "FAILED";
	}

	private void sendEmailForReturnRejectionStatus(DoAuthRequest dreq) {
		Map<String, String> mailContentMap = new HashMap<String, String>();

		String image = getClass().getResource("/images/email_header.png").toString();
		mailContentMap.put("imageURL", image);
		String head = "Request Delivery Order with Do Reference No:  " + dreq.getDoRefNo() + " is " + dreq.getStatus();
		mailContentMap.put("head", head);
		mailContentMap.put("doref", dreq.getDoRefNo());
		mailContentMap.put("status", dreq.getStatus());
		String email = dreq.getReqEmail();
		String cc = dreq.getBolEmail();
		if (dreq.getDoEmail() != null)
			cc = dreq.getBolEmail() + ";" + dreq.getDoEmail();
		String[] ccList = cc.split(";");
		String subject = "DO with RefNo: " + dreq.getDoRefNo() + " is " + dreq.getStatus();
		String[] listofCCMail = ccEmail.split(";");
		String[] bothCCMail = (String[]) ArrayUtils.addAll(listofCCMail, ccList);
		String reqName = dreq.getReqPartyName();
		mailContentMap.put("reqName", reqName);
		for (ApprovalLog alog : dreq.getApprovalLog()) {
			if (dreq.getBol().getStatus().equalsIgnoreCase(Constants.BOL_STATUS.RETURNED.value)) {
				if (alog.getReturnRemark() != null) {
					mailContentMap.put("remarks", "REMARKS: " + alog.getReturnRemark().getRemarks());
					mailContentMap.put("comments", "COMMENTS: " + alog.getComments());
				}
			} else if (dreq.getBol().getStatus().equalsIgnoreCase(Constants.BOL_STATUS.REJECTED.value)) {
				if (alog.getRejectionRemark() != null) {
					mailContentMap.put("remarks", "REMARKS: " + alog.getRejectionRemark().getRemarks());
					mailContentMap.put("comments", "COMMENTS: " + alog.getComments());
				}
			}
		}
		emailNotificationService.sendMail(new MailDTO().builder().toAddress(email).subject(subject)
				.ccAddress(bothCCMail).mailContentMap(mailContentMap).build(),
				Constants.EMAIL_TYPE.REJECT_RETURN_DO.value);

	}

	private void sendEmailForApprovedStatus(DoAuthRequest dreq) {
		Map<String, String> mailContentMap = new HashMap<String, String>();
		String image = getClass().getResource("/images/email_header.png").toString();
		mailContentMap.put("imageURL", image);
		String head = "Request Delivery Order with Do Reference No:  " + dreq.getDoRefNo() + " is " + dreq.getStatus();
		mailContentMap.put("head", head);
		mailContentMap.put("doref", dreq.getDoRefNo());
		String email = dreq.getReqEmail();
		String cc = dreq.getBolEmail();
		if (dreq.getDoEmail() != null)
			cc = dreq.getBolEmail() + ";" + dreq.getDoEmail();
		String[] ccList = cc.split(";");
		String subject = "Approve DO";
		String[] listofCCMail = ccEmail.split(";");
		String reqName = dreq.getReqPartyName();
		mailContentMap.put("reqName", reqName);
		subject = "Approve DO";
		emailNotificationService.sendMail(new MailDTO().builder().toAddress(email).ccAddress(listofCCMail)
				.subject(subject).ccAddress(ccList).mailContentMap(mailContentMap).build(), "DO_APPROVED");
	}

	@Override
	@Transactional(readOnly = true)
	public DoAuthRequest getDoAuthrequest(Long doAuthRequestId) {
		return doAuthRequestRepository.findById(doAuthRequestId);

	}

	@Override
	@Transactional(readOnly = true)
	public List<RejectionRemarksDTO> getRejectionRemarks() {
		Iterable<RejectionRemark> r = rejectionRemarkRepository.findAll();
		List<RejectionRemarksDTO> target = new ArrayList<>();
		r.forEach(s -> {
			target.add(rejectionRemarksMapper.domaintoDTO(s));
		});

		return target;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReturnRemarksDTO> getReturnRemarks() {
		Iterable<ReturnRemark> r = returnRemarkRepository.findAll();
		List<ReturnRemarksDTO> target = new ArrayList<>();
		r.forEach(s -> {
			target.add(returnRemarksMapper.domaintoDTO(s));
		});

		return target;
	}

	@Override
	@Transactional(readOnly = true)
	public String getAuthDocumentPathById(Long authDocId) {
		DoAuthDoc doAuthDoc = doAuthDocRepository.findByDoAuthDocsId(authDocId);
		if (null != doAuthDoc) {
			return doAuthDoc.getDocumentPath();
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getDODocumentPathById(Long authReqId) {
		DeliveryOrder doAuthDoc = deliveryOrderRepository.findByAuthReqId(authReqId);
		if (null != doAuthDoc) {
			return doAuthDoc.getDocumentPath();
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public byte[] generateConsigneeReport(SearchDTO dto, UserDTO userDTO, HttpServletResponse response)
			throws JRException, FileNotFoundException, IOException, ParseException {
		byte[] bytes = null;
		Pageable pageable = null;
		String sortorder = "";
		String sortCol = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		List<ViewDTO> viewList = new ArrayList<ViewDTO>();
		String createdBy = userDTO.getAgentCode();
		String agentName = null;
		String agentCode = null;
		String impCode = "";
		String impName = "";
		String paymentType = null;
		String status = null;
		status = dto.getStatus().isEmpty() || dto.getStatus() == "CHOOSE" ? null : dto.getStatus();
		// Fetch data between the dates
		List<Bol> bolList = null;
		if (status.equalsIgnoreCase("CHOOSE"))
			dto.setStatus("");
		if (userDTO.getUserType().equalsIgnoreCase("A")) {
			dto.setShippingAgentCode(userDTO.getAgentCode());
		} else if (userDTO.getUserType().equalsIgnoreCase("I")) {
			dto.setImpCode(userDTO.getAgentCode());
		} else if ((userDTO.getUserType().equalsIgnoreCase("IN"))) {
			if (StringUtils.equalsIgnoreCase(dto.getShippingAgentCode(), "undefined")) {
				dto.setShippingAgentCode(null);
			}
			if (StringUtils.equalsIgnoreCase(dto.getImpCode(), "undefined")) {
				dto.setImpCode(null);
			}
		}
		bolList = bolRepository.findAll(new BillOfLaddingSpecification().getFilterInternal(dto, userDTO));
		// Process With Data
		if (!bolList.isEmpty()) {
			final JasperReport report;
			if (userDTO.getUserType().equalsIgnoreCase("I")) {
				report = loadTemplate("consigneeIN");
			} else {
				report = loadTemplate("consigneeSA");
			}
			if (report != null) {
				for (Bol bol : bolList) {
					if (!bol.getBolInvoices().isEmpty()) {
						for (BolInvoice bolInvoice : bol.getBolInvoices()) {
							ViewDTO viewDto = new ViewDTO();
							viewDto.setInvoiceNumber(bolInvoice.getInvoiceNumber());
							viewDto.setInvoiceValue(bolInvoice.getInvoiceValue());

							viewDto.setBolNumber(bol.getBolNumber());
							viewDto.setBolType(bol.getBolType());
							viewDto.setStatus(bol.getStatus());
							viewDto.setCreatedDate(bol.getCreatedDate());

							for (Collection collection : bolInvoice.getCollections()) {
								viewDto.setTransactionId(collection.getTransactionId());
								viewDto.setPayMethod(collection.getCollectionType());
								viewDto.setCurrency(collection.getBolInvoice().getCurrency());
								String payemntStatus = collection.getStatus().equalsIgnoreCase("SUCCESS") ? "Paid"
										: "Not Paid";
								viewDto.setPaymentStatus(payemntStatus);
								if (!collection.getRosooms().isEmpty()) {
									for (Rosoom rosoom : collection.getRosooms()) {
										String fiInstrument = rosoom.getFiInstrument().substring(0, 3);
										// CodeMaster codeMaster =
										// codeMasterRepository.findByCodeMasterValue(fiInstrument);
										// if (null != codeMaster)
										// viewDto.setFiInstrument(codeMaster.getCodeMasterDesc());
										paymentType = fiInstrument.substring(fiInstrument.length() - 1);
										if (!paymentType.isEmpty()) {
											if (paymentType == "M")
												// viewDto.put("paymentType", Constants.PAYMENT_TYPE.M.value);
												viewDto.setFiInstrument(Constants.PAYMENT_TYPE.M.value);
											else
												// viewDto.put("paymentType", Constants.PAYMENT_TYPE.V.value);
												viewDto.setFiInstrument(Constants.PAYMENT_TYPE.V.value);
										}

									}
								}
							}

							if (!bol.getBolDetails().isEmpty()) {
								for (BolDetail bolDetail : bol.getBolDetails()) {
									viewDto.setShippingAgentName(bolDetail.getShippingAgentName());
									if (!StringUtils.isEmpty(dto.getShippingAgentCode())) {
										agentName = bolDetail.getShippingAgentName();
										agentCode = bolDetail.getShippingAgentCode();
									}
								}
							}

							if (!bol.getDoAuthRequests().isEmpty()) {
								for (DoAuthRequest authRequest : bol.getDoAuthRequests()) {
									viewDto.setDoRefNo(authRequest.getDoRefNo());
									// viewDto.setStatus(authRequest.getStatus());
									if (authRequest.getBolPartyName() != null)
										viewDto.setBolPartyName(
												authRequest.getBolPartyName().split(Pattern.quote("-"))[1]);
									if (authRequest.getReqPartyName() != null) {
										viewDto.setReqPartyName(
												authRequest.getReqPartyName().split(Pattern.quote("-"))[1]);
										if (!StringUtils.isEmpty(dto.getImpCode())) {
											impCode = authRequest.getReqPartyName().split(Pattern.quote("-"))[0];
											impName = authRequest.getReqPartyName().split(Pattern.quote("-"))[1];
										}
									}
									if (authRequest.getInitiatorCode() != null) {
										viewDto.setReqCode(authRequest.getInitiatorCode());
									}
									if (authRequest.getInitiatorType() != null) {
										viewDto.setReqType(
												Constants.USER_TYPE.valueOf(authRequest.getInitiatorType()).value);
									}
									if (authRequest.getDoPartyName() != null) {
										viewDto.setDoPartyName(
												authRequest.getDoPartyName().split(Pattern.quote("-"))[1]);
									}
									viewDto.setBlPartyCode(authRequest.getBlPartyCode());
									viewDto.setDoPartyCode(authRequest.getDoPartyCode());
									viewDto.setReqPartyName(authRequest.getReqPartyName());
									viewDto.setBolPartyName(authRequest.getBolPartyName());
									viewDto.setDoPartyName(authRequest.getDoPartyName());
									viewDto.setDoCreatedBy(authRequest.getCreatedBy());
									viewDto.setDoAuthCreatedDate(authRequest.getCreatedDate());
									if (authRequest.getBlPartyType() != null) {
										try {
											viewDto.setBlPartyType(
													Constants.USER_TYPE.valueOf(authRequest.getBlPartyType()).value);
										} catch (Exception e) {

										}
									}
									if (authRequest.getDoPartyType() != null) {
										try {
											viewDto.setDoPartyType(
													Constants.USER_TYPE.valueOf(authRequest.getDoPartyType()).value);
										} catch (Exception e) {

										}
									}
									if (authRequest.getApprovalLog() != null) {
										for (ApprovalLog approvalLog : authRequest.getApprovalLog()) {
											if (authRequest.getStatus()
													.equalsIgnoreCase(Constants.BOL_STATUS.COMPLETED.value)) {
												viewDto.setApprovedBy(approvalLog.getCreatedBy());
												viewDto.setApprovedDate(approvalLog.getCreatedDate());
											}
											if (authRequest.getStatus()
													.equalsIgnoreCase(Constants.DO_STATUS.RETURNED.value)) {
												if (approvalLog.getReturnRemark() != null) {
													viewDto.setReturnedRemark(
															approvalLog.getReturnRemark().getRemarks());
												}
											}
										}
									}

								}

							}
							/*
							 * else { viewDto.setStatus(bol.getStatus()); }
							 */

							viewList.add(viewDto);
						}

					}

				}
			}

			// Jasper Report.
			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(viewList);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("listDataSource", dataSource);
			parameters.put("fromDate", formatter.parse(dto.getFrmDate()));
			parameters.put("toDate", formatter.parse(dto.getToDate()));
			parameters.put("agentCode", agentCode);
			parameters.put("agentName", agentName);
			parameters.put("impCode", impCode);
			parameters.put("impName", impName);
			parameters.put("logo", getClass().getResource("/images/logo_dt.png").toString());
			parameters.put("status", dto.getStatus());
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JRXlsExporter exporter = new JRXlsExporter();
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			exporter.exportReport();
			byte[] reportBytes = xlsReport.toByteArray();
			return reportBytes;
		}

		return null;

	}

	// Load jrxml template
	@Transactional
	private JasperReport loadTemplate(String reportName) throws JRException {

		InputStream reportInputStream = null;
		JasperDesign jasperDesign = null;
		if (reportName.equalsIgnoreCase("consigneeSA"))
			reportInputStream = getClass().getResourceAsStream("/templates/consigneeReport.jrxml");
		if (reportName.equalsIgnoreCase("consigneeIN"))
			reportInputStream = getClass().getResourceAsStream("/templates/consigneeReportIN.jrxml");
		else if (reportName.equalsIgnoreCase("reciept"))
			reportInputStream = getClass().getResourceAsStream("/templates/doReceipt.jrxml");
		if (reportInputStream != null) {
			jasperDesign = JRXmlLoader.load(reportInputStream);
			return JasperCompileManager.compileReport(jasperDesign);
		}

		return null;

	}

	@Override
	@Transactional
	public List<PaymentLog> fetchRosoomPendingPayments() {
		return paymentLogRepository.fetchRosoomPendingPayments(Double.valueOf(enquirePendingTime), Constants.PENDING);
	}

	@Override
	@Transactional
	public String savePayProof(DoAuthRequestDTO doAuthRequestDTO, DoAuthRequestDTO saveddataDTO, String file,
			HttpServletRequest httpServletRequest) throws IOException {
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		DoAuthRequest doAuthRequest = doAuthRequestRepository
				.findById(Long.valueOf(doAuthRequestDTO.getDoAuthRequestId()));
		Set<PaymentLog> paylog = doAuthRequest.getPaymentLogs();
		String msg = "Upload Payment Proof Succesfully updated. DO Reference No. ";
		String savedStatus = doAuthRequest.getStatus();
		Set<String> processedInvoice = new HashSet();
		if (paylog != null) {
			Set<Collection> oldCollectionSet = doAuthRequest.getCollections();

			String partialPayAllow = "N";
			List<String> payInvoiceDetails = saveddataDTO.getPayingInvoice();
			List<String> payInvoiceList = new ArrayList();

			if (payInvoiceDetails != null) {
				for (String invList : payInvoiceDetails) {
					String[] parts = invList.split("\\+");
					if (parts.length > 0) {

						payInvoiceList.add(EncryptionUtil.decrypt(parts[0]));
					}
				}

			}
			List<SAInitiatorPayment> isSaPartialPayList = bolService.getSAInitiatorPartialPayment(
					doAuthRequest.getBol().getBolDetails().parallelStream().filter(bdetail -> bdetail != null)
							.map(bdetail -> bdetail.getShippingAgentCode()).findAny().orElse(null),
					userDTO.getAgentType());
			if (isSaPartialPayList != null)
				partialPayAllow = isSaPartialPayList.get(0).getPaymentAllowed();
			Set<BolInvoice> bolInvList = new HashSet();

			Set<Collection> collectionSet = new HashSet();
			Set<PaymentOfProof> paymentOfProofSet = new HashSet();
			Double payingAmt = 0D;
			for (PaymentLog payLog : doAuthRequest.getPaymentLogs()) {
				if (oldCollectionSet != null)
					if (oldCollectionSet.stream().anyMatch(col -> col.getBolInvoice().getBolInvoiceId()
							.equals(payLog.getBolInvoice().getBolInvoiceId()))) {
						continue;
					} else
						bolInvList.add(payLog.getBolInvoice());
				String path = "";

				Collection collection = new Collection();
				if (payInvoiceList.contains(payLog.getBolInvoice().getInvoiceNumber())) {
					if (!processedInvoice.contains(payLog.getBolInvoice().getInvoiceNumber())) {
						processedInvoice.add(payLog.getBolInvoice().getInvoiceNumber());
						collection.setAmount(payLog.getBolInvoice().getInvoiceValue().toString());
						payingAmt = payingAmt + payLog.getBolInvoice().getInvoiceValue();
						if (file != null) {
							if (!file.equalsIgnoreCase("null")) {
								String[] parts = file.split("base64,");
								if (parts.length == 2) {
									file = parts[1];
									path = Utilities.saveDocumentstoPath(file,
											"PaymentProof" + "." + Utilities.getFileExtn(Utilities.decodeBase64(file)),
											paymentProof, doAuthRequest.getBol().getBolNumber());
								}

							}
						}
						collection.setStatus("SUCCESS");
						payLog.setStatus("SUCCESS");
						// payLog.setPaidBy(userDTO.getAgentCode());
						payLog.setModifiedBy(userDTO.getUserName());
						payLog.setModifiedDate(new Date());
						collection.setCreatedBy(userDTO.getUserName());
						collection.setDoAuthRequest(doAuthRequest);
						collection.setBolInvoice(payLog.getBolInvoice());
						collection.setInvoiceNumber(payLog.getBolInvoice().getInvoiceNumber());
						collection.setPayStatus(payLog.getPayStatus());
						collection.setCollectionType("UPLOAD_PAYMENT_PROOF");

						collection.setStatus("SUCCESS");
						collection.setCreatedBy(userDTO.getUserName());
						collection.setDoAuthRequest(doAuthRequest);

						PaymentOfProof paymentOfProof = new PaymentOfProof();
						paymentOfProof.setCollection(collection);
						paymentOfProof.setPaymentAmount(new BigDecimal(collection.getAmount()));
						paymentOfProof.setCreatedBy(collection.getCreatedBy());
						paymentOfProof.setReference(path);
						paymentOfProofSet.add(paymentOfProof);

						collection.setPaymentOfProofs(paymentOfProofSet);

						if (doAuthRequest.getBol().getBolInvoices().stream().map(savedBolInv -> {
							if (savedBolInv.getInvoiceNumber().equals(payLog.getBolInvoice().getInvoiceNumber())) {
								savedBolInv.setStatus(Constants.BOLINVOICE_STATUS.PAYMENT_SUCCESS.value);

							}
							return savedBolInv;

						}).collect(Collectors.toSet()) != null)
							;
						collectionSet.add(collection);
						/*
						 * if (savedStatus.equalsIgnoreCase(Constants.DO_STATUS.DO_INITIATED.value)) {
						 * if (doAuthRequest.getBol().getBolInvoices().stream().anyMatch(bolInv ->
						 * bolInv.getStatus()
						 * .equals(Constants.BOLINVOICE_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value)))
						 * doAuthRequest.getBol().setStatus((Constants.DO_STATUS.DO_INITIATED.value));
						 * doAuthRequest.setStatus(Constants.DO_STATUS.DO_INITIATED.value); }
						 */
						collectionSet.add(collection);
					}
				}

			}
			if (partialPayAllow.equalsIgnoreCase("N")) {
				Boolean isPartialPayment = isPartialPayment(doAuthRequest, doAuthRequestDTO.getPayingAmt(),
						doAuthRequestDTO.getPaidAmt());
				if (!isPartialPayment) {
					doAuthRequest.setStatus((Constants.DO_STATUS.PENDING_FOR_APPROVAL.value));
					doAuthRequest.getBol().setStatus(Constants.BOL_STATUS.INITIATED.value);
				} else {
					doAuthRequest.setStatus(Constants.DO_STATUS.PARTIAL_PAYMENT.value);
					doAuthRequest.getBol().setStatus(Constants.BOL_STATUS.PARTIAL_PAYMENT.value);
					msg = "Upload Payment Proof Succesfully updated and it is partial payment.DO request will processed only after complete payment.  DO Reference No.";
				}
			} else {
				doAuthRequest.setStatus((Constants.DO_STATUS.PENDING_FOR_APPROVAL.value));
				doAuthRequest.getBol().setStatus(Constants.BOL_STATUS.INITIATED.value);
			}
			doAuthRequest.setCollections(collectionSet);
			if (savedStatus.equalsIgnoreCase(Constants.DO_STATUS.DO_INITIATED.value)) {

				for (BolInvoice bolInvoice : doAuthRequest.getBol().getBolInvoices()) {
					if (bolInvoice.getStatus() != null) {
						if (bolInvoice.getStatus()
								.equalsIgnoreCase(Constants.BOLINVOICE_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value)) {
							doAuthRequest.getBol().setStatus((Constants.DO_STATUS.DO_INITIATED.value));
							doAuthRequest.setStatus(Constants.DO_STATUS.DO_INITIATED.value);
						}
						if (bolInvoice.getStatus().equalsIgnoreCase(Constants.BOLINVOICE_STATUS.PAYMENT_FAILED.value)) {
							doAuthRequest.setStatus(Constants.DO_STATUS.DO_INITIATED.value);
						}
					}

				}
			}
			doAuthRequest.setModifiedBy(userDTO.getUserName());
			doAuthRequest.setModifiedDate(new Date());

		}

		DoAuthRequest saveddoAuthRequest = doAuthRequestRepository.save(doAuthRequest);

		if (saveddoAuthRequest != null) {

			return msg + saveddoAuthRequest.getDoRefNo();
		} else
			return "FAILED";
	}

	@Override
	@Transactional
	public List<SelectDto> getPaymentOption(String agentCode, String userDTO) {
		PaymentOption paymentOptions = paymentOptionRepository.findByShippingAgentCode(agentCode);
		List<SelectDto> paymentOptionSelectList = new ArrayList();
		if (paymentOptions == null) {
			paymentOptions = paymentOptionRepository.findByShippingAgentCode("DEFAULT");
		}
		if (paymentOptions != null) {
			// paymentOptionSelectList.add(new SelectDto("CHOOSE", "-1"));
			StringTokenizer paytokenizer = new StringTokenizer(paymentOptions.getPaymentMode(), ",");
			while (paytokenizer.hasMoreTokens()) {
				String value = paytokenizer.nextToken();
				String label = "";
				if (value.equalsIgnoreCase("ONLINE PAYMENT"))
					label = "0";
				else if (value.equalsIgnoreCase("CREDIT PAYMENT"))
					label = "1";
				else if (value.equalsIgnoreCase("UPLOAD PAYMENT PROOF"))
					label = "2";

				paymentOptionSelectList.add(new SelectDto(value, label));
			}
		}
		return paymentOptionSelectList;
	}

	@Override
	@Transactional
	public String getAdminEmailByAgentCode(String agentCode, List<String> subIds) {
		List<DTAdminEmailView> emailViewList = dTAdminEmailViewRepository
				.findDistinctByAgentCodeAndBusinessSubIdIn(agentCode, subIds);
		if (null != emailViewList && emailViewList.size() > 0) {
			return emailViewList.get(0).getEmail();
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public DTAdminEmailView getAdminDetailsByAgentCode(String agentCode, List<String> subIds) {
		return dTAdminEmailViewRepository.getAgentNameByAgentCodeAndBusinessSubIdIn(agentCode, subIds);

	}

	@Override
	@Transactional
	public List<DTUserView> getAdminEmailByAgentCodeReqParty(String agentCode, List<String> subIds, String userName) {
		return dTUserViewRepository.findDistinctByAgentCodeAndUserNameAndBusinessSubIdIn(agentCode, userName, subIds);
	}

	@Override
	@Transactional
	public byte[] recieptWithTransactionID(String transactionId, UserDTO userDTO, HttpServletResponse response)
			throws JRException {

		Long gcId = null;
		String agentName = "";
		String agentCode = "";
		String collectionType = "";
		String paymentType = "";
		String receiptNo = "";
		String image = "";
		double sum = 0;
		String companyName = "";
		String fiInstrument = "";

		CodeMaster codeMaster = new CodeMaster();
		List<String> subIds = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<ViewDTO> dtos = new ArrayList<ViewDTO>();

		final JasperReport report = loadTemplate("reciept");
		subIds.add(Constants.AGENT_TYPE.IMPORTER.value);
		subIds.add(Constants.AGENT_TYPE.CLEARING_AGENT.value);
		subIds.add(Constants.AGENT_TYPE.FREEZONE_LICENCE.value);
		subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);

		List<Collection> collections = collectionRepository.findByTransactionId(transactionId);

		if (collections != null) {
			if (collections.size() > 0) {
				sum = collections.stream().mapToDouble(o -> Double.valueOf(o.getAmount())).sum();
				for (Collection collection : collections) {
					ViewDTO dto = new ViewDTO<>();
					dto.setInvoiceNumber(collection.getInvoiceNumber());
					dto.setInvoiceValue(collection.getBolInvoice().getInvoiceValue());
					dto.setCurrency(collection.getBolInvoice().getCurrency());
					dto.setAmount(collection.getAmount());
					dto.setCreatedDate(collection.getCreatedDate());
					collectionType = Constants.PAYMENT_METHOD.valueOf(collection.getCollectionType()).name;
					dto.setPayMethod(collectionType);
					image = getClass().getResource("/images/logo_dt.png").toString();
					receiptNo = collection.getReceiptNo();
					DoAuthRequest doAuthRequest = collection.getDoAuthRequest();
					dto.setDoRefNo(doAuthRequest.getDoRefNo());
					dto.setBolNumber(doAuthRequest.getBol().getBolNumber());
					if (!collection.getRosooms().isEmpty()) {
						Rosoom r = collection.getRosooms().iterator().next();
						fiInstrument = r.getFiInstrument();
						paymentType = fiInstrument.substring(fiInstrument.length() - 1);
					}

					List<PaymentLog> payLogs = paymentLogRepository.findByTransactionIdAndStatus(transactionId,
							Constants.PAY_SUCCESS);
					PaymentLog payLog = payLogs.iterator().next();
					if (payLog.getPaidBy() != null) {
						Set<DTAgentView> agentDetailSet = bolService.getAgentDetails(payLog.getInitiatorId().toString(),
								subIds);
						DTAgentView agent = new DTAgentView();
						if (agentDetailSet.size() != 0) {
							agent = agentDetailSet.iterator().next();
							companyName = agent.getCompanyName();
							gcId = agent.getGcId();
							if (!doAuthRequest.getBol().getBolDetails().isEmpty()) {
								BolDetail bolDet = doAuthRequest.getBol().getBolDetails().iterator().next();
								dto.setShippingAgentName(bolDet.getShippingAgentName());
								agentName = bolDet.getShippingAgentName();
								agentCode = bolDet.getShippingAgentCode();
							}
						}
					}

					dtos.add(dto);
				}
			}

			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dtos);
			codeMaster = codeMasterRepository.findByCodeMasterValue(paymentType);
			parameters.put("invoiceList", dataSource);
			parameters.put("logo", image);
			parameters.put("gcId", gcId);
			parameters.put("agentName", agentName);
			parameters.put("agentCode", agentCode);
			parameters.put("collectionType", collectionType);
			parameters.put("companyName", companyName);
			if (null == codeMaster)
				parameters.put("paymentType", "");
			else
				parameters.put("paymentType", codeMaster.getCodeMasterDesc());
			parameters.put("receiptNo", receiptNo);
			parameters.put("transactionId", transactionId);
			parameters.put("createdBy", collections.get(0).getCreatedBy());
			parameters.put("createdDate", collections.get(0).getCreatedDate());
			parameters.put("totalAmount", sum);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getDoauthReqVersion(Long id) {
		return doAuthRequestRepository.findById(id).getVersion();
	}

	@Override
	@Transactional(readOnly = true)
	public PaymentOfProof findByPayProofId(Long id) {
		return paymentProofRepository.findBypaymentOfProofId(id);

	}

	@Override
	@Transactional
	public SARosoomDTO getRosoomDetailByShippingAgentId(Long shippingAgentId, String currency) {
		return sARosoomMapper.saRosoomDomaintoDTO(
				sARosoomRepository.findByCurrencyAndShippingAgentShippingAgentId(currency, shippingAgentId));
	}

	@Override
	@Transactional
	public ShippingAgent getShippingAgentFromPaymentLog(Long doAuthRequestId) {
		List<PaymentLog> saList = paymentLogRepository.getShippingAgentByDoAuthRequestDoAuthRequestId(doAuthRequestId);
		if (saList != null)
			if (saList.size() > 0)
				return saList.get(0).getShippingAgent();
			else
				return null;
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection getCollection(String invoiceNo) {
		return collectionRepository.findByInvoiceNumber(invoiceNo);

	}

	@Override
	@Transactional
	public String getDocumentPath(String collectionId) {
		PaymentOfProof paymentOfProof = paymentProofRepository
				.findByCollectionCollectionId((Long.valueOf(collectionId)));
		return paymentOfProof.getReference();
	}

	@Override
	@Transactional
	public Long getShippingAgentPaymentLog(PaymentLog paymentLog) {
		PaymentLog payLog = paymentLogRepository.findByPaymentLogId(paymentLog.getPaymentLogId());
		if (payLog != null) {
			if (payLog.getShippingAgent() != null) {
				Long shippingAgentId = payLog.getShippingAgent().getShippingAgentId();
				return shippingAgentId;
			}
		} else {
			return null;
		}
		return null;

	}

	@Override
	public String findDistinctByAgentCode(String agentCode) {
		List<DTAdminEmailView> emailViewList = dTAdminEmailViewRepository.findDistinctByAgentCode(agentCode);
		if (null != emailViewList && emailViewList.size() > 0) {
			return emailViewList.get(0).getEmail();
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public DoAuthRequestDTO updatePaymentLogForPaymentPendingWithImporter(Long doAuthRequestId,
			List<String> selectedInvoice, UserDTO userDTO) {
		DoAuthRequestDTO doAuthRequestDTO = new DoAuthRequestDTO();
		if (selectedInvoice != null && selectedInvoice.size() > 0) {
			Long randSeq = paymentLogRepository.getNextTranxNo();
			String payTransactionId = "DREF" + randSeq;
			Double totalAmt = 0D;
			String currency = "";
			for (String invoiceNo : selectedInvoice) {
				invoiceNo = EncryptionUtil.decrypt(invoiceNo);
				PaymentLog savedPayLog = paymentLogRepository
						.findByDoAuthRequestDoAuthRequestIdAndBolInvoiceInvoiceNumberAndStatus(doAuthRequestId,
								invoiceNo, Constants.PAYMENTLOG_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value);
				if (savedPayLog != null) {
					totalAmt = totalAmt + savedPayLog.getAmount();
					savedPayLog.setStatus(Constants.PAYMENTLOG_STATUS.PENDING.value);
					savedPayLog.setTransactionId(payTransactionId);
					// savedPayLog.setModifiedBy(userDTO.getUserName());
					// savedPayLog.setModifiedDate(new Date());
					savedPayLog.setCreatedBy(userDTO.getUserName());
					savedPayLog.setCreatedDate(new Date());
					// savedPayLogSet.add(paymentLogMapper.paylogdomaintoDTO(paymentLogRepository.save(savedPayLog)));
					paymentLogRepository.save(savedPayLog);
					BolInvoice bolInvoice = bolInvoiceRepository.findByInvoiceNumber(invoiceNo);
					bolInvoice.setStatus(Constants.BOLINVOICE_STATUS.PAYMENT_PENDING.value);
					bolInvoice.setModifiedBy(userDTO.getUserName());
					bolInvoice.setModifiedDate(new Date());
					bolInvoiceRepository.save(bolInvoice);
					paymentLogRepository.flush();
					bolInvoiceRepository.flush();
				}
			}
			DoAuthRequest doAuthRequest = doAuthRequestRepository.findByDoAuthRequestId(doAuthRequestId);
			doAuthRequestDTO.setTransactionId(payTransactionId);
			doAuthRequestDTO.setPayingAmt(totalAmt);
			doAuthRequestDTO.setDoRefNo(doAuthRequest.getDoRefNo());
			doAuthRequestDTO.setDoAuthRequestId(doAuthRequestId);
			BoLDTO boLDTO = new BoLDTO();
			boLDTO.setBolNumber(doAuthRequest.getBol().getBolNumber());
			doAuthRequestDTO.setShippingAgentCode(
					doAuthRequest.getBol().getBolDetails().iterator().next().getShippingAgentCode());
			doAuthRequestDTO.setBol(boLDTO);
			doAuthRequestDTO.setBolEmail(doAuthRequest.getBolEmail());
			doAuthRequestDTO.setDoEmail(doAuthRequest.getDoEmail());
			doAuthRequestDTO.setReqPartyName(doAuthRequest.getReqPartyName());
			doAuthRequestDTO.setIsOnline(true);

		}
		return doAuthRequestDTO;
	}

	@Override
	@Transactional
	public void updateApproverViewedStatus(DoAuthRequest doAuthRequest) {
		doAuthRequestRepository.save(doAuthRequest);

	}

	@Override
	@Transactional(readOnly = true)
	public DoAuthRequestDTO getApproverViewedStatus(Long doAuthReqId, UserDTO userDTO) {
		DoAuthRequest doAuthReq = doAuthRequestRepository.findById(doAuthReqId);
		return new DoAuthRequestDTO().builder().doAuthRequestId(doAuthReq.getDoAuthRequestId())
				.viewedBy(doAuthReq.getViewedBy()).viewedDate(doAuthReq.getViewedDate())
				.approvedViewed(doAuthReq.getApprovedViewed())
				.userName(userDTO.getUserName()).build();
	}

	@Override
	@Transactional(readOnly = true)
	public Double getPayingAmountByInvoiceNumber(List<String> payInvoiceList, String bolNo) {
		Double payingAmt = 0.0;
		if (payInvoiceList != null) {
			for (String invNo : payInvoiceList) {
				BolInvoice bolInvoice = bolInvoiceRepository.findByInvoiceNumberAndBolBolNumber(invNo, bolNo);
				if (bolInvoice != null)
					payingAmt = payingAmt + bolInvoice.getInvoiceValue();
			}
		}
		return payingAmt;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DoAuthRequestDTO> getDODetailByAuthRequestId(Long authRequestId) {
		Specification specification = new DoAuthRequestSpecification().getspecification(authRequestId);
		List<DoAuthRequest> doAuthRequests = doAuthRequestRepository.findAll((specification));
		DoAuthRequest doAuthRequestEntity = doAuthRequests.get(0);
		DoAuthRequestDTO doAuthRequestDTO = new DoAuthRequestDTO();

		Set<DoAuthDocDTO> authDocDTOList = new HashSet<DoAuthDocDTO>();
		Set<BolInvoiceDTO> bolInvoiceDTOList = new HashSet<BolInvoiceDTO>();
		Set<ApprovalLogDTO> approveLogDTOList = new HashSet<ApprovalLogDTO>();
		Set<PaymentLogDTO> paymentLogDTOList = new HashSet<PaymentLogDTO>();
		Set<Credit> creditsList = new HashSet<Credit>();
		Set<PaymentOfProofDTO> paymentOfProofDTOList = new HashSet<PaymentOfProofDTO>();
		Set<Rosoom> rosoomList = new HashSet<Rosoom>();
		List<DoAuthRequestDTO> authRequestDTOsList = new ArrayList<DoAuthRequestDTO>();

		// Set the Static Mapping
		if (doAuthRequestEntity != null) {

			doAuthRequestDTO.setDoAuthRequestIdEncr(
					EncryptionUtil.encrypt(String.valueOf(doAuthRequestEntity.getDoAuthRequestId())));
			doAuthRequestDTO.setDoRefNo(doAuthRequestEntity.getDoRefNo());
			doAuthRequestDTO.setReqContactPerson(doAuthRequestEntity.getReqContactPerson());
			doAuthRequestDTO.setReqEmail(doAuthRequestEntity.getReqEmail());
			doAuthRequestDTO.setReqContactNumber(doAuthRequestEntity.getReqContactNumber());
			doAuthRequestDTO.setReqPartyName(doAuthRequestEntity.getReqPartyName());

			doAuthRequestDTO.setBolContactPerson(doAuthRequestEntity.getBolContactPerson());
			doAuthRequestDTO.setBolEmail(doAuthRequestEntity.getBolEmail());
			doAuthRequestDTO.setBolContactNumber(doAuthRequestEntity.getBolContactNumber());
			doAuthRequestDTO.setBolPartyName(doAuthRequestEntity.getBolPartyName());

			doAuthRequestDTO.setDoContactPerson(doAuthRequestEntity.getDoContactPerson());
			doAuthRequestDTO.setDoEmail(doAuthRequestEntity.getDoEmail());
			doAuthRequestDTO.setDoContactNumber(doAuthRequestEntity.getDoContactNumber());
			doAuthRequestDTO.setDoPartyName(doAuthRequestEntity.getDoPartyName());
			doAuthRequestDTO.setCreatedDate(doAuthRequestEntity.getCreatedDate());
			doAuthRequestDTO.setCreatedBy(doAuthRequestEntity.getCreatedBy());
			doAuthRequestDTO.setTempCreatedDate(
					DateUtil.formatDate(doAuthRequestEntity.getCreatedDate(), DateUtil.COMMON_DATE_FORMAT));
			doAuthRequestDTO.setTempmodifiedDate(
					DateUtil.formatDate(doAuthRequestEntity.getModifiedDate(), DateUtil.COMMON_DATE_FORMAT));
			doAuthRequestDTO.setModifiedDate(doAuthRequestEntity.getModifiedDate());
			doAuthRequestDTO.setModifiedBy(doAuthRequestEntity.getModifiedBy());
			doAuthRequestDTO.setStatus(doAuthRequestEntity.getStatus());

			// SET BOL DTO
			if (doAuthRequestEntity.getBol() != null) {
				BoLDTO boLDTO = new BoLDTO();
				Bol bol = doAuthRequestEntity.getBol();
				boLDTO.setBolNumber(bol.getBolNumber());
				boLDTO.setEncBolNumber(EncryptionUtil.encrypt(bol.getBolNumber()));
				boLDTO.setBolType(bol.getBolType());
				boLDTO.setStatus(bol.getStatus());
				boLDTO.setCreatedDate(bol.getCreatedDate());
				Set<CollectionDTO> collectionDTOList = new HashSet<CollectionDTO>();
				// SET BOL_DETAILS DTO
				if (bol.getBolDetails() != null) {
					Set<BoLDetailDTO> boLDetailDTOList = new HashSet<BoLDetailDTO>();
					for (BolDetail bolDet : bol.getBolDetails()) {
						BoLDetailDTO boLDetailDTO = new BoLDetailDTO();
						boLDetailDTO.setConsigneeName(bolDet.getConsigneeName());
						boLDetailDTO.setVesselName(bolDet.getVesselName());
						boLDetailDTO.setVesselEta(bolDet.getVesselEta());
						boLDetailDTO.setVesselAta(bolDet.getVesselAta());
						boLDetailDTO.setImporterCode(bolDet.getImporterCode());
						boLDetailDTO.setShippingAgentCode(bolDet.getShippingAgentCode());
						boLDetailDTO.setShippingAgentName(bolDet.getShippingAgentName());
						boLDetailDTO.setVoyageNumber(bolDet.getVoyageNumber());
						boLDetailDTO.setContainerCount(bolDet.getContainerCount());
						boLDetailDTOList.add(boLDetailDTO);
					}
					boLDTO.setBolDetails(boLDetailDTOList);
				}
				// SET BOL_INVOICE DTO
				if (bol.getBolInvoices() != null) {
					Set<BolInvoice> bolInvoiceList = bol.getBolInvoices();
					for (BolInvoice bolInvoice : bolInvoiceList) {
						BolInvoiceDTO bolInvoiceDTO = new BolInvoiceDTO();
						bolInvoiceDTO.setInvoiceNumber(bolInvoice.getInvoiceNumber());
						bolInvoiceDTO.setEncrInvoiceNumber(EncryptionUtil.encrypt(bolInvoice.getInvoiceNumber()));
						bolInvoiceDTO.setInvoiceValue(bolInvoice.getInvoiceValue());
						bolInvoiceDTO.setCurrency(bolInvoice.getCurrency());
						if (bolInvoice.getStatus() != null) {
							if (bolInvoice.getStatus()
									.equalsIgnoreCase(Constants.BOLINVOICE_STATUS.PAYMENT_SUCCESS.value))
								;
							bolInvoiceDTO.setCreatedBy(bolInvoice.getModifiedBy());
						} else {
							// bolInvoiceDTO.setCreatedBy("");
							bolInvoiceDTO.setCreatedBy(bolInvoice.getModifiedBy());
						}

						if (bolInvoice.getInvoiceType() != null) {
							InvoiceTypeDTO invoiceTypeDTO = new InvoiceTypeDTO();
							InvoiceType invoiceType = bolInvoice.getInvoiceType();
							invoiceTypeDTO.setInvoiceTypeId(invoiceType.getInvoiceTypeId());
							invoiceTypeDTO.setInvoiceTypeName(invoiceType.getInvoiceTypeName());
							bolInvoiceDTO.setInvoiceType(invoiceTypeDTO);
						}
						if (doAuthRequestEntity.getCollections() != null) {
							Set<CollectionDTO> collectionDTOSet = new HashSet<CollectionDTO>();
							for (Collection collection : doAuthRequestEntity.getCollections()) {
								CollectionDTO collec = new CollectionDTO();

								if (StringUtils.equalsIgnoreCase(collection.getBolInvoice().getInvoiceNumber(),
										bolInvoice.getInvoiceNumber())) {
									collec.setEncrCollectionId(
											EncryptionUtil.encrypt(collection.getCollectionId().toString()));
									collec.setCollectionType(collection.getCollectionType());
									collec.setAmount(collection.getAmount());
									collec.setInvoiceNumber(collection.getInvoiceNumber());
									collec.setStatus(collection.getStatus());
									collec.setCreatedBy(collection.getCreatedBy());

									// Credit
									if (collection.getCredits() != null) {
										for (PaymentOfProof poProof : collection.getPaymentOfProofs()) {
											CreditDTO creditDTO = new CreditDTO();

										}
									}
									// PaymentOfProof
									if (collection.getPaymentOfProofs() != null) {
										for (PaymentOfProof poProof : collection.getPaymentOfProofs()) {
											PaymentOfProofDTO paymentOfProofDTO = new PaymentOfProofDTO();
											paymentOfProofDTO.setPaymentOfProofId(poProof.getPaymentOfProofId());
											paymentOfProofDTO.setRefNumber(poProof.getRefNumber());
											collec.setRefNumber(poProof.getRefNumber());
											paymentOfProofDTOList.add(paymentOfProofDTO);
										}
										collec.setPaymentOfProofs(paymentOfProofDTOList);
									}
									// Rosoom
									if (collection.getRosooms() != null) {
										RosoomDTO rosoomDTO = new RosoomDTO();

									}
									collectionDTOSet.add(collec);
								}

							}
							if (collectionDTOSet.size() == 0) {
								collectionDTOSet.add(new CollectionDTO());
								bolInvoiceDTO.setCollections(collectionDTOSet);
							} else {

								bolInvoiceDTO.setCollections(collectionDTOSet);
							}
						}
						bolInvoiceDTOList.add(bolInvoiceDTO);

					}
					boLDTO.setBolInvoices(bolInvoiceDTOList);
				}
				doAuthRequestDTO.setBol(boLDTO);

			}

			// SET DO_AUTH_Documents DTO
			if (doAuthRequestEntity.getDoAuthDocs() != null) {
				for (DoAuthDoc doauAuthDoc : doAuthRequestEntity.getDoAuthDocs()) {
					DoAuthDocDTO authDocoumentDto = new DoAuthDocDTO();
					DocumentDTO documentDTO = new DocumentDTO();
					Document document = doauAuthDoc.getDocumentBean();
					authDocoumentDto.setEncrDocumentPath(EncryptionUtil.encrypt(doauAuthDoc.getDocumentPath()));
					// authDocoumentDto.setDoAuthDocsId(doauAuthDoc.getDoAuthDocsId());
					authDocoumentDto
							.setEncrDoAuthDocsId(EncryptionUtil.encrypt(String.valueOf(doauAuthDoc.getDoAuthDocsId())));
					// documentDTO.setDocumentId(document.getDocumentId());
					documentDTO.setDocumentValue(document.getDocumentValue());
					authDocoumentDto.setDocumentBean(documentDTO);
					// authDocoumentDto.setDocumentBean(document);
					authDocDTOList.add(authDocoumentDto);
				}
			}

			// SET paymentLogs DTO
			if (doAuthRequestEntity.getPaymentLogs() != null) {
				for (PaymentLog payLog : doAuthRequestEntity.getPaymentLogs()) {
					PaymentLogDTO paymentLogDTO = new PaymentLogDTO();
					// if(payLog.getStatus().equalsIgnoreCase(Constants.PAY_SUCCESS)) {
					paymentLogDTO.setPaidBy(payLog.getPaidBy());
					paymentLogDTO.setInvoiceNumber(payLog.getBolInvoice().getInvoiceNumber());
					paymentLogDTO.setAmount(payLog.getAmount());
					paymentLogDTOList.add(paymentLogDTO);
					// }
				}
			}

			// SET approvalLog DTO
			if (doAuthRequestEntity.getApprovalLog() != null) {
				for (ApprovalLog approvalLog : doAuthRequestEntity.getApprovalLog()) {
					ApprovalLogDTO approveLogDTO = new ApprovalLogDTO();
					approveLogDTO.setCreatedDate(approvalLog.getCreatedDate());
					approveLogDTO.setStatus(approvalLog.getStatus());
					approveLogDTO.setCreatedBy(approvalLog.getCreatedBy());
					approveLogDTO.setComments(approvalLog.getComments());

					if (approvalLog.getRejectionRemark() != null) {
						RejectionRemark rejectionRemark = approvalLog.getRejectionRemark();
						RejectionRemarksDTO rejectionRemarksDTO = new RejectionRemarksDTO();
						rejectionRemarksDTO.setRemarks(rejectionRemark.getRemarks());
						approveLogDTO.setRejectionRemark(rejectionRemarksDTO);
					}
					if (approvalLog.getReturnRemark() != null) {
						ReturnRemark returnRemark = approvalLog.getReturnRemark();
						ReturnRemarksDTO returnRemarksDTO = new ReturnRemarksDTO();
						returnRemarksDTO.setRemarks(returnRemark.getRemarks());
						approveLogDTO.setReturnRemark(returnRemarksDTO);
					}
					approveLogDTOList.add(approveLogDTO);
				}

			}

			doAuthRequestDTO.setApprovalLog(approveLogDTOList);
			doAuthRequestDTO.setDoAuthDocs(authDocDTOList);
			doAuthRequestDTO.setPaymentLogs(paymentLogDTOList);
			authRequestDTOsList.add(doAuthRequestDTO);
		}
		return authRequestDTOsList;

	}

	@Override
	public void sendMailForRequestDO(DoAuthRequestDTO savedDoAuthRequestDTO, UserDTO userDTO, Boolean isPayProof) {
		Map<String, String> mailContentMap = new HashMap<String, String>();
		String image = getClass().getResource("/images/email_header.png").toString();
		mailContentMap.put("imageURL", image);
		String head = "Request Delivery Order with Do Reference No:  " + savedDoAuthRequestDTO.getDoRefNo()
				+ " initiated.";
		mailContentMap.put("head", head);
		mailContentMap.put("detail", "");
		mailContentMap.put("bol", savedDoAuthRequestDTO.getBolNumber());
		mailContentMap.put("bolPartyName", savedDoAuthRequestDTO.getBolPartyName());
		mailContentMap.put("doPartyName", savedDoAuthRequestDTO.getDoPartyName());
		mailContentMap.put("reqPartyName", savedDoAuthRequestDTO.getReqPartyName());
		mailContentMap.put("reqContactNumber", savedDoAuthRequestDTO.getReqContactNumber());
		mailContentMap.put("reqContactPerson", savedDoAuthRequestDTO.getReqContactPerson());
		mailContentMap.put("reqEmail", savedDoAuthRequestDTO.getReqEmail());
		mailContentMap.put("totalAmount", String.valueOf(savedDoAuthRequestDTO.getTotalAmount()));
		mailContentMap.put("payingAmt", String.valueOf(savedDoAuthRequestDTO.getPayingAmt()));

		List<String> subIds = new ArrayList<>();
		subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);
		subIds.add(Constants.AGENT_TYPE.SHIPPING_AGENT.value);
		String shippingAgentCode = savedDoAuthRequestDTO.getShippingAgentCode();
		String bolPartyEmail = savedDoAuthRequestDTO.getBolEmail();
		String doPartyEmail = savedDoAuthRequestDTO.getDoEmail();
		// String emailSA = getAdminEmailByAgentCodeReqPartyNew(shippingAgentCode,
		// subIds, userDTO.getUserName());
		String emailSA = billOfLaddingFacade.getShippingAgentEmailId(shippingAgentCode);
		String emails = bolPartyEmail;
		String reqParty = savedDoAuthRequestDTO.getReqPartyName();
		mailContentMap.put("reqParty", reqParty);
		mailContentMap.put("detail", "Please proceed to further action.");
		String[] listOfCCMail = ccEmail.split(";");
		String toEmails = "";
		if (doPartyEmail != null) {
			emails = emails + "," + doPartyEmail;
		}
		String[] listOfCCMails = ccEmail.split(";");
		if (emailSA != null) {
			toEmails = toEmails + emailSA + ";";
			/*
			 * emailNotificationService.sendMail(new
			 * MailDTO().builder().toAddress(emailSA).ccAddress(listOfCCMails)
			 * .subject("Request DO").mailContentMap(mailContentMap).build(),
			 * Constants.EMAIL_TYPE.DO_REQUEST.value);
			 */
		}

		if (savedDoAuthRequestDTO.getIsPartialPayment() == true) {
			String subject = "Partial Payment for BOL " + savedDoAuthRequestDTO.getBolNumber();
			emailNotificationService.sendMail(
					new MailDTO().builder().toAddress(savedDoAuthRequestDTO.getDoEmail()).ccAddress(listOfCCMails)
							.subject(subject).mailContentMap(mailContentMap).build(),
					Constants.EMAIL_TYPE.DO_REQUEST_PARTIAL_PAYMENT_DO.value);

			emailNotificationService.sendMail(
					new MailDTO().builder().toAddress(savedDoAuthRequestDTO.getBolEmail()).ccAddress(listOfCCMails)
							.subject(subject).mailContentMap(mailContentMap).build(),
					Constants.EMAIL_TYPE.DO_REQUEST_PARTIAL_PAYMENT_BOL.value);
		} else {

			String[] listOfTo = null;

			if (isPayProof) {
				List<ShippingAgentAttributes> shippingAgentAttributes = shippingAgentAttributesRepository
						.findShippingAgentAttributesByAttributeNameAndIsActiveAndShippingAgentShippingAgentCode(
								Constants.FINANCE_EMAILS, 1L, shippingAgentCode);
				if (shippingAgentAttributes != null) {
					if (shippingAgentAttributes.size() > 0) {
						toEmails = toEmails + shippingAgentAttributes.get(0).getAttributeValue() + ";";
					}

					/*
					 * listOfTo = Stream.of(listOfTo,listOfToFinanceOfficer).flatMap(Stream::of)
					 * .toArray(String[]::new);
					 */
				}

			}
			if (savedDoAuthRequestDTO.getBolEmail() != null) {
				// listOfTo[listOfTo.length+1]=savedDoAuthRequestDTO.getBolEmail();
				toEmails = toEmails + savedDoAuthRequestDTO.getBolEmail() + ";";
			}
			if (savedDoAuthRequestDTO.getDoEmail() != null) {
				// listOfTo[listOfTo.length+1]=savedDoAuthRequestDTO.getDoEmail();
				toEmails = toEmails + savedDoAuthRequestDTO.getBolEmail() + ";";
			}
			if (toEmails != "") {
				listOfTo = toEmails.split(";");
				listOfTo = Utilities.cleanArray(listOfTo);
				String subject = "Delivery Order Request submitted for BOL: " + savedDoAuthRequestDTO.getBolNumber();

				emailNotificationService
						.sendMail(
								new MailDTO().builder().toAddressList(listOfTo).ccAddress(listOfCCMails)
										.subject(subject).mailContentMap(mailContentMap).build(),
								Constants.EMAIL_TYPE.DO_REQUEST_DO.value);
			}

		}

	}

	/**
	 * The Desciption of the method to save Authorize DO request
	 * 
	 * @param agentCode,dataDTO,userDTO,httpServletRequest
	 * @return ResponseRosoomDto
	 */
	public String getAdminEmailByAgentCodeReqPartyNew(String agentCode, List<String> subIds, String userName) {
		List<DTUserView> emailViewList = getAdminEmailByAgentCodeReqParty(agentCode, subIds, userName);
		if (null != emailViewList && emailViewList.size() > 0) {
			return emailViewList.get(0).getEmail();
		} else {
			return null;
		}
	}

	@Override
	public String getAgentNameByAgentCode(String agentCode, List<String> subIds) {
		DTAdminEmailView dTAdminEmailView = dTAdminEmailViewRepository
				.getAgentNameByAgentCodeAndBusinessSubIdIn(agentCode, subIds);
		if (dTAdminEmailView != null)
			return dTAdminEmailView.getAgentName();
		else
			return null;
	}
}
