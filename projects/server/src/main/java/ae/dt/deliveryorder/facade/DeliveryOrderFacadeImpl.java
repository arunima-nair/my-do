package ae.dt.deliveryorder.facade;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ae.dt.common.constants.Constants;
import ae.dt.common.dto.DataDto;
import ae.dt.common.dto.MailDTO;
import ae.dt.common.dto.PageDto;
import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.ResponseDto;
import ae.dt.common.dto.ResponseRosoomDto;
import ae.dt.common.dto.SelectDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.dto.ViewDTO;
import ae.dt.common.util.DateUtil;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.common.util.Utilities;
import ae.dt.deliveryorder.domain.ApprovalLog;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.DTAdminEmailView;
import ae.dt.deliveryorder.domain.DTUserView;
import ae.dt.deliveryorder.domain.DeliveryOrder;
import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.CollectionDTO;
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
import ae.dt.deliveryorder.dto.SARosoomDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import ae.dt.deliveryorder.mapper.BoLMapper;
import ae.dt.deliveryorder.mapper.BolInvoiceMapper;
import ae.dt.deliveryorder.mapper.DeliveryOderMapper;
import ae.dt.deliveryorder.mapper.DoAuthDocsMapper;
import ae.dt.deliveryorder.mapper.DoAuthRequestMapper;
import ae.dt.deliveryorder.mapper.InvoiceTypeMapper;
import ae.dt.deliveryorder.mapper.PaymentLogMapper;
import ae.dt.deliveryorder.repository.BolInvoiceRepository;
import ae.dt.deliveryorder.repository.DoAuthRequestRepository;
import ae.dt.deliveryorder.repository.PaymentLogRepository;
import ae.dt.deliveryorder.repository.SAInitiatorPaymentRepository;
import ae.dt.deliveryorder.service.AuthoriseDeliveryOrderService;
import ae.dt.deliveryorder.service.BillOfLaddingService;
import ae.dt.deliveryorder.service.DeliveryOrderService;
import ae.dt.deliveryorder.service.EmailNotificationService;
import ae.dt.deliveryorder.service.FileProcessingService;

/**
 *  2/10/2019.
 */
/**
 * @author ARUNIMA NAIR
 *
 */
@Service("deliveryOrderFacade")
public class DeliveryOrderFacadeImpl implements DeliveryOrderFacade {

	@Value("${DOfilePath}")
	private String dOfilePath;
	@Value("${DOApprovefilePath}")
	private String DOApprovefilePath;
	@Autowired
	DeliveryOrderService deliveryOrderService;
	@Autowired
	DoAuthRequestMapper doAuthRequestMapper;
	@Autowired
	DoAuthDocsMapper doAuthDocsMapper;
	@Autowired
	DeliveryOderMapper deliveryOderMapper;
	@Autowired
	BillOfLaddingService bolService;
	@Autowired
	BoLMapper bolMapper;
	@Autowired
	PaymentLogMapper paymentLogMapper;
	@Autowired
	FileProcessingService fileProcessingService;
	@Autowired
	BolInvoiceMapper bolInvMapper;
	@Autowired
	BillOfLaddingFacade billOfLaddingFacade;
	@Autowired
	InvoiceTypeMapper invoiceTypeMapper;
	@Autowired
	EmailNotificationService emailNotificationService;
	@Autowired
	AuthoriseDeliveryOrderService authoriseDeliveryOrderService;
	@Autowired
	PaymentLogRepository paymentLogRepository;
	@Autowired
	DoAuthRequestRepository doAuthRequestRepository;
	@Autowired
	BolInvoiceRepository bolInvoiceRepository;
	@Autowired
	SAInitiatorPaymentRepository sAInitiatorPaymentRepository;

	@Value("${cc.email}")
	private String ccEmail;
	Logger logger = LoggerFactory.getLogger(DeliveryOrderFacadeImpl.class);
	private Timestamp timestamp;

