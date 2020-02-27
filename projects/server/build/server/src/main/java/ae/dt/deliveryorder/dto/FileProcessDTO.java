package ae.dt.deliveryorder.dto;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileProcessDTO {
	private String agentCode;
	private String status;
	private String fileName;
	private String fileLocation;
	private String uploadType;
	private String bolNo;
	private String invoiceNo;
	private String error;
	
}
