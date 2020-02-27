package ae.dt.deliveryorder.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ae.dt.common.constants.Constants;
import ae.dt.common.domain.DTAgentView;
import ae.dt.common.dto.MailDTO;
import ae.dt.common.exception.BusinessException;
import ae.dt.common.util.DateUtil;
import ae.dt.common.util.ErrorCodes;
import ae.dt.common.util.Utilities;
import ae.dt.deliveryorder.domain.AlertNotification;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.dto.BOL;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.FileProcessDTO;
import ae.dt.deliveryorder.dto.Invoice;
import ae.dt.deliveryorder.dto.InvoiceUploadDTO;
import ae.dt.deliveryorder.repository.AlertNotificationRepository;
import ae.dt.deliveryorder.repository.BolInvoiceRepository;
import ae.dt.deliveryorder.repository.BolRepository;
import ae.dt.deliveryorder.repository.DTAgentViewRepository;
import ae.dt.deliveryorder.repository.FileLogDetailsRepository;
import ae.dt.deliveryorder.repository.FileLogRepository;
import ae.dt.deliveryorder.repository.InvoiceTypeRepository;
import ae.dt.deliveryorder.specification.BoLInvoiceSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("fileProcessingService")
public class FileProcessingServiceImpl implements FileProcessingService {

	@Autowired
	BolRepository bolrepository;

	@Autowired
	BolInvoiceRepository bolInvoiceRepository;

	@Autowired
	FileLogRepository fileLogrepository;

	@Autowired
	FileLogDetailsRepository fileLogDetailsRepository;

	@Autowired
	BolRepository bolRepository;

	@Autowired
	InvoiceTypeRepository invoiceTypeRepository;
	
	@Autowired
	AlertNotificationRepository alertNotificationRepository;
	
	@Autowired
	DTAgentViewRepository dTAgentViewRepository;

	@Value("${invoicefilepath}")
	private String invoicefilepath;

	@Value("${from.email}")
	private String fromMail;
	@Value("${cc.email}")
	private String ccEmail;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	DeliveryOrderService deliveryOrderService;
	@Autowired
	BillOfLaddingService billOfLaddingService;
	
	@Override
	@Transactional
	public FileLog saveFileLog(BOL bol) {
		log.info("saveFileLog Start !!!!!!");
		FileLog fileLog = createFileLogDomain(bol);
		fileLog = fileLogrepository.save(fileLog);
		log.info("saveFileLog End !!!!!!");
		return fileLog;
	}

	@Override
	@Transactional
	public String fileProcessing(BOL bol, Invoice invoice) {
		log.info("fileProcessing Start !!!!!!");
		Bol bolDomain = null;String bolNumber=null;
		if (StringUtils.isNotEmpty(bol.getBolNumber()))
			bolNumber = bol.getBolNumber().toUpperCase();
		bolDomain = (Bol) bolRepository.findBolObjByBolnumber(bolNumber);
		if (null == bolDomain) {
			bolDomain = new Bol();
		} else {
			if (!(StringUtils.equalsIgnoreCase(bolDomain.getStatus(), Constants.BOL_STATUS.NEW.toString()))) {
				return "BoL is already initiated for DO";
			}
		}

		createBolDomain(bolDomain, bol, invoice);
		String error = null;
		try{
			bolrepository.save(bolDomain);
			/*todo
			 * get email id from alert and notification table for that bol 
			 * if email id exist send a mail.else dont send
			 */
			
			//this.sendMail(bol.getBolNumber(), bol.getShippingAgentCode(),"UPLOAD_BOL");
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}

		log.info("fileProcessing End !!!!!!");
		return error;
	}