	@Override
	public PaginationDTO<ViewDTO> getDOrequestDetails(SearchDTO searchDTO, Pageable pageable, UserDTO userDTO,
			String sortorder, String sortCol) {

		Page<DoAuthRequest> pageDoAuthRequest = deliveryOrderService.getDOrequestDetails(searchDTO, pageable, userDTO,
				sortorder, sortCol);
		String bolNo = "";
		List<ViewDTO> viewDTOList = doAuthRequestMapper.doAuthRequestDomaintoViewDTO(pageDoAuthRequest);

		if (null != pageDoAuthRequest && pageDoAuthRequest.getContent().size() > 0) {
			int i = 0;
			for (ViewDTO viewDTO : viewDTOList) {
				BoLDTO bolDTO = bolService
						.getBolDetailObject(pageDoAuthRequest.getContent().get(i).getBol().getBolNumber());
				viewDTO.setBolNumber(bolDTO.getBolNumber());
				viewDTO.setBolNumberEncr(EncryptionUtil.encrypt(bolDTO.getBolNumber()));
				viewDTO.setBolId(bolDTO.getBolId());
				viewDTO.setBolStatus(bolDTO.getStatus());
				viewDTO.setDoAuthRequestIdEncr(EncryptionUtil.encrypt(String.valueOf(viewDTO.getDoAuthRequestId())));
				viewDTO.setFormatedCreatedDate(DateUtil.formatDate(bolDTO.getCreatedDate(), "dd/MM/yyyy HH:mm"));
				if (viewDTO.getIsReturned() != null)
					viewDTO.setIsReturned(viewDTO.getIsReturned().equalsIgnoreCase("Y") ? "YES" : "NO");
				i++;
			}

			PageDto pageDTO = new PageDto();
			pageDTO.setPageNumber(pageable.getPageNumber());
			pageDTO.setTotalElements(pageDoAuthRequest.getTotalElements());
			pageDTO.setSize(pageable.getPageSize());
			pageDTO.setTotalPages(pageDoAuthRequest.getTotalPages());
			// viewDTOList.sort(Comparator.comparing(ViewDTO::getIsReturned));
			// Comparator<ViewDTO> empComparatorByIsReturnedNullLast =
			// Comparator.comparing(ViewDTO::getIsReturned,Comparator.nullsLast(String::compareTo));
			// Collections.sort(viewDTOList, empComparatorByIsReturnedNullLast);
			return new PaginationDTO<ViewDTO>(viewDTOList, pageable.getPageNumber(), pageDTO.getTotalElements(),
					pageable.getPageSize());
		}
		return null;
	}

	@Override
	public PaginationDTO<ViewDTO> getDOPendingPaymentDetails(SearchDTO searchDTO, Pageable pageable, UserDTO userDTO,
			String sortorder, String sortCol) {

		Page<DoAuthRequest> pageDoAuthRequest = deliveryOrderService.getDOrequestDetails(searchDTO, pageable, userDTO,
				sortorder, sortCol);
		String bolNo = "";
		List<ViewDTO> viewDTOList = doAuthRequestMapper.doAuthRequestDomaintoViewDTO(pageDoAuthRequest);

		if (null != pageDoAuthRequest && pageDoAuthRequest.getContent().size() > 0) {
			int i = 0;
			for (ViewDTO viewDTO : viewDTOList) {
				BoLDTO bolDTO = bolService
						.getBolDetailObject(pageDoAuthRequest.getContent().get(i).getBol().getBolNumber());
				viewDTO.setBolNumber(bolDTO.getBolNumber());
				viewDTO.setBolNumberEncr(EncryptionUtil.encrypt(bolDTO.getBolNumber()));
				viewDTO.setBolId(bolDTO.getBolId());
				viewDTO.setBolStatus(bolDTO.getStatus());
				viewDTO.setDoAuthRequestIdEncr(EncryptionUtil.encrypt(String.valueOf(viewDTO.getDoAuthRequestId())));
				i++;
			}

			PageDto pageDTO = new PageDto();
			pageDTO.setPageNumber(pageable.getPageNumber());
			pageDTO.setTotalElements(pageDoAuthRequest.getTotalElements());
			pageDTO.setSize(pageable.getPageSize());
			pageDTO.setTotalPages(pageDoAuthRequest.getTotalPages());
			return new PaginationDTO<ViewDTO>(viewDTOList, pageable.getPageNumber(), pageDTO.getTotalElements(),
					pageable.getPageSize());
		}
		return null;
	}

