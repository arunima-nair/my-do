package ae.dt.deliveryorder.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolInvoice;
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
public class RequestBolInvoiceDTO {
	private Long requestBolInvoiceId;

	private Long bolId;

	private Long bolInvoiceId;

	private String createdBy;

	private String bolNo;

	private String bolInvoiceNo;
	
	private String createdDateStr;
	
	private Date createdDate;
	
	private String shippingCode;
	
	private String initiatorCode;

	private String shippingName;

	private String shippingType;
}
