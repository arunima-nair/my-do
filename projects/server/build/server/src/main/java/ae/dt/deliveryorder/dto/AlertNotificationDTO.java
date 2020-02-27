package ae.dt.deliveryorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertNotificationDTO {
	private Long alertId;

	private String status;

	private String bolId;

	private String emailId;
}
