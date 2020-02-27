package ae.dt.deliveryorder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ae.dt.common.dto.PaginationDTO;
import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.dto.SearchDTO;
import net.sf.jasperreports.engine.JRException;


public interface FileLogService{
	public Page<FileLog> getFileLogs(SearchDTO searchDto, Pageable pageable,String sortOrder,String sortcol, UserDTO dto);
	public List<FileLogDetail> getByDetailsById(String fileLogId) throws JRException;
	public Object[] getCountDetails(Long fileLogId );
	public String getFileLogDocumentByPath(String fileLogId);
}
