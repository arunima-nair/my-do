package ae.dt.deliveryorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalLogDTO {
	@JsonIgnore
	private Long approvalLogId;

	private String status;
	
	private RejectionRemarksDTO rejectionRemark;
	
	private ReturnRemarksDTO returnRemark;
	
	private DoAuthRequestDTO doAuthRequest;

	private String comments;
	
	private String createdBy;
	
	private Date createdDate;
	
}
