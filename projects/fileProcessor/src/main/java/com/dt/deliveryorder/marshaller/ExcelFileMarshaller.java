package com.dt.deliveryorder.marshaller;

import com.dt.deliveryorder.dto.BOL;
import com.dt.deliveryorder.dto.Invoice;
import com.dt.deliveryorder.util.ExcelFileWrapper;
import com.dt.deliveryorder.util.FileMappingUtil;
import com.dt.deliveryorder.util.Utils;
import org.apache.camel.Exchange;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dt.deliveryorder.util.Utils.convertObjToJsonString;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Component
public class ExcelFileMarshaller extends BaseMarshaller{

    @Autowired
    FileMappingUtil fileMappingUtil;

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {

    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        ExcelFileWrapper excelFileWrapper = new ExcelFileWrapper(getFileFromExchange(exchange),
                                                                    getFileType(exchange));
        Map<String,String> lineConfMap =getPartnerConfiguration(getAgentCode(exchange));
        Map<String, BOL> bolMap = fetchBolAndInvoice(lineConfMap, exchange, excelFileWrapper);
        List<BOL> bolList =  bolMap.values().stream().collect(Collectors.toList());
        String jsonInString = convertObjToJsonString(bolList);
//        updateFileNameInHeader(exchange);
        return jsonInString;
    }






    private Map<String,String> getPartnerConfiguration(String code){
        return  fileMappingUtil.getLineMap(code);
    }

    private Integer getItemPosition(Map lineConfMap, String key){
        return lineConfMap.get(key) == null ? -1 : Integer.valueOf((String)lineConfMap.get(key));
    }



    private Map<String, BOL> fetchBolAndInvoice(Map lineConfMap,Exchange exchange,
                                                ExcelFileWrapper excelFileWrapper) {

        Map<String, BOL> bolMap = new HashMap<String, BOL>();
        BOL bol = null;
        List<Invoice> invoiceList = null;
        String bolNumber = null;
        Integer pos=0;
        String dateFormat="";
        String excelFormat="";
        for(int row=1;row<excelFileWrapper.getTotalRows(); row++) {
            if (StringUtils.isEmpty(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"bolnumber"), ExcelFileWrapper.ExcelTypeConstants.STRING)))
                 continue;

            bol = new BOL();
            bol.setProcessedFilePath(super.getProcessedFilePath(exchange));
            bolNumber = excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"bolnumber"), ExcelFileWrapper.ExcelTypeConstants.STRING);
            bol.setBolNumber(bolNumber);
            bol.setUploadType(super.getUploadType(exchange));
            bol.setShippingAgentCode(super.getShippingAgentCode(exchange));

            bol.setBolType(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"bolType"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            if (lineConfMap.get("bolTypeMapper") != null) {
                bol.setBolType((String) ((Map)lineConfMap.get("bolTypeMapper")).get(bol.getBolType()));
            }

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"invoiceNumber"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            invoice.setInvoiceValue(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"invoiceValue"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            if (lineConfMap.get("invoiceIssueDate") != null){
                pos = parseInt((String)((Map)lineConfMap.get("invoiceIssueDate")).get("pos"));
                dateFormat = (String)((Map)lineConfMap.get("invoiceIssueDate")).get("format");
                invoice.setInvoiceIssueDate(Utils.getFormattedDate(excelFileWrapper.getValueFromCell(row, pos, ExcelFileWrapper.ExcelTypeConstants.STRING),dateFormat ));
            }

            if (lineConfMap.get("vesselETA") != null){
                pos = parseInt((String)((Map)lineConfMap.get("vesselETA")).get("pos"));
                dateFormat = (String)((Map)lineConfMap.get("vesselETA")).get("format");
                excelFormat = (String)((Map)lineConfMap.get("vesselETA")).get("excelFormat");
                if ("date".equalsIgnoreCase(excelFormat))
                     bol.setVesselETA(Utils.getFormattedDate(excelFileWrapper.getValueFromCell(row, pos, ExcelFileWrapper.ExcelTypeConstants.DATE),dateFormat ));
                else
                    bol.setVesselETA(Utils.getFormattedDate(excelFileWrapper.getValueFromCell(row, pos, ExcelFileWrapper.ExcelTypeConstants.STRING),dateFormat ));
            }



            if (isEmpty(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"currency"), ExcelFileWrapper.ExcelTypeConstants.STRING)))
                invoice.setCurrency("AED");
            else
               invoice.setCurrency(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"currency"), ExcelFileWrapper.ExcelTypeConstants.STRING));

            if (isEmpty(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"invoiceType"), ExcelFileWrapper.ExcelTypeConstants.STRING)))
                invoice.setInvoiceType("OTHER");
            else
                excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"invoiceType"), ExcelFileWrapper.ExcelTypeConstants.STRING);

            bol.setVesselName(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"vesselName"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            bol.setVoyageNumber(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"voyageNumber"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            bol.setCustomerImporterCode(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"importerCode"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            bol.setConsigneeCode(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"consigneeCode"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            bol.setConsigneeName(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"consigneeName"), ExcelFileWrapper.ExcelTypeConstants.STRING));
            bol.setShippingAgentName(excelFileWrapper.getValueFromCell(row, getItemPosition(lineConfMap,"shippingAgentName"), ExcelFileWrapper.ExcelTypeConstants.STRING));

            if (bolMap.get(bolNumber) != null){
                bolMap.get(bolNumber).getInvoices().add(invoice);
            } else {
                bol.addInvoice(invoice);
                bolMap.put(bolNumber, bol);
            }
        }
        return bolMap;
    }
}
