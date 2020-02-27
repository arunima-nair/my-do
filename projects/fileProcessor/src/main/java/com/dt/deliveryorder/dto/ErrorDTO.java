package com.dt.deliveryorder.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorDTO implements Serializable {
    private String agentCode;
    private String filePath;
    private String error;
}
