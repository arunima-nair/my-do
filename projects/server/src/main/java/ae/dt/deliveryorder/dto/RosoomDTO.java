package ae.dt.deliveryorder.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RosoomDTO {
	private Long rosoomId;

	private Date fiDate;

	private String fiInstrument;

	private String fiTransactionId;

	private String gatewayTransactionId;

	private BigDecimal paymentAmount;

	private String remarks;
	@JsonIgnore
	private CollectionDTO collection;

}
