package ae.dt.deliveryorder.dto;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BoLDTO {

	private String bolNumber;
	
	private String encBolNumber;

	private String bolType;
	
	private Set<BolChannelDTO> bolChannels;
	
	private Set<BoLDetailDTO> bolDetails;
	
	private Set<BolInvoiceDTO> bolInvoices;

	private Set<DoAuthRequestDTO> doAuthRequests;

	private String createdBy;

	private String status;
@JsonIgnore
	private Long bolId;
	
	private Long bolGroupId;

	private Date createdDate;
	private Long isValid;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;
	private Long version;
	
	private int size =10;
    private int totalPages;
    private long totalElements;
    private int pageNumber;

	private String bolIdStr;
	
	// private Set<DoAuthRequest> doAuthRequests;
    public void addInvoices(BolInvoiceDTO bolInvoice){
		bolInvoice.setBol(this);
		bolInvoices.add(bolInvoice);
	}

	public void addBolDetails(BoLDetailDTO bolDetail){
		bolDetail.setBol(this);
		bolDetails.add(bolDetail);
	}
	

	
}
