package ae.dt.deliveryorder.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ARUNIMA. NAIR on 2/14/2019.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
	private Long cashId;

	private BigDecimal paymentAmount;

	private String reference;

	private BigDecimal version;
	@JsonIgnore
	private CollectionDTO collection;
	
	private String createdBy;
}