	@Override
	public DoAuthRequest updateAfterPayment(PaymentDTO paymentDTO, String custRefNo, String statusParam,
			String agentCode) {
		return deliveryOrderService.updateAfterPayment(paymentDTO, custRefNo, statusParam, agentCode);

	}

	@Override
	public String approveDOrequestDetails(String id, String status, String file, HttpServletRequest httpServletRequest)
			throws IOException {
		DoAuthRequest req = deliveryOrderService.getDoAuthrequest(Long.valueOf(id));
		// if(req.getStatus().equalsIgnoreCase("Payment_success")) {
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		req.setStatus(status);
		req.setModifiedBy(userDTO.getUserName());
		req.setModifiedDate(new Date());
		req.setApprovedViewed("N");

		if (file != null)
			if (!file.equalsIgnoreCase("null")) {
				req.getBol().setStatus(status);
				DeliveryOrder deliveryOrder;
				if (null == req.getDeliveryOrder())
					deliveryOrder = new DeliveryOrder();
				else
					deliveryOrder = req.getDeliveryOrder();

				ObjectMapper mapper = new ObjectMapper();
				final JsonNode fileNode = mapper.readTree(file);
				final String uploadDoc = fileNode.get("uploadDoc").asText();
				final String oblCopy = fileNode.get("oblCopy").asText();
				String[] parts = uploadDoc.split("base64,");
				req.setOblCopy(oblCopy);
				if (oblCopy.equalsIgnoreCase("2")) {
					req.getBol().setBolType(Constants.BOL_TYPE.EBL.value);
				}
				if (parts.length == 2) {

					String path = Utilities.saveDocumentstoPath(parts[1],
							"ApproveDO" + "." + Utilities.getFileExtn(Utilities.decodeBase64(parts[1])),
							DOApprovefilePath, req.getBol().getBolNumber());
					deliveryOrder.setDocumentPath(path);
					deliveryOrder.setModifiedBy(userDTO.getUserName());
					deliveryOrder.setCreatedBy(userDTO.getUserName());
					deliveryOrder.setCreatedDate(new Date());
					deliveryOrder.setDoAuthRequest(req);
					deliveryOrder.setModifiedDate(new Date());
					req.setDeliveryOrder(deliveryOrder);
					// }
				} else
					req.setStatus("PENDING_DO");
			} else
				req.setStatus("PENDING_DO");
		String result = deliveryOrderService.approveDO(req);
		if (result.equalsIgnoreCase("success")) {
			String shippingAgentCode = req.getBol().getBolDetails().stream()
					.filter(detail -> detail.getShippingAgentCode() != null)
					.map(detail -> detail.getShippingAgentCode()).findAny().orElse(null);

			// sendMail(req.getBol().getBolNumber(), shippingAgentCode, "DO_PROCESSED");
		}
		return result;
		/*
		 * }else { return null; }
		 */
	}