	public void sendEmail(BOL bol) {
		String bolNo=null;
		if(StringUtils.isNotEmpty(bol.getBolNumber())){
			bolNo=bol.getBolNumber().toUpperCase();
		}
		String notifiyEmail=null;
		List<String> subIds = new ArrayList<>();
		subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);
		subIds.add(Constants.AGENT_TYPE.SHIPPING_AGENT.value);
	//	String notifiyEmail = alertNotificationRepository.findEmailIdByBolId(bolNo);
		List<AlertNotification>alertEmail=alertNotificationRepository.findByBolId(bolNo);
		String emalSA = billOfLaddingService.getShippingAgentEmailId(bol.getShippingAgentCode());
		if(alertEmail!=null) {
			if(alertEmail.size()>0) {
				 notifiyEmail =alertEmail.get(0).getEmailId();
	
					StringTokenizer emailtokenizer = new StringTokenizer(notifiyEmail, ",");
					while (emailtokenizer.hasMoreTokens()) {
						String emailImporter = emailtokenizer.nextToken();
						Map<String, String> mailContentMapImporter = new HashMap<String, String>();
						String head="Invoice Uploaded for BOL "+bol.getBolNumber()+" (Request DO)";
						mailContentMapImporter.put("head", head);
						mailContentMapImporter.put("bol", bol.getBolNumber());
						mailContentMapImporter.put("importerName",bol.getConsigneeName());
						mailContentMapImporter.put("shippingAgentName", bol.getShippingAgentName());
						mailContentMapImporter.put("shippingAgentEmail",emalSA);
						
						String image = getClass().getResource("/static/images/email_header.png").toString();
						mailContentMapImporter.put("imageURL", image);
						String[] listOfCCMail =  ccEmail.split(";");
						emailNotificationService.sendMail(new MailDTO().builder().toAddress(emailImporter).subject(head).ccAddress(listOfCCMail)
								.mailContentMap(mailContentMapImporter).build(), Constants.EMAIL_TYPE.UPLOAD_BOL.value);
					}
				}
		}
	}
	
	@Override
	@Transactional
	public void saveFileLogDetails(BOL bol, Invoice invoice, FileLog fileLog, String error) {
		FileLogDetail fileLogDetail = createFileLogDetailsDomain(bol, invoice, fileLog, error);
		fileLogDetailsRepository.save(fileLogDetail);
	}

	private void createBolDomain(Bol bolDomain, BOL bolDTO, Invoice invoice) {
		log.info("createBolDomain Start !!!!!!");
		// Todo Audit logging for BOL
		BolDetail bolDetail = null;
		bolDetail = createBolDetailDomain(bolDomain, bolDTO, bolDetail);
		if (null != invoice && StringUtils.isNotEmpty(invoice.getInvoiceNumber())) {
			createBOLInvoiceDomain(bolDomain, invoice);
		}
		bolDomain.addBolDetails(bolDetail);
		log.info("createBolDomain End !!!!!!");

	}

	private BolDetail createBolDetailDomain(Bol bolDomain, BOL bolDTO, BolDetail bolDetail) {
		String bolNumber= "";
		
		if (StringUtils.isNotEmpty(bolDTO.getBolNumber()))
			bolNumber = bolDTO.getBolNumber().toUpperCase();
		bolDomain.setBolNumber(bolNumber);
		//if (StringUtils.isNotEmpty(bolDTO.getBolType()))
		//	bolDomain.setBolType(bolDTO.getBolType());
	
			if (StringUtils.isNotEmpty(bolDTO.getBolType()))
			bolDomain.setBolType(bolDTO.getBolType());
			else {
			throw new BusinessException("BOL TYPE: Invalid");
			}
		
		bolDomain.setStatus(Constants.BOL_STATUS.NEW.value);
		bolDomain.setBolGroupId(null);
		bolDomain.setIsActive(Constants.VALID_VAL);
		bolDomain.setCreatedDate(new Date());

		if (bolDomain.getBolDetails().isEmpty()) {
			bolDetail = new BolDetail();
			if (StringUtils.isNotEmpty(bolDTO.getShippingAgentCode())) {
				bolDetail.setCreatedBy(bolDTO.getShippingAgentCode());
				bolDomain.setCreatedBy(bolDTO.getShippingAgentCode());
			} else {
				bolDetail.setCreatedBy(Constants.SYSTEM);
				bolDomain.setCreatedBy(Constants.SYSTEM);
			}
			bolDetail.setCreatedDate(new Date());
		} else {
			bolDetail = bolDomain.getBolDetails().iterator().next();
			if (StringUtils.isNotEmpty(bolDTO.getShippingAgentCode())) {
				bolDetail.setModifiedBy(bolDTO.getShippingAgentCode());
				bolDomain.setModifiedBy(bolDTO.getShippingAgentCode());
			} else {
				bolDetail.setModifiedBy(Constants.SYSTEM);
				bolDomain.setModifiedBy(Constants.SYSTEM);
			}
			bolDetail.setModifiedDate(new Date());
		}
		if (StringUtils.isNotEmpty(bolDTO.getVesselName()))
			bolDetail.setVesselName(bolDTO.getVesselName());
		if (StringUtils.isNotEmpty(bolDTO.getVesselATA())) {
			Date vesselATA = DateUtil.parseDate(bolDTO.getVesselATA(), DateUtil.DATE_FORMAT, Constants.VESSEL_ATA);
			bolDetail.setVesselAta(vesselATA);
		}
		if (StringUtils.isNotEmpty(bolDTO.getVesselETA())) {
			Date vesselETA = DateUtil.parseDate(bolDTO.getVesselETA(), DateUtil.DATE_FORMAT, Constants.VESSEL_ETA);
			bolDetail.setVesselEta(vesselETA);
		}
		if (StringUtils.isNotEmpty(bolDTO.getVoyageNumber()))
			bolDetail.setVoyageNumber(bolDTO.getVoyageNumber());
		if (StringUtils.isNotEmpty(bolDTO.getConsigneeCode()))
			bolDetail.setImporterCode(bolDTO.getConsigneeCode());
		if (StringUtils.isNotEmpty(bolDTO.getShippingAgentName()))
			bolDetail.setShippingAgentName(bolDTO.getShippingAgentName());
		else {
			List<String> subIds = new ArrayList<>();
			subIds.add(Constants.AGENT_TYPE.SHIPPING_AGENT.value);
			subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);
			Set<DTAgentView> agentDetailSet =billOfLaddingService.getAgentDetails(bolDTO.getShippingAgentCode(),subIds);
			if(agentDetailSet!=null) {
				if(agentDetailSet.size()>0) {
					bolDetail.setShippingAgentName(agentDetailSet.stream().map(agentDetail->agentDetail.getAgentName()).findAny().orElse(null));	
				}
			}
		}
		if (StringUtils.isNotEmpty(bolDTO.getShippingAgentCode()))
			bolDetail.setShippingAgentCode(bolDTO.getShippingAgentCode());
		if (StringUtils.isNotEmpty(bolDTO.getContainerCount()))
			bolDetail.setContainerCount(bolDTO.getContainerCount());
		if (StringUtils.isNotEmpty(bolDTO.getConsigneeName()))
			bolDetail.setConsigneeName(bolDTO.getConsigneeName());

		bolDetail.setIsActive(Constants.VALID_VAL);
		/*
		 * if (StringUtils.isNotEmpty(bolDTO.getShippingAgentCode())) {
		 * bolDetail.setCreatedBy(bolDTO.getShippingAgentCode());
		 * bolDomain.setCreatedBy(bolDTO.getShippingAgentCode()); }else{
		 * bolDetail.setCreatedBy(Constants.SYSTEM);
		 * bolDomain.setCreatedBy(Constants.SYSTEM); }
		 */
		return bolDetail;
	}

	private void createBOLInvoiceDomain(Bol bolDomain, Invoice invoice) {

		log.info("createBOLInvoiceDomain Start !!!!!!");
		BolInvoice bolInvoice = null;
		String invoiceNumber=null;
		if(StringUtils.isNotEmpty(invoice.getInvoiceNumber()))
			invoiceNumber=invoice.getInvoiceNumber().toUpperCase();
		List<BolInvoice> bolInvoices = bolInvoiceRepository
				.findAll(new BoLInvoiceSpecification().getFilter(bolDomain.getBolNumber().toUpperCase(), invoiceNumber));
		if (bolInvoices.isEmpty()) {
			bolInvoice = new BolInvoice();
		} else {
			bolInvoice = bolInvoices.get(0);
		}
		bolInvoice.setInvoiceNumber(invoiceNumber);
		if (StringUtils.isNotEmpty(invoice.getInvoiceValue())) {
			try {
				bolInvoice.setInvoiceValue(Double.valueOf(invoice.getInvoiceValue()));
			} catch (NumberFormatException e) {
				throw new BusinessException("Invoice Value: Invalid");
			}
		}
		bolInvoice.setCurrency(invoice.getCurrency());
		if (StringUtils.isNotEmpty(invoice.getInvoiceIssueDate())) {
			Date invoiceIssueDate = DateUtil.parseDate(invoice.getInvoiceIssueDate(), DateUtil.DATE_FORMAT,
					Constants.INVOICE_ISSUE_DATE);
			bolInvoice.setInvoiceDate(invoiceIssueDate);
		}
		if (StringUtils.isNotEmpty(invoice.getInvoiceValidityDate())) {
			Date invoiceExpiryDate = DateUtil.parseDate(invoice.getInvoiceValidityDate(), DateUtil.DATE_FORMAT,
					Constants.INVOICE_EXPIRY_DATE);
			bolInvoice.setInvoiceValidityDate(invoiceExpiryDate);
		}
		bolInvoice.setCreatedBy(bolDomain.getCreatedBy());
		bolInvoice.setCreatedDate(new Date());
		bolInvoice.setIsActive(Constants.VALID_VAL);
		if (StringUtils.isNotEmpty(invoice.getInvoiceType())) {
			for (Constants.INVOICE_TYPE invoice_Type : Constants.INVOICE_TYPE.values()) {
				if (StringUtils.equalsIgnoreCase(invoice_Type.toString(), invoice.getInvoiceType())) {
					String invType = invoice_Type.value;
					InvoiceType invoiceType = invoiceTypeRepository.findByInvoiceTypeNameAndIsActive(invType,
							Constants.ACTIVE_VAL);
					if (null == invoiceType) {
						throw new BusinessException("Invoice Type: " + ErrorCodes.INVALID_INVOICE_TYPE);
					}
					bolInvoice.setInvoiceType(invoiceType);
					break;
				}
			}
			if (null == bolInvoice.getInvoiceType()) {
				throw new BusinessException("Invoice Type: " + ErrorCodes.INVALID_INVOICE_TYPE);
			}
		}
		// path pending pending
		bolDomain.addInvoices(bolInvoice);
		log.info("createBOLInvoiceDomain End !!!!!!");

	}

	private FileLog createFileLogDomain(BOL bol) {
		log.info("createFileLogDomain Start !!!!!!");
		FileLog fileLog = new FileLog();
		String fileRef = Constants.FILE_REF.concat(String.valueOf(fileLogrepository.getNextFileRefNo()));
		fileLog.setReferenceNumber(fileRef);
		fileLog.setFilePath(bol.getProcessedFilePath());
		fileLog.setUploadType(bol.getUploadType());
		fileLog.setShippingAgentCode(bol.getShippingAgentCode());
		fileLog.setIsActive(Constants.VALID_VAL);
		if (StringUtils.isNotEmpty(bol.getShippingAgentCode())) {
			fileLog.setCreatedBy(bol.getShippingAgentCode());
		} else {
			fileLog.setCreatedBy(Constants.SYSTEM);
		}
		log.info("createFileLogDomain End !!!!!!");
		return fileLog;
	}

	private FileLogDetail createFileLogDetailsDomain(BOL bol, Invoice invoice, FileLog fileLog, String error) {
		log.info("createFileLogDetailsDomain Start !!!!!!");
		FileLogDetail fileLogDetail = new FileLogDetail();
		if (StringUtils.isEmpty(error)) {
			fileLogDetail.setIsProcessed(Constants.YES);
		} else {
			fileLogDetail.setIsProcessed(Constants.NO);
			fileLogDetail.setErrorCode(error);
		}
		fileLogDetail.setIsActive(Constants.VALID_VAL);
		if (StringUtils.isNotEmpty(bol.getBolNumber()))
			fileLogDetail.setBolNumber(bol.getBolNumber().toUpperCase());
		if (null != invoice)
			fileLogDetail.setInvoiceNumber(invoice.getInvoiceNumber().toUpperCase());
		if (StringUtils.isNotEmpty(bol.getShippingAgentCode())) {
			fileLogDetail.setCreatedBy(bol.getShippingAgentCode());
		} else {
			fileLogDetail.setCreatedBy(Constants.SYSTEM);
		}

		fileLogDetail.setFileLog(fileLog);
		fileLog.addFileLogDetails(fileLogDetail);
		log.info("createFileLogDetailsDomain Start !!!!!!");
		return fileLogDetail;

	}

	private String validateBoL(BOL bol) {

		bol.getBolNumber();
		return null;

	}

	@Override
	@Transactional
	public String invoicePathUpdate(InvoiceUploadDTO invoiceUploadDTO) {
		String invoiceNumber=null,bolNumber=null;
		if(StringUtils.isNotEmpty(invoiceUploadDTO.getInvoiceNumber())){
			invoiceNumber=invoiceUploadDTO.getInvoiceNumber().toUpperCase();
		}
		if(StringUtils.isNotEmpty(invoiceUploadDTO.getBolNumber())){
			bolNumber=invoiceUploadDTO.getBolNumber().toUpperCase();
		}

		List<BolInvoice> bolInvoices = bolInvoiceRepository.findAll(new BoLInvoiceSpecification()
				.getInvoiceDetails(invoiceUploadDTO.getShippingAgentCode(),invoiceNumber , bolNumber));
		if (bolInvoices.isEmpty()) {
			return "Invoice not available";
		} else {
			BolInvoice bolInvoice = bolInvoices.get(0);
			bolInvoice.setPath(invoiceUploadDTO.getPath());
			bolInvoice.setModifiedBy("SYSTEM");
			bolInvoice.setModifiedDate(new Date());
			bolInvoiceRepository.save(bolInvoice);
			Bol bol = bolRepository.findBolByBolNumber(bolInvoice.getBol().getBolNumber());
			
			this.sendMailForInvoiceUpload(bol);
			return null;
		}

	}

	private void sendMailForInvoiceUpload(Bol bol) {
		BOL bolDto = new BOL();
		List<Invoice> invoiceList = new ArrayList<>();
		bolDto.setBolNumber(bol.getBolNumber());
		bolDto.setBolType(bol.getBolType());

		bolDto.setShippingAgentCode(bol.getBolDetails().iterator().next().getShippingAgentCode());
		bolDto.setShippingAgentName(bol.getBolDetails().iterator().next().getShippingAgentName());
		bolDto.setShippingAgentNumer(null);
		bolDto.setVesselName(bol.getBolDetails().iterator().next().getVesselName());
		bolDto.setVoyageNumber(bol.getBolDetails().iterator().next().getVoyageNumber());
		bolDto.setConsigneeName(bol.getBolDetails().iterator().next().getConsigneeName());
		bolDto.setContainerCount(bol.getBolDetails().iterator().next().getContainerCount());
		this.sendEmail(bolDto);
		
	}

	@Override
	public void saveBLDetails(BOL bol, List<BolInvoiceDTO> invoiceDTOList) {
		Bol bolDomain = (Bol) bolRepository.findBolByBolNumber(bol.getBolNumber());
		bolDomain.setModifiedDate(new Date());
		BolDetail bolDetail = null;
		createBolDetailDomain(bolDomain, bol, bolDetail);
		createBOLInvoice(bolDomain, invoiceDTOList);
		try {
			bolrepository.save(bolDomain);
			this.sendMail(bol.getBolNumber(),bol.getShippingAgentCode(),"UPLOAD_BOL",bol.getConsigneeName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void createBOLInvoice(Bol bolDomain, List<BolInvoiceDTO> invoiceDTOList) {
		log.info("createBOLInvoice Start !!!!!!");
		bolDomain.clearBolInvoices();
		for (BolInvoiceDTO bolInvoiceDTO : invoiceDTOList) {
			BolInvoice bolInvoice = null;
			if (null != bolInvoiceDTO.getBolInvoiceId()) {
				bolInvoice = bolInvoiceRepository.findByBolInvoiceId(bolInvoiceDTO.getBolInvoiceId());
				bolInvoice.setModifiedBy(bolDomain.getCreatedBy());
				bolInvoice.setModifiedDate(new Date());
			} else {
				bolInvoice = new BolInvoice();
				bolInvoice.setCreatedBy(bolDomain.getCreatedBy());
				bolInvoice.setCreatedDate(new Date());

			}
			if(StringUtils.isNotEmpty(bolInvoiceDTO.getInvoiceNumber()))
				bolInvoice.setInvoiceNumber(bolInvoiceDTO.getInvoiceNumber().toUpperCase());
			bolInvoice.setInvoiceValue(bolInvoiceDTO.getInvoiceValue());
			bolInvoice.setCurrency(bolInvoiceDTO.getCurrency());
			if (StringUtils.isNotEmpty(bolInvoiceDTO.getInvoiceIssueDate())) {
				Date invoiceIssueDate = DateUtil.parseDate(bolInvoiceDTO.getInvoiceIssueDate(), DateUtil.DATE_FORMAT,
						Constants.INVOICE_ISSUE_DATE);
				bolInvoice.setInvoiceDate(invoiceIssueDate);
			}
			if (StringUtils.isNotEmpty(bolInvoiceDTO.getInvoiceDueDate())) {
				Date invoiceExpiryDate = DateUtil.parseDate(bolInvoiceDTO.getInvoiceDueDate(), DateUtil.DATE_FORMAT,
						Constants.INVOICE_EXPIRY_DATE);
				bolInvoice.setInvoiceValidityDate(invoiceExpiryDate);
			}

			if (null != bolInvoiceDTO.getInvoiceDate()) {
				bolInvoice.setInvoiceDate(bolInvoiceDTO.getInvoiceDate());
			}
			if (null != bolInvoiceDTO.getInvoiceValidityDate()) {
				bolInvoice.setInvoiceValidityDate(bolInvoiceDTO.getInvoiceValidityDate());
			}

			bolInvoice.setIsActive(Constants.VALID_VAL);
			if (null != bolInvoiceDTO.getInvoiceType()) {
				InvoiceType invoiceType = invoiceTypeRepository.findByInvoiceTypeIdAndIsActive(
						bolInvoiceDTO.getInvoiceType().getInvoiceTypeId(), Constants.ACTIVE_VAL);
				bolInvoice.setInvoiceType(invoiceType);
			}
			// path pending
			if(null != bolInvoiceDTO.getPath()) {
				bolInvoice.setPath(bolInvoiceDTO.getPath());
			}
			if (null != bolInvoiceDTO.getBase64Invoice()) {
				try {
					if (null != bolDomain.getBolDetails()) {
						String agentCode = bolDomain.getBolDetails().stream()
								.filter(boldetails -> boldetails.getShippingAgentCode() != null)
								.map(boldetails -> boldetails.getShippingAgentCode()).findAny().orElse(null);
						String docContent = bolInvoiceDTO.getBase64Invoice();
						if (StringUtils.isNotEmpty(docContent)) {
							if (docContent.contains("base64,")) {
								String invoicePath = processInvoice(bolInvoiceDTO.getBase64Invoice(), agentCode,
										bolInvoiceDTO.getFileName());
								bolInvoice.setPath(invoicePath);
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bolInvoice.setBol(bolDomain);
			bolDomain.addInvoices(bolInvoice);

		}

	}
	@Override
	public String processInvoice(String uploadDoc, String agentCode, String fileName) throws IOException {
		String result = null;
		if(uploadDoc!=null) {
		String[] parts = uploadDoc.split("base64,");
		if (!uploadDoc.isEmpty()) {
			if (parts.length > 0) {
				uploadDoc = parts[1];
			}
			byte[] bs = Utilities.decodeBase64(uploadDoc);
			String fileExtn = Utilities.getFileExtn(bs);
			fileName = "Invoice_" + System.currentTimeMillis() + "." + fileExtn;
			String pdfInvoicepath = Utilities.saveInvoicePDFtoPath(uploadDoc, fileName, invoicefilepath, agentCode,fileExtn,null,"fromBolUpload");
			result = pdfInvoicepath;

		}
		
		}
		return result;
	}

	private void sendMail(String id, String agentCode, String type, String impName) {
		List<String> subIds = new ArrayList<>();
		subIds.add(Constants.AGENT_TYPE.FRIEGHT_FORWARDER.value);
		subIds.add(Constants.AGENT_TYPE.SHIPPING_AGENT.value);

		String[] listOfCCEmail = null;
		//String email = deliveryOrderService.getAdminEmailByAgentCode(agentCode, subIds);
		String email = billOfLaddingService.getShippingAgentEmailId(agentCode);
		// String email = "Joseph.vibik@dubaitrade.ae";
		Map<String, String> mailContentMap = new HashMap<String, String>();
		mailContentMap.put("content", type);
		String image = getClass().getResource("/images/email_header.png").toString();
		mailContentMap.put("imageURL", image);
		String head = " Bill of Ladding with No:" + id + " created.";
		mailContentMap.put("head", head);
		mailContentMap.put("bol", id);
		String agentName=deliveryOrderService.getAgentNameByAgentCode(agentCode, subIds);
		String agentNameEmail=billOfLaddingService.getShippingAgentEmailId(agentCode);
		mailContentMap.put("shippingAgentName", agentName); 
		mailContentMap.put("shippingAgentEmail", agentNameEmail); 
		mailContentMap.put("importerName", impName); 
		listOfCCEmail = ccEmail.split(";");
		emailNotificationService.sendMail(new MailDTO().builder().toAddress(email).ccAddress(listOfCCEmail)
				.subject(head).mailContentMap(mailContentMap).build(), type);

	}

	@Override
	public FileLog saveErrorMessageToFileLog(FileProcessDTO fileProcessDTO) {
		
		String fileRef = Constants.FILE_REF.concat(String.valueOf(fileLogrepository.getNextFileRefNo()));
		
		//SAVE - FILE LOG
		FileLog fileLog = new FileLog();
		fileLog.setFileName(fileProcessDTO.getFileName());
		fileLog.setFilePath(fileProcessDTO.getFileLocation());
		fileLog.setReferenceNumber(fileRef);
		fileLog.setShippingAgentCode(fileProcessDTO.getAgentCode());
		fileLog.setUploadType(fileProcessDTO.getUploadType());
		fileLog.setStatus(fileProcessDTO.getStatus());
		fileLog.setIsActive(Constants.VALID_VAL);
		fileLog.setVersion(0);
		if (StringUtils.isNotEmpty(fileProcessDTO.getAgentCode())) {
			fileLog.setCreatedBy(fileProcessDTO.getAgentCode());
		} else {
			fileLog.setCreatedBy(Constants.SYSTEM);
		}	

		//SAVE - FILE LOG DETAILS
		FileLogDetail fileLogDetail = new FileLogDetail();
		fileLogDetail.setIsProcessed(Constants.NO);
		fileLogDetail.setErrorCode(fileProcessDTO.getError());
		fileLogDetail.setIsActive(Constants.VALID_VAL);
		fileLogDetail.setFileLog(fileLog);
		if (StringUtils.isNotEmpty(fileProcessDTO.getAgentCode())) {
			fileLogDetail.setCreatedBy(fileProcessDTO.getAgentCode());
		} else {
			fileLogDetail.setCreatedBy(Constants.SYSTEM);
		}
		
		fileLog.addFileLogDetails(fileLogDetail);
		fileLogrepository.save(fileLog);
		return fileLog;
	}


}
