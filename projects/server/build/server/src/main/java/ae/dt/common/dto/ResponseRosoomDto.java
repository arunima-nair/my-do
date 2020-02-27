package ae.dt.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kamala.Devi on 4/10/2019.
 */
@Data
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseRosoomDto implements Serializable{

     String status ;
     DataDto data ;
     String message;
     String code;
     String gatewayUrl;
     String serviceOwnerId;
     String rosoomServiceId;
     String rosoomChannel;
     String rosoomLicenseKey;
     String custRefNo;
     String serviceDesc;
     String responseUrl;
     String totalAmount;
     String soTransactionId;
     String documentCharges;
     String base64signatureString;
     String popup;
     List invoiceNo;
     String buEncryptionMode;




}
