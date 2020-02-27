package ae.dt.common.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DTAgentViewDTO implements Serializable {

	private Long gcId;

	private String companyName;

	private String businessUnitId;

	private String businessUnitName;

	private String businessGroupId;

	private String agentCode;

	private String agentName;
	
	private String businessGroupDesc;
	
	private String businessSubId;
}
