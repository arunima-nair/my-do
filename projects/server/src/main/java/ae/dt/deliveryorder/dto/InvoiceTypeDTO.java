package ae.dt.deliveryorder.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceTypeDTO {
	
	private Long invoiceTypeId;
	
	private String invoiceTypeName;
	
	private Set<SAInitiatorInvoiceTypeDTO> saInitiatorInvoiceType;
	
	private Set<BolInvoiceDTO> bolInvoice;
	
	private Date createdDate;
	private Long isValid= 1L;
	private String modifiedBy;
	private Date modifiedDate;
	private Long isActive;

}
