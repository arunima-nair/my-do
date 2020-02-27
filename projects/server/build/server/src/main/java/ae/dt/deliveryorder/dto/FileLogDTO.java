package ae.dt.deliveryorder.dto;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * Created by Joseph Vibik on 06/23/2019.
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileLogDTO {
@JsonIgnore
	private Long fileLogId;
	private String fileLogIdEncr;
	private String referenceNumber;
	private String fileName;
	private String filePath;
	private String uploadType;
	private String Status;
	private String shippingAgentCode;
	private Set<FileLogDetailsDTO> fileLogDetails;
	
	private String encId;
	private Long totalCount;
	private Long passedCount;
	private Long failedCount;

	private long version;
	private String createdBy;
	private Date createdDate;
	private Long isValid = 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private long isActive;
	private String convertedDate;

}