	@Override
	@Transactional
	public String rejectReturnDo(String id, String status, String remarkId, String otherComments,
			List<BolInvoiceDTO> bolItems, HttpServletRequest httpServletRequest) throws IOException {
		DoAuthRequest req = deliveryOrderService.getDoAuthrequest(Long.valueOf(id));
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");

		req.setStatus(status);
		req.setModifiedBy(userDTO.getUserName());
		req.setModifiedDate(new Date());
		req.getBol().setStatus(status);

		if (bolItems != null && bolItems.size() > 0 && status.equalsIgnoreCase(Constants.DO_STATUS.RETURNED.value)) {
			Bol bol = req.getBol();
			Set<BolInvoice> bolInvSet = bol.getBolInvoices();
			for (BolInvoiceDTO bolInvDto : bolItems) {
				bolInvDto.setIsValid(Constants.ACTIVE_VAL);
				bolInvDto.setCreatedDate(new Date());
				bolInvDto.setIsActive(Constants.ACTIVE_VAL);
				bolInvDto.setCreatedBy(userDTO.getUserName());
				bolInvDto.setInvoiceType(bolInvDto.getInvoiceType());
				bolInvDto.setInvoiceNumber(StringUtils.capitalise(bolInvDto.getInvoiceNumber()));
				BolInvoice bolInvoice = bolInvMapper.bolinvoiceDTOtoDoman(bolInvDto);
				bolInvoice.setInvoiceType(invoiceTypeMapper.invoiceTypeDTOtoDoman(bolInvDto.getInvoiceType()));

				/*
				 * bolInvoice.setPath(billOfLaddingFacade.processInvoice(bolInvDto.
				 * getBase64Invoice(), httpServletRequest, bolInvDto.getInvoiceNumber(),
				 * bolInvDto.getFileName()));
				 */
				bolInvoice.setPath(fileProcessingService.processInvoice(bolInvDto.getBase64Invoice(),
						userDTO.getAgentCode(), bolInvDto.getFileName()));
				bol.addInvoices(bolInvoice);

			}
			bolService.saveBoL(bol);
		}
		if (status.equalsIgnoreCase(Constants.DO_STATUS.REJECTED.value)) {
			for (BolInvoice bolInvoice : req.getBol().getBolInvoices()) {
				bolInvoice.setStatus("");
			}
		}
		if (status.equalsIgnoreCase(Constants.DO_STATUS.RETURNED.value)) {
			req.setIsReturned("Y");
		}
		String result = deliveryOrderService.approveDO(req);
		if (result != null) {
			result = updateLog(id, remarkId, otherComments, userDTO);

			if (status.equalsIgnoreCase("RETURNED"))
				result = "RETURN FOR MORE INFORMATION.";
			if (status.equalsIgnoreCase("REJECTED"))
				result = "DO REJECTED.";
		}
		return result;
	}

	@Override
	public String updateLog(String id, String remarkId, String otherComments, UserDTO userDTO) {
		return deliveryOrderService.updateLog(id, remarkId, otherComments, userDTO);
	}

	@Override
	public List<RejectionRemarksDTO> getRejectionRemarks() {
		return deliveryOrderService.getRejectionRemarks();
	}

	@Override
	public List<ReturnRemarksDTO> getReturnRemarks() {
		return deliveryOrderService.getReturnRemarks();
	}

	@Override
	public String getAuthDocumentPathById(Long authDocId) {
		return deliveryOrderService.getAuthDocumentPathById(authDocId);
	}

	@Override
	public String getDODocumentPathById(Long authReqId) {
		return deliveryOrderService.getDODocumentPathById(authReqId);
	}

	@Override
	public String uploadDo(String id, String file, HttpServletRequest httpServletRequest) throws IOException {
		DoAuthRequest req = deliveryOrderService.getDoAuthrequest(Long.valueOf(id));
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		req.setModifiedBy(userDTO.getUserName());
		req.setModifiedDate(new Date());
		if (file != null) {
			if (!file.equalsIgnoreCase("null")) {
				req.setStatus("COMPLETED");
				DeliveryOrder dO = req.getDeliveryOrder();

				ObjectMapper mapper = new ObjectMapper();
				final JsonNode fileNode = mapper.readTree(file);
				final String uploadDoc = fileNode.get("uploadDoc").asText();
				final String oblCopy = fileNode.get("oblCopy").asText();
				String[] parts = uploadDoc.split("base64,");
				req.setOblCopy(oblCopy);
				if (parts.length == 2) {
					file = parts[1];
					String path = Utilities.saveDocumentstoPath(file,
							"ApproveDO" + "." + Utilities.getFileExtn(Utilities.decodeBase64(file)), DOApprovefilePath,
							req.getBol().getBolNumber());
					dO.setDocumentPath(path);
					dO.setModifiedBy(userDTO.getUserName());
					dO.setModifiedDate(new Date());
					req.setDeliveryOrder(dO);
					if (req.getApprovalLog() != null) {

						for (ApprovalLog approvalLog : req.getApprovalLog()) {
							approvalLog.setModifiedBy(userDTO.getUserName());
							approvalLog.setModifiedDate(new Date());
						}

					}
				}

			}
			return deliveryOrderService.approveDO(req);
		}
		return null;
	}

