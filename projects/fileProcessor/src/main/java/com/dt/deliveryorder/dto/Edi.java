package com.dt.deliveryorder.dto;

import lombok.Data;

@Data
public class Edi {
    private String standard;
    private String version;
    private String release;
    private String customerCode;

    public String getEdiText(){
        return customerCode+"-"+standard+"-"+version+"-"+release;
    }

}
