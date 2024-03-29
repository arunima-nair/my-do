package ae.dt.deliveryorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import oracle.jdbc.driver.Const;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pcfc.dw.cepg.signature.PaymentInitSignData;

import ae.dt.common.constants.Constants;
import ae.dt.common.controller.BaseController;
import ae.dt.common.dto.DataDto;
import ae.dt.common.dto.MailDTO;
import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.PartyDetailsDTO;
import ae.dt.common.dto.ResponseDto;
import ae.dt.common.dto.ResponseRosoomDto;
import ae.dt.common.dto.SelectDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.dto.ViewDTO;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.common.util.Utilities;
import ae.dt.deliveryorder.domain.DTAdminEmailView;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
import ae.dt.deliveryorder.dto.RejectionRemarksDTO;
import ae.dt.deliveryorder.dto.ReturnRemarksDTO;
import ae.dt.deliveryorder.dto.SARosoomDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import ae.dt.deliveryorder.facade.AuthoriseDeliveryOrderFacade;
import ae.dt.deliveryorder.facade.BillOfLaddingFacade;
import ae.dt.deliveryorder.facade.DeliveryOrderFacade;
import ae.dt.deliveryorder.processors.RosoomEnquiryProcessor;
import ae.dt.deliveryorder.service.AuthoriseDeliveryOrderService;
import ae.dt.deliveryorder.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
/**
 * @author ARUNIMA NAIR
 *
 */
@Slf4j
@RestController

public class DeliveryOrderController extends BaseController {

	@Autowired
	DeliveryOrderFacade deliveryOrderFacade;
	@Autowired
	BillOfLaddingFacade billOfLaddingFacade;
	@Autowired
	EmailNotificationService emailNotificationService;
	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	@Autowired
	RosoomEnquiryProcessor rosoomEnquiryProcessor;
	@Autowired
	AuthoriseDeliveryOrderService authoriseDeliveryOrderService;
	@Autowired
	AuthoriseDeliveryOrderFacade authoriseDeliveryOrderFacade;

	@Autowired
	private Environment env;

	Logger logger = LoggerFactory.getLogger(DeliveryOrderController.class);

	@Value("${rosoom.payinit.keyStore.location}")
	private String rosoomKeyStoreLocation;
	@Value("${rosoom.payinit.keyStore.keyStorePassword}")
	private String rosoomKeyStorePassword;
	@Value("${rosoom.payinit.keyStore.keyPassword}")
	private String rosoomKeyPassword;
	@Value("${rosoom.payinit.keyStore.keyAlias}")
	private String rosoomKeyAlias;
	@Value("${doSign.signatureAlgorithm}")
	private String signatureAlgorithm;

	@Value("${cc.email}")
	private String ccEmail;
	/*
	 * @Value("${rosoom.serviceownerid}") private String serviceOwnerId;
	 * 
	 * @Value("${rosoom.licenseKey}") private String rosoomLicenseKey;
	 * 
	 * @Value("${rosoom.serviceid}") private String rosoomServiceId;
	 */
	@Value("${rosoom.channel}")
	private String rosoomChannel;
	@Value("${rosoom.service.description}")
	private String serviceDesc;
	@Value("${rosoom.document.charges}")
	private String documentCharges;
	@Value("${rosoom.bu.encryption.mode}")
	private String encryptionMode;
	@Value("${rosoom.gateway.url}")
	private String gatewayUrl;
	@Value("${rosoom.response.url}")
	private String responseUrl;
	@Value("${local.key.location}")
	private String localKeyLocation;


	/**
	 * rejectReturnDO API This API reject/return for more details of Delivery Order
	 * details.
	 * 
	 * @param status,authReqId,comments,remarkId
	 * @return ResponseDto containing message string
	 */

