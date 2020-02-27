package ae.dt.deliveryorder.facade;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.dto.FileLogDTO;
import net.sf.jasperreports.engine.JRException;

public interface FileLogFacade {

		public PaginationDTO<FileLogDTO> getFileLogsDetails(String referenceNumber, String bolNo,
			String invoiceNumber, String shippingAgentName, String status,
			String fromDate, String toDate, Pageable pageable,String sortOrder,String sortcol, UserDTO userDTO);

		public byte[] generateFileLogReport(String fileLogId, String shippingAgentName, String referenceNumber, String uploadType, String fromDate, UserDTO userDTO, HttpServletRequest request) throws JRException;

		public String getFileLogDocumentByPath(String fileLogId);

	
}
