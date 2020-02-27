package ae.dt.deliveryorder.facade;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ae.dt.common.constants.Constants;
import ae.dt.common.dto.PageDto;
import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.DateUtil;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.dto.FileLogDTO;
import ae.dt.deliveryorder.dto.FileLogDetailsDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import ae.dt.deliveryorder.mapper.FileLogDetailMapper;
import ae.dt.deliveryorder.mapper.FileLogMapper;
import ae.dt.deliveryorder.service.FileLogService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


@Service("fileLogFacade")
public class FileLogFacadeImpl implements FileLogFacade {

	@Autowired
	FileLogService fileLogService;
	
	@Autowired
	FileLogMapper fileLogMapper;
	
	@Autowired
	FileLogDetailMapper fileLogDetailMapper;
	
	@Override
	public PaginationDTO<FileLogDTO> getFileLogsDetails(String referenceNumber, String fileNo, String invoiceNumber,
			String shippingAgentName, String status, String fromDate, String toDate, Pageable pageable,String sortOrder,String sortcol, UserDTO userDTO) {
		
		if(StringUtils.isEmpty(fromDate))
			fromDate=null;
		if(StringUtils.isEmpty(toDate))
			toDate=null;
		
		Page<FileLog> pageFileLogs =  fileLogService.getFileLogs(new SearchDTO().builder().refNo(referenceNumber)
				.status(status)
				.frmDate(fromDate)
				.toDate(toDate)
				.bolNumber(fileNo)
				.invoiceNumber(invoiceNumber)
				.shippingAgentCode(shippingAgentName).build(), pageable, sortOrder, sortcol, userDTO);
		
	
		if (pageFileLogs.getContent().size() > 0) {
			List<FileLogDTO> fileLogDtoList = (List<FileLogDTO>) fileLogMapper.fileLogPageEntityToDTO(pageFileLogs);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date=null;
			for (FileLogDTO fileLogDTO : fileLogDtoList) {
				fileLogDTO.setFileLogIdEncr(EncryptionUtil.encrypt(Long.toString(fileLogDTO.getFileLogId())));
				String dateString = format.format(fileLogDTO.getCreatedDate());
				fileLogDTO.setConvertedDate(dateString);
				Object[] details = null;
				details = fileLogService.getCountDetails(Long.valueOf(fileLogDTO.getFileLogId()));
				Object[] stringValues = (Object[])details[0];
				Long passedCount = null;
				Long failedCount = null;
				Long totalCount = null;
				if(fileLogDTO.getStatus()!=null && fileLogDTO.getStatus().equalsIgnoreCase(Constants.PAYMENTLOG_STATUS.FAILED.value)) {
					passedCount = 0L;
					failedCount = 0L;
					totalCount = 0L;
				}
				else {
					passedCount = (Long) stringValues[0];
					failedCount = (Long) stringValues[1];
					totalCount = (Long) stringValues[2];
				}
				fileLogDTO.setPassedCount(passedCount);
				fileLogDTO.setFailedCount(failedCount);
				fileLogDTO.setTotalCount(totalCount);
			}
			PageDto pageDTO = new PageDto();
			pageDTO.setPageNumber(pageable.getPageNumber());
			pageDTO.setTotalElements(pageFileLogs.getTotalElements());
			pageDTO.setSize(pageable.getPageSize());
			pageDTO.setTotalPages(pageFileLogs.getTotalPages());
			
			return new PaginationDTO<FileLogDTO>(fileLogDtoList, pageable.getPageNumber(), pageDTO.getTotalElements(),
					pageable.getPageSize());
		}
		return new PaginationDTO<FileLogDTO>(new ArrayList<FileLogDTO>(),null,null,pageable.getPageSize());
	}




	@Override
	public byte[] generateFileLogReport(String fileLogId, String shippingAgentName, String referenceNumber, String uploadType, String fromDate, UserDTO userDTO, HttpServletRequest request) throws JRException {
		List<FileLogDetail> fileLogDetails = fileLogService.getByDetailsById(fileLogId);
		String image = "";
		if (fileLogDetails != null) {
			final JasperReport report = loadTemplate("fileLogDetailsReport");
			if (report != null) {
				List<FileLogDetailsDTO> detailsList = fileLogDetailMapper.fileLogEntityToDTO(fileLogDetails);
				for (FileLogDetailsDTO fileLogDetail : detailsList) {
					if(fileLogDetail.getIsProcessed().equals("Y"))
						fileLogDetail.setIsProcessed("Success");
					else
						fileLogDetail.setIsProcessed("Failed");
				}
				// Create an empty datasource.
				final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detailsList);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("listDataSource", dataSource);
				parameters.put("shippingAgentName", shippingAgentName);
				parameters.put("referenceNumber", referenceNumber);
				parameters.put("uploadType", uploadType);
				parameters.put("fromDate", fromDate);
				image = getClass().getResource("/images/logo_dt.png").toString();
				parameters.put("logo", image);
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
				JRXlsExporter exporter = new JRXlsExporter();
				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
				exporter.exportReport();
				byte[] reportBytes = xlsReport.toByteArray();
				return reportBytes;
			}
		}
		return null;
	}

	@Override
	public String getFileLogDocumentByPath(String fileLogId) {
		return fileLogService.getFileLogDocumentByPath(fileLogId);
	}
		
	// Load jrxml template
	private JasperReport loadTemplate(String reportName) throws JRException {

		InputStream reportInputStream = null;
		JasperDesign jasperDesign = null;
		reportInputStream = getClass().getResourceAsStream("/templates/fileLogDetailsReport.jrxml");
		if (reportInputStream != null) {
			jasperDesign = JRXmlLoader.load(reportInputStream);
			return JasperCompileManager.compileReport(jasperDesign);
		}
		return null;
	}
	
}
