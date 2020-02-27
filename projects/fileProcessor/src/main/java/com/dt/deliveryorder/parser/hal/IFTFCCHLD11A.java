package com.dt.deliveryorder.parser.hal;

import com.dt.deliveryorder.dto.BOL;
import com.dt.deliveryorder.dto.Invoice;
import com.dt.deliveryorder.parser.EdiFileParser;
import com.dt.deliveryorder.util.EdiUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang.StringUtils.startsWithIgnoreCase;

public class IFTFCCHLD11A extends EdiFileParser {
    private Map<String, BOL> bolListMap = null;
    private BOL bol = null;
    private Invoice invoice = null;
    private File file = null;

    public IFTFCCHLD11A(File file) {
        this.file = file;
    }

    @Override
    protected void segmentCallback(String segment) {


        if (startsWithIgnoreCase(segment.trim(), EdiConstants.UNT.toString())) {

            if (StringUtils.isEmpty(invoice.getInvoiceType())) {
                invoice.setInvoiceType("OTHER");
            }
            bol.addInvoice(invoice);
            if (bolListMap.get(bol.getBolNumber()) == null)
                bolListMap.put(bol.getBolNumber(), bol);
            else
                bolListMap.get(bol.getBolNumber()).addInvoiceList(bol.getInvoices());

            bol = new BOL();
            invoice = new Invoice();
        }

        else if (startsWithIgnoreCase(segment.trim(), EdiConstants.BGM.toString())) {
            invoice.setInvoiceNumber(getSegmentValue(segment, 2));
        }
        else if (startsWithIgnoreCase(segment.trim(), EdiConstants.MOA.toString()) &&
                startsWithIgnoreCase(getSegmentValue(segment, 1,0), "388")   ) {
            invoice.setInvoiceValue(getSegmentValue(segment, 1,1));
            invoice.setCurrency(getSegmentValue(segment, 1,2));
        }
        else if (startsWithIgnoreCase(segment, EdiConstants.RFF.toString()) &&
                StringUtils.equalsIgnoreCase(super.getSegmentValue(segment.trim(), 1, 0),
                        EdiConstants.BM.toString())) {
            bol.setBolNumber(getSegmentValue(segment, 1, 1));
        } else if (startsWithIgnoreCase(segment, EdiConstants.DTM.toString()) &&
                StringUtils.equalsIgnoreCase(super.getSegmentValue(segment.trim(), 1, 0), "3")) {
            invoice.setInvoiceIssueDate( EdiUtils.getDate(super.getSegmentValue(segment.trim(), 1, 1),
                    super.getSegmentValue(segment.trim(), 1, 2)));
        } else if (startsWithIgnoreCase(segment, EdiConstants.DTM.toString()) &&
                StringUtils.equalsIgnoreCase(super.getSegmentValue(segment.trim(), 1, 0), "7")) {
            invoice.setInvoiceDueDate(EdiUtils.getDate(super.getSegmentValue(segment.trim(), 1, 1)
                                      ,super.getSegmentValue(segment.trim(), 1, 2)));
            invoice.setInvoiceValidityDate(EdiUtils.getDate(super.getSegmentValue(segment.trim(), 1, 1)
                    ,super.getSegmentValue(segment.trim(), 1, 2)));
        } else if (startsWithIgnoreCase(segment,EdiConstants.TCC.toString()) &&
                   StringUtils.equalsIgnoreCase(super.getSegmentValue(segment.trim(),1,3),"SEAFREIGHT") ) {
            invoice.setInvoiceType("FREIGHT_CHARGES");
        }


    }

    @Override
    public Object parse() {

        try {
            bolListMap = new HashMap<>();
            bol = new BOL();
            invoice = new Invoice();
            Map segMentMap = Stream.of(EdiConstants.UNH.toString(), EdiConstants.BGM.toString(),
                    EdiConstants.RFF.toString(), EdiConstants.DTM.toString(),
                    EdiConstants.MOA.toString(), EdiConstants.UNT.toString())
                    .collect(Collectors.toMap(s1 -> s1.toString(), s1 -> s1));
            super.scanFile(file, segMentMap);
            return bolListMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
