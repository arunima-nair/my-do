package ae.dt.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenDTO implements Serializable {

    private String token;
    private String validtity;
    private String agentType;
    private String agentCode;
    private String userType;

}
