package ae.dt.deliveryorder.controller;

import java.io.IOException;

/**
 * Created by Joseph Vibik on 06/23/2019.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ae.dt.common.controller.BaseController;
import ae.dt.common.controller.TokenController;
import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.dto.FileLogDTO;
import ae.dt.deliveryorder.facade.FileLogFacade;
import net.sf.jasperreports.engine.JRException;

@RestController
public class MonitorFileUploadsController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(TokenController.class);

	@Autowired
	FileLogFacade fileLogFacade;

	@GetMapping("/app/api/secure/getLogsDetail")
	public PaginationDTO<FileLogDTO> searchLogDetails(@RequestParam String referenceNumber, @RequestParam String bolNo,
			@RequestParam String invoiceNumber, @RequestParam String shippingAgentName, @RequestParam String status,
			@RequestParam String fromDate, @RequestParam String toDate, Pageable pageable,
			HttpServletRequest httpServletRequest) {

		logger.info("********************   Started to fetch the File Logs   ********************");
		String pgSize = (String) httpServletRequest.getParameter("pgSize");
		String pgNo = (String) httpServletRequest.getParameter("pgNo");
		String sortOrder = (String) httpServletRequest.getParameter("sort_order");
		String sortcol = (String) httpServletRequest.getParameter("sort_col");
		logger.info("pgSize==" + pgSize + "-------------pgNo==" + pgNo);
		pageable = new PageRequest(Integer.valueOf(pgNo) - 1, Integer.valueOf(pgSize));
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userDTO");
		if (sortcol.equalsIgnoreCase("0"))
			sortcol = "referenceNumber";
		if (sortcol.equalsIgnoreCase("2"))
			sortcol = "createdDate";
		if (status.equalsIgnoreCase("Choose"))
			status = "";
		PaginationDTO<FileLogDTO> fileLogDTO = fileLogFacade.getFileLogsDetails(referenceNumber, bolNo, invoiceNumber,
				shippingAgentName, status, fromDate, toDate, pageable, sortOrder,sortcol, userDTO);
		logger.info("********************   Completed the API to fetch the File Logs  ********************");
		return fileLogDTO;
	}

	@GetMapping("/app/api/file/fileLogReport")
	public ResponseEntity<byte[]> fileLogReport(@RequestParam String shippingAgentName,@RequestParam String fileLogId,@RequestParam String referenceNumber, @RequestParam String uploadType,@RequestParam String fromDate, HttpServletRequest httpServletRequest)
			throws JRException {
		
		logger.info("********************   Started to fetch the File Logs Details based on Log file id = '{}'  ********************",fileLogId);
		UserDTO userDTO = (UserDTO) httpServletRequest.getAttribute("userName");
		byte[] bytes = fileLogFacade.generateFileLogReport(EncryptionUtil.decrypt(fileLogId),shippingAgentName,referenceNumber,uploadType,fromDate, userDTO, httpServletRequest);
		logger.info("********************   Completed the API to fetch the File Logs Details   ********************");
		return ResponseEntity.ok().header("Content-Type", "application/vnd.ms-excel; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"" + "FileLogReport.xls\"").body(bytes);
		
	}

	@GetMapping("/app/api/file/fileDowload/{fileLogId}")
	public String fileDowload(@PathVariable String fileLogId, HttpServletResponse response) throws IOException {
		
		logger.info("********************   Started to downolad the file based on Log file id path = '{}'  ********************",fileLogId);
		String path = null;
		path = fileLogFacade.getFileLogDocumentByPath(EncryptionUtil.decrypt(fileLogId));
		String resp = super.downloadFile(path, response);
		logger.info("********************   Completed the APIfor download the file  ********************");
		return resp;
	}
}
