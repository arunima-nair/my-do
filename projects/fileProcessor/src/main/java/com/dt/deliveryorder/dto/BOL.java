package com.dt.deliveryorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BOL extends  BaseDTO{
    private String bolNumber;
    private String bolType;
    private String vesselName;
    private String vesselATA;
    private String vesselETA;
    private String voyageNumber;
    private String customerImporterCode;
    private String consigneeCode;
    private String consigneeName;
    private String freeDays;
    private String shippingAgentName;
    private String shippingAgentCode;
    private String processedFilePath;
    private String containerCount;
    private String uploadType;

    private List<Invoice> invoices;

    public void addInvoice(Invoice invoice) {
        if (this.getInvoices() == null)
            this.setInvoices(new ArrayList());

        this.getInvoices().add(invoice);
    }

    public void addInvoiceList(List<Invoice> invoiceList) {
        if (this.getInvoices() == null)
            this.setInvoices(new ArrayList());

        this.getInvoices().addAll(invoiceList);
    }
}