	@Override
	public byte[] generateConsigneeReport(SearchDTO dto, UserDTO userDTO, HttpServletResponse response)
			throws Exception {
		return deliveryOrderService.generateConsigneeReport(dto, userDTO, response);
	}

	@Override
	public String savePayProof(DoAuthRequestDTO savedDoAuthRequestDTO, DoAuthRequestDTO dataDTO, String doc,
			HttpServletRequest httpServletRequest) throws IOException {
		return deliveryOrderService.savePayProof(savedDoAuthRequestDTO, dataDTO, doc, httpServletRequest);
	}

	@Override
	public List<SelectDto> getPaymentOption(String agentCode, UserDTO userDTO) {
		return deliveryOrderService.getPaymentOption(agentCode, agentCode);
	}

	@Override
	public String getAdminEmailByAgentCode(String agentCode, List<String> subIds) {
		return deliveryOrderService.getAdminEmailByAgentCode(agentCode, subIds);
	}

	@Override
	public String getAdminEmailByAgentCodeReqParty(String agentCode, List<String> subIds, String userName) {
		List<DTUserView> emailViewList = deliveryOrderService.getAdminEmailByAgentCodeReqParty(agentCode, subIds,
				userName);
		if (null != emailViewList && emailViewList.size() > 0) {
			return emailViewList.get(0).getEmail();
		} else {
			return null;
		}
	}

	@Override
	public byte[] recieptWithTransactionID(String transactionId, UserDTO userDTO, HttpServletResponse response)
			throws Exception {
		return deliveryOrderService.recieptWithTransactionID(transactionId, userDTO, response);
	}

	@Override
	public Long getDoauthReqVersion(Long id) {
		return deliveryOrderService.getDoauthReqVersion(id);
	}

	@Override
	public PaymentOfProof findByPayProofId(Long id) {
		return deliveryOrderService.findByPayProofId(id);
	}

