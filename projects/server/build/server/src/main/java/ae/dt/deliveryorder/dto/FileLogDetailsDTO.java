package ae.dt.deliveryorder.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileLogDetailsDTO {
@JsonIgnore
	private Long fileLogDetailsId;
	private Long fileLogId;
	private String isProcessed;
	private String errorCode;
	private FileLogDTO fileLog;
	private String bolNumber;
	private String invoiceNumber;
	
	private String isActive;
	private String isValid;
	private String createdBy;
	private Date  createdDate;
	private String modifyBy;
	private Date modifyDate;
	
}