	@PostMapping(path = "/app/api/secure/rejectReturnDO")
	public ResponseDto rejectReturnDO(/*
										 * @RequestParam("status") String Status, @RequestParam("id") String id,
										 * 
										 * @RequestParam("remarkId") String remarkId, @RequestParam("comments") String
										 * otherComments,
										 */ @RequestBody List<BolInvoiceDTO> bolItems,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {
		String result = null;
		// List<BolInvoiceDTO> boldto=new ArrayList<BolInvoiceDTO>(bolItems);
		String id = EncryptionUtil.decrypt(httpServletRequest.getParameter("id"));
		String Status = httpServletRequest.getParameter("status");
		String otherComments = httpServletRequest.getParameter("comments");
		String remarkId = httpServletRequest.getParameter("remarkId");

		result = deliveryOrderFacade.rejectReturnDo(id, Status, remarkId, otherComments, bolItems, httpServletRequest);
		if (result != null) {
			return new ResponseDto(Constants.REST_STATUS_SUCCESS, null, result, null);
		} else
			return new ResponseDto(Constants.REST_STATUS_FAIL, null, "FAILED.", null);

	}

	/**
	 * getDOrequest API This API APPROVE DO.
	 * 
	 * @param DataDto
	 * @return ResponseDto containing string
	 */
	@PostMapping(path = "/app/api/secure/approveDO")
	public ResponseDto processDOrequestDetails(@RequestParam("status") String Status, @RequestParam("id") String id,
			@RequestBody String file, HttpServletRequest httpServletRequest) throws IOException, ParseException {
		String result = null;
		// deliveryOrderFacade.getDorequest(Long.valueOf(id));
		id = EncryptionUtil.decrypt(id);
		if (Status.equalsIgnoreCase("RETURNED") || Status.equalsIgnoreCase("REJECTED")
				|| Status.equalsIgnoreCase("CANCELLED")) {
			file = null;
		}
		result = deliveryOrderFacade.approveDOrequestDetails(id, Status, file, httpServletRequest);

		if (result != null) {
			UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
			result = deliveryOrderFacade.updateLog(id, null, null, userDTO);
			if (Status.equalsIgnoreCase("COMPLETED"))
				result = "DO Approved Successfully";
			if (Status.equalsIgnoreCase("RETURNED"))
				result = "RETURNED FOR MORE INFORMATION .";
			if (Status.equalsIgnoreCase("REJECTED"))
				result = "DO REJECTED.";
			if (Status.equalsIgnoreCase("CANCELLED"))
				result = "DO CANCELLED.";

		} else
			return new ResponseDto(Constants.REST_STATUS_FAIL, null, "FAILED.", null);
		return new ResponseDto(Constants.REST_STATUS_SUCCESS, null, result, null);

	}

	@RequestMapping(path = "/api/secure/formRosoomPaymentUrl")
	private ResponseRosoomDto formRosoomPaymentUrl(@RequestBody DoAuthRequestDTO doAuthRequestDTO, Double payingAmt,
			HttpServletRequest httpServletRequest) throws IOException {

		double totalAmount = 0D;
		String currency = "AED";
		String soTransactionId = doAuthRequestDTO.getTransactionId();
		totalAmount = payingAmt;

		ShippingAgent shippingAgent = null;
		shippingAgent = deliveryOrderFacade.getShippingAgentFromPaymentLog(doAuthRequestDTO.getDoAuthRequestId());
		if (shippingAgent != null) {
			SARosoomDTO sARosoomDTO = deliveryOrderFacade
					.getRosoomDetailByShippingAgentId(shippingAgent.getShippingAgentId(), currency);
			if (soTransactionId != null) {
				if (sARosoomDTO != null) {
					String base64signatureString = null;
					PaymentInitSignData signData = new PaymentInitSignData();
					String custRefNo = String.valueOf(doAuthRequestDTO.getDoRefNo());
					String doKeyStoreLocation = sARosoomDTO.getKeyStoreLoc();

					if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
						doKeyStoreLocation = localKeyLocation;

					} else {
						doKeyStoreLocation = sARosoomDTO.getKeyStoreLoc();

					}

					String doKeyStorePassword = sARosoomDTO.getKeyStorePassword();
					String doKeyAlias = sARosoomDTO.getKeyAlias();
					String doKeyPassword = sARosoomDTO.getKeyPassword();
					try {

						KeyStore keyStore = (KeyStore) signData.loadKeystore(doKeyStoreLocation, doKeyStorePassword);
						PrivateKey privateKey = (PrivateKey) keyStore.getKey(doKeyAlias, doKeyPassword.toCharArray());
						String payload = "soTransactionID::" + soTransactionId + "||serviceID::"
								+ sARosoomDTO.getRosoomServiceId() + "||serviceOwnerID::"
								+ sARosoomDTO.getServiceOwnerId() + "||serviceCost::" + String.valueOf(totalAmount)
								+ "||documentationCharges::" + documentCharges + "||serviceDescription::" + serviceDesc
								+ "||responseURL::" + responseUrl + "||licenseKey::" + sARosoomDTO.getRosoomLicenseKey()
								+ "||serviceChannel::" + rosoomChannel + "||customerReferenceNumber::" + custRefNo
								+ "||buEncryptionMode::" + encryptionMode + "||";
						byte[] signedPayload = signData.sign(payload, privateKey, signatureAlgorithm);
						log.info("signedPayload----", signedPayload);
						base64signatureString = Base64.encodeBase64URLSafeString(signedPayload);

					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					String isAmend= doAuthRequestDTO.getIsAmend()?"AMEND":"";
					ResponseRosoomDto responseRosoomDto = new ResponseRosoomDto().builder().gatewayUrl(gatewayUrl)
							.serviceOwnerId(sARosoomDTO.getServiceOwnerId())
							.rosoomServiceId(sARosoomDTO.getRosoomServiceId()).rosoomChannel(rosoomChannel)
							.rosoomLicenseKey(sARosoomDTO.getRosoomLicenseKey()).custRefNo(custRefNo)
							.serviceDesc(serviceDesc).responseUrl(responseUrl).totalAmount(String.valueOf(totalAmount))
							.soTransactionId(soTransactionId).custRefNo(custRefNo).soTransactionId(soTransactionId)
							.documentCharges(documentCharges).base64signatureString(base64signatureString).popup("YES")
							.buEncryptionMode(encryptionMode)
							// .invoiceNo(invno)
							.build();
					return responseRosoomDto;
				} else {

				}

			}
		}
		return null;
	}

	@RequestMapping(path = "/app/api/secure/searchDO")
	public PaginationDTO<ViewDTO> searchDO(@RequestParam("bolNo") String bolNo, @RequestParam("doRefNo") String doRefNo,
			@RequestParam("status") String status, Pageable pageable, HttpServletRequest httpServletRequest)
			throws IOException, ParseException {
		if (bolNo.equalsIgnoreCase("null"))
			bolNo = "";
		if (doRefNo.equalsIgnoreCase("null"))
			doRefNo = "";
		if (status.equalsIgnoreCase("null") || status.equalsIgnoreCase("CHOOSE"))
			status = "";
		SearchDTO searchDTO = new SearchDTO().builder().bolNumber(bolNo).doRefNo(doRefNo).status(status).build();
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		String pgSize = (String) httpServletRequest.getParameter("pgSize");
		String pgNo = (String) httpServletRequest.getParameter("pgNo");
		String sortorder = (String) httpServletRequest.getParameter("sort_order");
		String sortcol = (String) httpServletRequest.getParameter("sort_col");
		String sortCol = "";
		if (sortcol.equalsIgnoreCase("0"))
			sortCol = "doRefNo";
		if (sortcol.equalsIgnoreCase("1"))
			sortCol = "bolNumber";
		if (sortcol.equalsIgnoreCase("5"))
			sortCol = "createdDate";
		pageable = new PageRequest(Integer.valueOf(pgNo) - 1, Integer.valueOf(pgSize));
		return deliveryOrderFacade.getDOrequestDetailsMultipleInvoice(searchDTO, pageable, userDTO, sortorder, sortCol,userDTO.getUserType());

	}

	@RequestMapping(path = "/app/api/secure/searchDOInternal")
	public PaginationDTO<ViewDTO> searchDOInternal(@RequestParam("bolNo") String bolNo,
			@RequestParam("doRefNo") String doRefNo, @RequestParam("status") String status,
			@RequestParam("importerName") String importerName, @RequestParam("shippingAgent") String shippingAgent,
			Pageable pageable, HttpServletRequest httpServletRequest) throws IOException, ParseException {
		if (bolNo.equalsIgnoreCase("null"))
			bolNo = "";
		if (doRefNo.equalsIgnoreCase("null"))
			doRefNo = "";
		if (status.equalsIgnoreCase("null"))
			status = "";
		if (importerName.equalsIgnoreCase("null") || importerName.equalsIgnoreCase("undefined"))
			importerName = "";
		if (shippingAgent.equalsIgnoreCase("null") || shippingAgent.equalsIgnoreCase("undefined"))
			shippingAgent = "";
		SearchDTO searchDTO = new SearchDTO().builder().bolNumber(bolNo).doRefNo(doRefNo).status(status)
				.impCode(importerName).shippingAgentCode(shippingAgent).build();
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		String pgSize = (String) httpServletRequest.getParameter("pgSize");
		String pgNo = (String) httpServletRequest.getParameter("pgNo");
		String sortorder = (String) httpServletRequest.getParameter("sort_order");
		String sortcol = (String) httpServletRequest.getParameter("sort_col");
		String sortCol = "";
		if (sortcol.equalsIgnoreCase("0"))
			sortCol = "doRefNo";
		if (sortcol.equalsIgnoreCase("1"))
			sortCol = "bolNumber";
		pageable = new PageRequest(Integer.valueOf(pgNo) - 1, Integer.valueOf(pgSize));
		userDTO.setUserType("IN");
		return deliveryOrderFacade.getDOrequestDetailsMultipleInvoice(searchDTO, pageable, userDTO, sortorder, sortCol,userDTO.getUserType());

	}

	@RequestMapping(path = "/app/api/secure/searchDOPending")
	public PaginationDTO<ViewDTO> searchDOPending(@RequestParam("bolNo") String bolNo,
			@RequestParam("doRefNo") String doRefNo, Pageable pageable, HttpServletRequest httpServletRequest)
			throws IOException, ParseException {
		SearchDTO searchDTO = new SearchDTO();
		if (bolNo.isEmpty())
			bolNo = "";
		if (doRefNo.isEmpty())
			doRefNo = "";

		if (!doRefNo.isEmpty() || !bolNo.isEmpty()) {
			DoAuthRequest authRequest = new DoAuthRequest();
			String status = "";
			if (doRefNo != null) {
				authRequest = authoriseDeliveryOrderFacade.getDoAuthRequest(doRefNo);
				if (authRequest != null) {
					status = authRequest.getStatus();
				}
			}
			if (!bolNo.isEmpty()) {
				status = billOfLaddingFacade.getBolStatus(bolNo);
			}
			if (authRequest != null || status != null)
				if (status.equalsIgnoreCase(Constants.BOL_STATUS.DO_INITIATED.value)
						|| status.equalsIgnoreCase(Constants.BOL_STATUS.PARTIAL_PAYMENT.value)) {
					searchDTO = new SearchDTO().builder().bolNumber(bolNo).doRefNo(doRefNo)
							.invoiceStatus(Constants.BOLINVOICE_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value)
							.status(status).build();
				}
				else {
					searchDTO = new SearchDTO().builder().bolNumber(bolNo).doRefNo(doRefNo)
							.invoiceStatus(Constants.BOLINVOICE_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value)
							.status(Constants.DO_STATUS.DO_INITIATED.value).build();
				}
		} else
			searchDTO = new SearchDTO().builder().bolNumber(bolNo).doRefNo(doRefNo)
					.invoiceStatus(Constants.BOLINVOICE_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value)
					.status(Constants.DO_STATUS.DO_INITIATED.value).build();
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		String pgSize = (String) httpServletRequest.getParameter("pgSize");
		String pgNo = (String) httpServletRequest.getParameter("pgNo");
		String sortorder = (String) httpServletRequest.getParameter("sort_order");
		String sortcol = (String) httpServletRequest.getParameter("sort_col");
		String sortCol = "";
		if (sortcol.equalsIgnoreCase("0"))
			sortCol = "doRefNo";
		if (sortcol.equalsIgnoreCase("1"))
			sortCol = "bolNumber";
		pageable = new PageRequest(Integer.valueOf(pgNo) - 1, Integer.valueOf(pgSize));
		// return deliveryOrderFacade.getDOrequestDetails(searchDTO, pageable, userDTO,
		// sortorder, sortCol);
		return deliveryOrderFacade.getDOPendingPaymentDetails(searchDTO, pageable, userDTO, sortorder, sortCol);
	}

	@RequestMapping(path = "/app/api/secure/searchDOApprov")
	public PaginationDTO<ViewDTO> searchDOApprov(Pageable pageable, HttpServletRequest httpServletRequest)
			throws IOException, ParseException {
		String bolNo = (String) httpServletRequest.getParameter("bolNo");
		String doRefNo = (String) httpServletRequest.getParameter("doRefNo");
		String status = (String) httpServletRequest.getParameter("status");
		String frmDate = (String) httpServletRequest.getParameter("frmDate");
		String toDate = (String) httpServletRequest.getParameter("toDate");
		String reqPartyEmail = (String) httpServletRequest.getParameter("reqPartyEmail");
		String reqParty = (String) httpServletRequest.getParameter("reqParty");
		String doParty = (String) httpServletRequest.getParameter("doParty");
		if (bolNo.equalsIgnoreCase("null"))
			bolNo = "";
		if (doRefNo.equalsIgnoreCase("null"))
			doRefNo = "";
		if (status.equalsIgnoreCase("null"))
			status = "PENDING_FOR_APPROVAL";
		if (frmDate == null)
			frmDate = "";
		else if (frmDate.equalsIgnoreCase("null") || frmDate.equalsIgnoreCase("01-01-1970")
				|| frmDate.equalsIgnoreCase(""))
			frmDate = "";
		if (toDate == null)
			toDate = "";
		else if (toDate.equalsIgnoreCase("null") || toDate.equalsIgnoreCase("01-01-1970")
				|| toDate.equalsIgnoreCase(""))
			toDate = "";
		if (reqPartyEmail.equalsIgnoreCase("null"))
			reqPartyEmail = "";
		if (reqParty.equalsIgnoreCase("null"))
			reqParty = "";
		if (doParty.equalsIgnoreCase("null"))
			doParty = "";
		SearchDTO searchDTO = new SearchDTO().builder().bolNumber(bolNo).doRefNo(doRefNo).status(status)
				.frmDate(frmDate).toDate(toDate).reqParty(reqParty).reqPartyEmail(reqPartyEmail).doParty(doParty)
				.build();
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		String pgSize = (String) httpServletRequest.getParameter("pgSize");
		String pgNo = (String) httpServletRequest.getParameter("pgNo");
		pageable = new PageRequest(Integer.valueOf(pgNo) - 1, Integer.valueOf(pgSize));
		String sortorder = (String) httpServletRequest.getParameter("sort_order");
		String sortcol = (String) httpServletRequest.getParameter("sort_col");
		String sortCol = "";
		if (sortcol.equalsIgnoreCase("0"))
			sortCol = "doRefNo";
		if (sortcol.equalsIgnoreCase("1"))
			sortCol = "bolNumber";
		if (sortcol.equalsIgnoreCase("4"))
			sortCol = "createdDate";
		if (sortcol.equalsIgnoreCase("6"))
			sortCol = "isReturned";
		return deliveryOrderFacade.getDOrequestDetails(searchDTO, pageable, userDTO, sortorder, sortCol);

	}
	

	/**
	 * paymentDOrequest API This API Payment DO.
	 * 
	 * @param dataDTO
	 * @return ResponseDto containing message string URL
	 */
	@PostMapping(path = "/app/api/secure/paymentDOProof")
	public ResponseDto paymentDOProof(@Valid @RequestParam("bolNo") String bolNo, @RequestBody String doc,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {
		log.info("Payment DO REQUEST !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		String Status = "";

		BoLDTO bolDto = billOfLaddingFacade.getBolDetailObject(bolNo);
		DoAuthRequestDTO savedDoAuthRequestDTO = bolDto.getDoAuthRequests().stream().filter(f -> f != null).map(f -> f)
				.findAny().orElse(null);
		savedDoAuthRequestDTO.setBol(bolDto);

		Status = deliveryOrderFacade.savePayProof(savedDoAuthRequestDTO, null, doc, httpServletRequest);
		return new ResponseDto(Constants.REST_STATUS_SUCCESS, null, Status, null);
	}

	@RequestMapping(value = "/app/api/file/downloadauthDocs")
	public String loadFile(@RequestParam("id") String id, HttpServletResponse response)
			throws URISyntaxException, IOException {

		String path = null;
		InputStream is;
		id = EncryptionUtil.decrypt(id);
		path = deliveryOrderFacade.getAuthDocumentPathById(Long.valueOf(id));

		if (path != null) {

			File file = new File(path);
			is = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();

		} else {
			return "File Not Found.";
		}

		return "SUCCESS";
	}

	@RequestMapping(value = "/app/api/file/downloadDo")
	public String downloadDo(@RequestParam("id") String id, HttpServletResponse response)
			throws URISyntaxException, IOException {

		String path = null;
		InputStream is;

		Long docId = Long.valueOf(EncryptionUtil.decrypt(id));
		path = deliveryOrderFacade.getDODocumentPathById(docId);

		if (path != null) {
			File file = new File(path);
			is = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		} else {
			return "File Not Found.";
		}

		return "SUCCESS";
	}

	@GetMapping(path = "/app/api/secure/getRejectionRemarks")
	public List<RejectionRemarksDTO> getRejectionRemarks() throws IOException, ParseException {

		return deliveryOrderFacade.getRejectionRemarks();

	}

	@GetMapping(path = "/app/api/secure/getReturnRemarks")
	public List<ReturnRemarksDTO> getReturnRemarks() throws IOException, ParseException {

		return deliveryOrderFacade.getReturnRemarks();

	}

	@RequestMapping(path = "/app/api/secure/uploadDO")
	public ResponseDto uploadDO(@RequestParam("id") String id, @RequestBody String file,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {
		id = EncryptionUtil.decrypt(id);
		String status = deliveryOrderFacade.uploadDo(id, file, httpServletRequest);
		if (status.equalsIgnoreCase("SUCCESS"))
			return new ResponseDto(Constants.REST_STATUS_SUCCESS, null, "DO UPLOADED SUCCESSFULLY.", null);
		else
			return new ResponseDto(Constants.REST_STATUS_FAIL, null, "DO UPLOADING FAILED.", null);

	}

	@RequestMapping(path = "/app/api/file/consigneeReport")
	public ResponseEntity<byte[]> consigneeReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("agentCode") String agentCode,
			@RequestParam("agentType") String agentType, @RequestParam("status") String status,
			@RequestParam("userType") String userType, HttpServletRequest httpServletRequest,
			HttpServletResponse response) throws Exception {
		SearchDTO dto = new SearchDTO();
		dto.setFrmDate(fromDate);
		dto.setToDate(toDate);
		dto.setStatus(status);
		dto.setShippingAgentCode(agentCode);
		dto.setImpCode(null);
		// UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		UserDTO userDTO = new UserDTO();
		userDTO.setAgentCode(agentCode);
		userDTO.setAgentType(agentType);
		userDTO.setUserType(userType);

		byte[] bytes = deliveryOrderFacade.generateConsigneeReport(dto, userDTO, response);
		return ResponseEntity.ok().header("Content-Type", "application/vnd.ms-excel; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + "REPORT.xls\"").body(bytes);
	}

	@RequestMapping(path = "/app/api/file/consigneeReportInternal")
	public ResponseEntity<byte[]> consigneeReportInternal(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("agentCode") String agentCode,
			@RequestParam("agentType") String agentType, @RequestParam("status") String status,
			@RequestParam("userType") String userType, @RequestParam("shippingAgent") String shippingAgentCode,
			@RequestParam("importerName") String importerCode, HttpServletRequest httpServletRequest,
			HttpServletResponse response) throws Exception {
		SearchDTO dto = new SearchDTO();
		dto.setFrmDate(fromDate);
		dto.setToDate(toDate);
		dto.setStatus(status);
		dto.setShippingAgentCode(shippingAgentCode);
		dto.setImpCode(importerCode);
		UserDTO userDTO = new UserDTO();
		userDTO.setAgentCode(agentCode);
		userDTO.setAgentType(agentType);
		userDTO.setUserType(userType);

		UserDTO sessionUserDTO = (UserDTO) httpServletRequest.getSession().getAttribute("userDTO");
		if (sessionUserDTO.getUserType() == "A") {
			userDTO = sessionUserDTO;
			dto.setShippingAgentCode(sessionUserDTO.getAgentCode());
			dto.setImpCode("");
		} else if (sessionUserDTO.getUserType() == "I") {
			userDTO = sessionUserDTO;
			dto.setShippingAgentCode("");
			dto.setImpCode(sessionUserDTO.getAgentCode());
		}

		// userDTO.setUserType("IN");
		byte[] bytes = deliveryOrderFacade.generateConsigneeReport(dto, userDTO, response);
		return ResponseEntity.ok().header("Content-Type", "application/vnd.ms-excel; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + "REPORT.xls\"").body(bytes);
	}

	@RequestMapping(path = "/app/api/file/recieptTransId")
	public ResponseEntity<byte[]> recieptTransId(@RequestParam("transactionId") String transactionId,
			HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
		transactionId = EncryptionUtil.decrypt(transactionId);
		return getReciept(transactionId, httpServletRequest, response);
	}

	@RequestMapping(path = "/app/api/file/recieptInvoiceNo")
	public ResponseEntity<byte[]> recieptInvoiceNo(@RequestParam("invoiceNo") String invoiceNo,
			HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
		String decrInvoiceNo = EncryptionUtil.decrypt(invoiceNo);
		String tranactionId = deliveryOrderFacade.getCollection(decrInvoiceNo);
		return getReciept(tranactionId, httpServletRequest, response);
	}

	public ResponseEntity<byte[]> getReciept(String id, HttpServletRequest httpServletRequest,
			HttpServletResponse response) throws Exception {
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		if (userDTO == null) {
			HttpSession session = httpSessionFactory.getObject();
			userDTO = (UserDTO) session.getAttribute("userDTO");
		}
		byte[] bytes = deliveryOrderFacade.recieptWithTransactionID(id, userDTO, response);
		if (bytes != null)
			return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8")
					.header("Content-Disposition", "inline; filename=\"" + "Reciept.pdf\"").body(bytes);
		else
			return null;
	}

	@RequestMapping(path = "/app/api/secure/getPaymentOption")
	public List<SelectDto> getPaymentOption(@RequestParam("agentCode") String agentCode,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		return deliveryOrderFacade.getPaymentOption(agentCode, userDTO);

	}

	@RequestMapping(value = "/app/api/secure/getAdminEmailByAgentCode")
	public List<SelectDto> getAdminEmailByAgentCode(@RequestParam("agentCode") String agentCode,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {

		List<String> subIds = new ArrayList<>();
		subIds.add(Constants.AGENT_TYPE.IMPORTER.value);
		subIds.add(Constants.AGENT_TYPE.CLEARING_AGENT.value);
		subIds.add(Constants.AGENT_TYPE.FREEZONE_LICENCE.value);
//		String email = deliveryOrderFacade.getAdminEmailByAgentCode(agentCode, subIds);
		String email = billOfLaddingFacade.getShippingAgentEmailId(agentCode);
		List<SelectDto> emailList = new ArrayList();
		emailList.add(new SelectDto().builder().label(email).value(email).build());
		return emailList;
	}

	@RequestMapping(value = "/app/api/secure/getAdminDetailsByAgentCode")
	public PartyDetailsDTO getAdminDetailsByAgentCode(@RequestParam("agentCode") String agentCode,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {

		List<String> subIds = new ArrayList<>();
		subIds.add(Constants.AGENT_TYPE.IMPORTER.value);
		subIds.add(Constants.AGENT_TYPE.CLEARING_AGENT.value);
		subIds.add(Constants.AGENT_TYPE.FREEZONE_LICENCE.value);
		DTAdminEmailView  dTAdminEmailView = deliveryOrderFacade.getAdminDetailsByAgentCode(agentCode, subIds);
	
		return new PartyDetailsDTO().builder().label(dTAdminEmailView.getAgentCode()).value(dTAdminEmailView.getEmail()).businessSubId(dTAdminEmailView.getBusinessSubId()).build();
	}
	
	@RequestMapping(value = "/app/api/secure/getAgentEmailByAgentCode")
	public List<SelectDto> getAgentEmailByAgentCode(@RequestParam("agentCode") String agentCode,
			HttpServletRequest httpServletRequest) throws IOException, ParseException {

		List<String> subIds = new ArrayList<>();
		subIds.add(Constants.AGENT_TYPE.SHIPPING_AGENT.value);
		subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);
//		String email = deliveryOrderFacade.getAdminEmailByAgentCode(agentCode, subIds);
		String email = billOfLaddingFacade.getShippingAgentEmailId(agentCode);
		List<SelectDto> emailList = new ArrayList();
		emailList.add(new SelectDto().builder().label(email).value(email).build());
		return emailList;
	}

	@RequestMapping(value = "/app/api/file/getPayProof")
	public String getPayProof(@RequestParam("id") Long id, HttpServletResponse response)
			throws URISyntaxException, IOException {

		InputStream is;

		String path = deliveryOrderFacade.findByPayProofId(id).getReference();
		if (path != null) {
			File file = new File(path);
			if (file.exists()) {
				is = new FileInputStream(file);
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
				OutputStream os = response.getOutputStream();
				byte[] buffer = new byte[1024];
				int len;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				os.flush();
				os.close();
				is.close();
			} else {
				return "Payment Proof Not Found.";
			}

		} else {
			return "Payment Proof not Uploaded.";
		}

		return "SUCCESS";
	}

	@GetMapping("/app/api/file/DowloadRecipt")
	public String fileDowload(@RequestParam("collectionId") String collectionId, HttpServletResponse response)
			throws IOException {

		logger.info(
				"********************   Started to downolad the file based on Invoice Number id path = '{}'  ********************",
				collectionId);
		String path = null;
		path = deliveryOrderFacade.getDocumentPath(EncryptionUtil.decrypt(collectionId));
		String resp = super.downloadFile(path, response);
		logger.info("********************   Completed the APIfor download the file  ********************");
		return resp;
	}

	@RequestMapping("/app/api/secure/updateApproverViewedStatus")
	public ResponseDto updateApproverViewedStatus(@RequestParam("authIdEncr") String authIdEncr,
			HttpServletRequest request) {
		UserDTO userDTO = (UserDTO) request.getAttribute("userName");
		deliveryOrderFacade.updateApproverViewedStatus(Long.valueOf(EncryptionUtil.decrypt(authIdEncr)), userDTO);
		// return userDTO.getUserName();
		return new ResponseDto(userDTO.getUserName(), null, null, null);

	}

	@RequestMapping("/app/api/secure/getApproverViewedStatus")
	public DoAuthRequestDTO getApproverViewedStatus(@RequestParam("authIdEncr") String authIdEncr,
			HttpServletRequest request) {
		UserDTO userDTO = (UserDTO) request.getAttribute("userName");
		return deliveryOrderFacade.getApproverViewedStatus(Long.valueOf(EncryptionUtil.decrypt(authIdEncr)), userDTO);
	}

	@RequestMapping("/app/api/file/startScheduler")
	public void schedulerStarter() {
		rosoomEnquiryProcessor.process();
	}

	@RequestMapping(path = "/app/api/secure/getDODetailByAuthRequestId")
	public DataDto<DoAuthRequestDTO> getDODetailByAuthRequestId(@RequestParam("authRequestId") String authRequestIdStr)
			throws IOException, ParseException {
		StopWatch sw = new StopWatch("getDOdetails");
		sw.start();
		Long authRequestId = Long.valueOf(EncryptionUtil.decrypt(authRequestIdStr));
		List<DoAuthRequestDTO> doAuthRequestDTOList = deliveryOrderFacade.getDODetailByAuthRequestId(authRequestId);
		sw.stop();
		logger.info("Test:" + sw.getTotalTimeSeconds());
		return new DataDto<DoAuthRequestDTO>(doAuthRequestDTOList);

	}


	/**
	 * authorizeDOrequest API This API authorize Delivery Order details.
	 * 
	 * @param dataDTO
	 * @return ResponseDto containing message string
	 */
	@PostMapping(path = "/app/api/secure/authorizeDOrequestNew")
	public ResponseRosoomDto authorizeDOrequestNew(@Valid @RequestParam("bol") String bolNo,
			@RequestBody DoAuthRequestDTO doAuthRequestDTO, HttpServletRequest httpServletRequest)
			throws IOException, ParseException {
		log.info("AUTHORIZE DO REQUEST ..............................");
		ResponseRosoomDto responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL)
				.data(null).message("PROCESS FAILED.").code(null).build();
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");

		if (!Utilities.isEmpty(doAuthRequestDTO.getEncrAuthLetterPath()))
			doAuthRequestDTO.setEncrAuthLetterPath(EncryptionUtil.decrypt(doAuthRequestDTO.getEncrAuthLetterPath()));
		if (!Utilities.isEmpty(doAuthRequestDTO.getEncrBolLetterPath()))
			doAuthRequestDTO.setEncrBolLetterPath(EncryptionUtil.decrypt(doAuthRequestDTO.getEncrBolLetterPath()));
		if (!Utilities.isEmpty(doAuthRequestDTO.getEncrEmiratesIdCopyPath()))
			doAuthRequestDTO
					.setEncrEmiratesIdCopyPath(EncryptionUtil.decrypt(doAuthRequestDTO.getEncrEmiratesIdCopyPath()));
		if (!Utilities.isEmpty(doAuthRequestDTO.getEncrOthDocPath()))
			doAuthRequestDTO.setEncrOthDocPath(EncryptionUtil.decrypt(doAuthRequestDTO.getEncrOthDocPath()));

		if (doAuthRequestDTO.getStatus() != null) {/* FOR AMEND DO */
			if (doAuthRequestDTO.getStatus().equalsIgnoreCase("APPROVED")) {
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL).data(null)
						.message("AMEND DO DENIED.").code(null).build();
				return responseRosoomDto;
			}

			if (!doAuthRequestDTO.getStatus().equalsIgnoreCase("NEW")) {
				Long version = deliveryOrderFacade.getDoauthReqVersion(
						Long.valueOf(EncryptionUtil.decrypt(doAuthRequestDTO.getDoAuthRequestIdEncr())));
				if (version != doAuthRequestDTO.getVersion()) {
					return new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL).data(null)
							.message("AMEND DO DENIED. BOL already initiated by another user.Try after sometime.")
							.code(null).build();
				}
			}
		}
		DoAuthRequestDTO doAuthRequestDTORes = authoriseDeliveryOrderFacade.saveAuthorizeDOrequest(bolNo,
				doAuthRequestDTO, userDTO, httpServletRequest);

		if (doAuthRequestDTORes.getIsOnline()) {
			if (doAuthRequestDTORes.getPayingAmt() > 0) {
				responseRosoomDto = formRosoomPaymentUrl(doAuthRequestDTORes, doAuthRequestDTORes.getPayingAmt(),
						httpServletRequest);
				if (doAuthRequestDTORes.getIsAmend() && null != responseRosoomDto) {
					String soTransactionId = null;
					if (doAuthRequestDTORes.getPaymentLogs() != null)
						for (PaymentLogDTO pay : doAuthRequestDTORes.getPaymentLogs()) {
							soTransactionId = pay.getTransactionId();
						}
					responseRosoomDto.setStatus(Constants.REST_STATUS_SUCCESS);
					responseRosoomDto.setMessage("DO AMENDED SUCCESSFULLY.");
					responseRosoomDto.setInvoiceNo(doAuthRequestDTO.getPayingInvoice());
					return responseRosoomDto;

				} else if (!doAuthRequestDTORes.getIsAmend() && null != responseRosoomDto) {
					responseRosoomDto.setStatus(Constants.REST_STATUS_SUCCESS);
					responseRosoomDto.setMessage("Online");
					responseRosoomDto.setInvoiceNo(doAuthRequestDTO.getPayingInvoice());
					return responseRosoomDto;
				} else {
					return responseRosoomDto.builder().status(Constants.REST_STATUS_FAIL).data(null)
							.message("Service Owner not configured for Shipping Agent and Currency").code(null).build();
				}
			} else {
				responseRosoomDto = new ResponseRosoomDto();
				/*
				 * todo check in which scenarion this error message
				 */
				return responseRosoomDto.builder().status(Constants.REST_STATUS_FAIL).data(null)
						.message("Payment should be greated than zero.").code(null).build();
			}
		}
		responseRosoomDto = authoriseDeliveryOrderFacade.getResponseStatus(doAuthRequestDTORes, doAuthRequestDTO,
				userDTO, httpServletRequest);

		return responseRosoomDto;

	}


	@PostMapping(path = "/app/api/secure/paymentDONew")
	public ResponseRosoomDto paymentDOrequestNew(@Valid @RequestParam("doAuthReqIdStr") String doAuthReqIdStr,
			@RequestBody List<String> selectedInvoice, HttpServletRequest httpServletRequest)
			throws IOException, ParseException {
		log.info("Payment DO REQUEST !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		ResponseRosoomDto responseRosoomDto = new ResponseRosoomDto();
		Long doAuthReqId = Long.valueOf(EncryptionUtil.decrypt(doAuthReqIdStr));
		UserDTO userDto = (UserDTO) httpServletRequest.getAttribute(Constants.USER_DTO);
		DoAuthRequestDTO doAuthRequestDTO = deliveryOrderFacade
				.updatePaymentLogForPaymentPendingWithImporter(doAuthReqId, selectedInvoice, userDto);
		if (doAuthRequestDTO.getPayingAmt() > 0) {
			if (null != doAuthRequestDTO) {
				responseRosoomDto = formRosoomPaymentUrl(doAuthRequestDTO, doAuthRequestDTO.getPayingAmt(),
						httpServletRequest);
			}
			if (null != responseRosoomDto) {
				responseRosoomDto.setStatus(Constants.REST_STATUS_SUCCESS);
				responseRosoomDto.setData(null);
				responseRosoomDto.setMessage("Online");
				responseRosoomDto.setCode(null);
			} else {
				responseRosoomDto.builder().status(Constants.REST_STATUS_FAIL).data(null)
						.message("Service Owner not configured for Shipping Agent and Currency").code(null).build();
			}
		}
		return responseRosoomDto;
	}

}
