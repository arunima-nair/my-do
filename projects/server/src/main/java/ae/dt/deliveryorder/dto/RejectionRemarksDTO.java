package ae.dt.deliveryorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kamala.Devi on 2/13/2019.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RejectionRemarksDTO {
	private Long rejectionRemarksId;

	private String remarks;

	//private Set<ApprovalLog> approvalLogs;

}
