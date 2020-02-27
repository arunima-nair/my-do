package com.dt.deliveryorder.dto;

import lombok.Data;

import java.util.Map;

@Data
public class BolConfDTO {
    private String type;
    private String bolnumber;
    private String bolType;
    private String invoiceNumber;
    private String invoiceValue;
    private Map<String,String> invoiceIssueDate;
    private String invoiceType;
    private String vesselName;
    private Map<String,String> vesselETA;
    private String voyageNumber;
    private String importerCode;
    private String consigneeCode;
    private String shippingAgentName;
    private String shippingAgentCode;
    private String secureKey;
    private String consigneeName;
    private Map<String,String> bolTypeMapper;
    private Map<String,String> pdfFileNamingStructure;
}