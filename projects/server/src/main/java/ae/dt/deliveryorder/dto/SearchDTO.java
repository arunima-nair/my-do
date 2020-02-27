package ae.dt.deliveryorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
	private String bolNumber;
	private String bolInvoiceNumber;
	private String shippingLineName;
	private String doRefNo;
	private String status;
	private String userName;
	private String frmDate;
	private String toDate;
	private String doParty;
	private String reqParty;
	private String reqPartyEmail;
	private String searchDefault;
	private String impCode;	
	private String invoiceNumber;
	private String shippingAgentCode;
	private String refNo;
	private String invoiceStatus;
	private String reqType;
	
}
