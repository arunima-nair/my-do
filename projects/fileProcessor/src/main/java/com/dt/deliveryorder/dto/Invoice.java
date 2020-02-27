package com.dt.deliveryorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice extends BaseDTO {
    private String invoiceNumber;
    private String invoiceValue;
    private String invoiceIssueDate;
    private String invoiceValidityDate;
    private String invoiceType;
    private String invoiceDueDate;
    private String currency;
    private String base64Invoice;
    private String payeeCode;
}
