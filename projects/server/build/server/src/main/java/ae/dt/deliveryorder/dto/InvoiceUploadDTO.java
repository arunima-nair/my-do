package ae.dt.deliveryorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceUploadDTO {
	private String bolNumber;
    private String invoiceNumber;
    private String path;
    private String shippingAgentCode;
}