	@Override
	public PaginationDTO<ViewDTO> getDOrequestDetailsMultipleInvoice(SearchDTO searchDTO, Pageable pageable,
			UserDTO userDTO, String sortorder, String sortCol, String userType) {
		PaginationDTO<ViewDTO> emptyDTO = new PaginationDTO<ViewDTO>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Page<DoAuthRequest> pageDoAuthRequest = deliveryOrderService.getDOrequestDetails(searchDTO, pageable, userDTO,
				sortorder, sortCol);
		String bolNo = "";
		List<ViewDTO> viewDTOList = doAuthRequestMapper.doAuthRequestDomaintoViewDTO(pageDoAuthRequest);
		if (null != pageDoAuthRequest && pageDoAuthRequest.getContent().size() > 0) {
			int i = 0;
			for (ViewDTO viewDTO : viewDTOList) {
				BoLDTO bolDTO = bolService
						.getBolDetailObject(pageDoAuthRequest.getContent().get(i).getBol().getBolNumber());
				viewDTO.setConvertedDate(format.format(pageDoAuthRequest.getContent().get(i).getCreatedDate()));
				viewDTO.setBolCreatedDate(bolDTO.getCreatedDate().toString());
				viewDTO.setBolNumber(bolDTO.getBolNumber());
				viewDTO.setBolId(bolDTO.getBolId());
				viewDTO.setBolStatus(bolDTO.getStatus());
				String bolNoEncr = EncryptionUtil.encrypt(bolDTO.getBolNumber());
				viewDTO.setBolNumberEncr(bolNoEncr);
				viewDTO.setDoAuthRequestIdEncr(EncryptionUtil.encrypt(String.valueOf(viewDTO.getDoAuthRequestId())));
				viewDTO.setDoCreatedDate(format.format(pageDoAuthRequest.getContent().get(i).getCreatedDate()));
				if (userType.equalsIgnoreCase(Constants.USER_TYPE.A.desc)) {
					if (!viewDTO.getStatus().equalsIgnoreCase(Constants.DO_STATUS.PENDING_FOR_APPROVAL.value)) {
						viewDTO.setAction(new String[] { "view" });
					} else {
						viewDTO.setAction(new String[] { "edit" });
					}
				} else if (userType.equalsIgnoreCase(Constants.USER_TYPE.I.desc)) {
					if (viewDTO.getStatus().equalsIgnoreCase(Constants.DO_STATUS.COMPLETED.value)
							|| viewDTO.getStatus().equalsIgnoreCase(Constants.DO_STATUS.REJECTED.value)) {
						viewDTO.setAction(new String[] { "view" });
					} else {
						viewDTO.setAction(new String[] { "edit" });
					}
				} else {
					viewDTO.setAction(new String[] { "view" });
				}
				i++;
			}

			PageDto pageDTO = new PageDto();
			pageDTO.setPageNumber(pageable.getPageNumber());
			pageDTO.setTotalElements(pageDoAuthRequest.getTotalElements());
			pageDTO.setSize(pageable.getPageSize());
			pageDTO.setTotalPages(pageDoAuthRequest.getTotalPages());
			return new PaginationDTO<ViewDTO>(viewDTOList, pageable.getPageNumber(), pageDTO.getTotalElements(),
					pageable.getPageSize());
		}
		return emptyDTO;
	}

	@Override
	public SARosoomDTO getRosoomDetailByShippingAgentId(Long shippingAgentId, String currency) {
		return deliveryOrderService.getRosoomDetailByShippingAgentId(shippingAgentId, currency);

	}

	@Override
	public ShippingAgent getShippingAgentFromPaymentLog(Long doAuthRequestId) {
		return deliveryOrderService.getShippingAgentFromPaymentLog(doAuthRequestId);
	}

	@Override
	public String getCollection(String invoiceNo) {
		Collection collection = deliveryOrderService.getCollection(invoiceNo);
		return collection.getTransactionId();

	}

	@Override
	public String getDocumentPath(String collectionId) {
		return deliveryOrderService.getDocumentPath(collectionId);
	}

	@Override
	public String getAdminEmailId(String agentCode) {
		return deliveryOrderService.findDistinctByAgentCode(agentCode);
	}

	@Override
	public DoAuthRequestDTO updatePaymentLogForPaymentPendingWithImporter(Long doAuthRequestId,
			List<String> selectedInvoice, UserDTO userDto) {
		return deliveryOrderService.updatePaymentLogForPaymentPendingWithImporter(doAuthRequestId, selectedInvoice,
				userDto);
	}

	@Override
	public void updateApproverViewedStatus(Long doAuthReqId, UserDTO userDTO) {
		DoAuthRequest doAuthReq = doAuthRequestRepository.findById(doAuthReqId);
		if (doAuthReq.getApprovedViewed() != null) {
			if (!StringUtils.equalsIgnoreCase(doAuthReq.getApprovedViewed(), Constants.Approver_view.Y.value)) {
				doAuthReq.setApprovedViewed("Y");
				doAuthReq.setViewedBy(userDTO.getUserName());
				doAuthReq.setViewedDate(new Date());
				deliveryOrderService.updateApproverViewedStatus(doAuthReq);
			}
		} else {
			doAuthReq.setApprovedViewed("Y");
			doAuthReq.setViewedBy(userDTO.getUserName());
			doAuthReq.setViewedDate(new Date());
			deliveryOrderService.updateApproverViewedStatus(doAuthReq);
		}
	
	}

	@Override
	public DoAuthRequestDTO getApproverViewedStatus(Long doAuthReqId, UserDTO userDTO) {
		return deliveryOrderService.getApproverViewedStatus(doAuthReqId, userDTO);
	}

