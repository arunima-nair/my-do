package ae.dt.deliveryorder.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.dto.FileLogDetailsDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import ae.dt.deliveryorder.mapper.FileLogDetailMapper;
import ae.dt.deliveryorder.mapper.FileLogMapper;
import ae.dt.deliveryorder.repository.FileLogDetailsRepository;
import ae.dt.deliveryorder.repository.FileLogRepository;
import ae.dt.deliveryorder.specification.FileLogSpecification;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service("fileLogService")
public class FileLogServiceImpl implements FileLogService {

	@Autowired
	FileLogRepository fileLogRepository;
	
	@Autowired
	FileLogDetailsRepository fileLogDetailsRepository;
	
	@Autowired
	FileLogDetailMapper fileLogDetailMapper;

	@Override
	@Transactional(readOnly = true)
	public Page<FileLog> getFileLogs(SearchDTO searchDTO, Pageable pageable,String sortOrder,String sortcol, UserDTO userDTO) {
		return fileLogRepository.findAll(new FileLogSpecification().getFilterData(searchDTO,sortOrder,sortcol, userDTO), pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FileLogDetail> getByDetailsById(String fileLogId) throws JRException {
		return fileLogDetailsRepository.findByFileLogFileLogId(Long.valueOf(fileLogId));
	}


	@Override
	@Transactional(readOnly = true)
	public String getFileLogDocumentByPath(String fileLogId) {
		FileLog fileLog = fileLogRepository.findByFileLogId(Long.valueOf(fileLogId));
		if(fileLog != null) {
			return fileLog.getFilePath();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Object[] getCountDetails(Long fileLogId) {
		return fileLogRepository.getCountDetails(fileLogId);

	}
}