	@Override
	public Double getPayingAmountByInvoiceNumber(List<String> payInvoiceList, String bolNo) {
		return deliveryOrderService.getPayingAmountByInvoiceNumber(payInvoiceList, bolNo);
	}

	@Override
	public List<DoAuthRequestDTO> getDODetailByAuthRequestId(Long authRequestId) {

		StopWatch sw = new StopWatch("getDODetailByAuthRequestId");
		sw.start();
		List<DoAuthRequestDTO> doAuthRequestList = deliveryOrderService.getDODetailByAuthRequestId(authRequestId);
		sw.stop();
		logger.info("Test in FACADE:" + sw.getTotalTimeSeconds());
		return doAuthRequestList;
	}

	@Override
	public ResponseRosoomDto getResponseStatus(DoAuthRequestDTO doAuthRequestDTORes, DoAuthRequestDTO doAuthRequestDTO,
			UserDTO userDTO, HttpServletRequest httpServletRequest) {
		ResponseRosoomDto responseRosoomDto = new ResponseRosoomDto();
		if (doAuthRequestDTORes.getIsAmend()) {
			String soTransactionId = null;
			if (doAuthRequestDTORes.getPaymentLogs() != null)
				for (PaymentLogDTO pay : doAuthRequestDTORes.getPaymentLogs()) {
					soTransactionId = pay.getTransactionId();
				}

			responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
					.message("DO AMENDED SUCCESSFULLY.").code(soTransactionId).build();

		} else {
			responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
					.message("REQUEST COMPLETED SUCCESSFULLY WITH DO REFERENCE NUMBER: "
							+ doAuthRequestDTORes.getDoRefNo())
					.code(null).build();
		}

		if (doAuthRequestDTO.getIsCredit() || doAuthRequestDTO.getIsPayProof()) {
			String status = null;
			if (doAuthRequestDTO.getIsCredit()) {
				status = "Payment through Credit Succesfully updated. DO Reference No. "
						+ doAuthRequestDTORes.getDoRefNo();
				if (doAuthRequestDTORes.getIsPartialPayment())
					status = "Payment through Credit Succesfully updated and it is partial payment.DO request will processed only after complete payment.  DO Reference No."
							+ doAuthRequestDTORes.getDoRefNo();

				if (doAuthRequestDTORes.getStatus() != "FAILED")
					responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS)
							.data(null).message(status).code(null).build();
				else
					responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL).data(null)
							.message("Payment through Credit Failed.").code(null).build();
			} else {
				status = "Upload Payment Proof Succesfully updated. DO Reference No. "
						+ doAuthRequestDTORes.getDoRefNo();
				if (doAuthRequestDTORes.getIsPartialPayment())
					status = "Upload Payment Proof Succesfully updated and it is partial payment.DO request will processed only after complete payment.  DO Reference No."
							+ doAuthRequestDTORes.getDoRefNo();

			}
			if (doAuthRequestDTORes.getStatus() != "FAILED")
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
						.message(status).code(null).build();
			else {
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL).data(null)
						.message("Upload Payment Proof Failed.").code(null).build();
			}
		} else if (doAuthRequestDTO.getIsPayImporter()) {
			if (doAuthRequestDTORes.getStatus() != "FAILED")
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
						.message("REQUEST COMPLETED SUCCESSFULLY WITH DO REFERENCE NUMBER: "
								+ doAuthRequestDTORes.getDoRefNo())
						.code(null).build();
		}

		return responseRosoomDto;
	}

	@Override
	public String getAgentNameByAgentCode(String agentCode, List<String> subIds) {
		return deliveryOrderService.getAgentNameByAgentCode(agentCode, subIds);
	}

	@Override
	public DTAdminEmailView getAdminDetailsByAgentCode(String agentCode, List<String> subIds) {
		return deliveryOrderService.getAdminDetailsByAgentCode(agentCode, subIds);
	}
}