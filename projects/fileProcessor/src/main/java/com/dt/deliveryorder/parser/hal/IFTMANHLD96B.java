package com.dt.deliveryorder.parser.hal;

import com.dt.deliveryorder.dto.BOL;
import com.dt.deliveryorder.parser.EdiFileParser;
import com.dt.deliveryorder.util.EdiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.startsWithIgnoreCase;

public class IFTMANHLD96B extends EdiFileParser {


    private List<BOL> bolList=null;
    private BOL bol = null;
    private String tdt = null;
    private String loc = null;
    private File file= null;

    public IFTMANHLD96B(File file) {
        this.file = file;
    }



    @Override
    protected void segmentCallback(String segment) {
        if (startsWithIgnoreCase(segment.trim(), EdiConstants.UNT.toString())) {
            bolList.add(bol);
            bol = new BOL();
            tdt = null;
            loc = null;
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.RFF.toString()) &&
                equalsIgnoreCase(super.getSegmentValue(segment.trim(), 1,0),
                        EdiConstants.BM.toString())) {
            bol.setBolNumber(this.getSegmentValue(segment, 1, 1));
            bol.setBolType("OBL");
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.RFF.toString()) &&
                equalsIgnoreCase(super.getSegmentValue(segment.trim(), 1,0),
                        EdiConstants.AEX.toString())) {
            bol.setBolNumber(this.getSegmentValue(segment, 1, 1));
            bol.setBolType("EBL");
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.TDT.toString())) {
            tdt = segment;
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.LOC.toString()) &&
                getSegmentValue(segment, 1).equals("11") &&
                getSegmentValue(segment, 2, 0).equals("AEJEA")) {
            loc = segment;
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.DTM.toString()) &&
                getSegmentValue(segment, 1, 0).equals("132") &&
                loc != null) {

            bol.setVesselName(getSegmentValue(tdt, 8, 3));
            bol.setVoyageNumber(getSegmentValue(tdt, 2));
            bol.setVesselETA(EdiUtils.getDate( getSegmentValue(segment.trim(), 1, 1 ),
                                    getSegmentValue(segment.trim(), 1, 2)));
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.NAD.toString()) &&
                getSegmentValue(segment.trim(), 1).equals("CN") &&
                loc != null) {
            bol.setConsigneeName(getSegmentValue(segment.trim(), 4, 0));
        } else if (startsWithIgnoreCase(segment.trim(), EdiConstants.NAD.toString()) &&
                getSegmentValue(segment, 1).equals("CA") &&
                loc != null) {
            bol.setShippingAgentCode(getSegmentValue(segment, 2, 0));
            bol.setShippingAgentName(getSegmentValue(segment, 4));
        }
    }

    @Override
    public Object parse() {

        try {
            bolList= new ArrayList<BOL>();
            bol = new BOL();
            Map segMentMap = Stream.of(EdiConstants.UNT.toString(), EdiConstants.RFF.toString(),
                                        EdiConstants.TDT.toString(), EdiConstants.LOC.toString(),
                                        EdiConstants.DTM.toString(),EdiConstants.NAD.toString())
                                          .collect(Collectors.toMap(s1 -> s1.toString(), s1 -> s1));
            super.scanFile(file, segMentMap);
            return bolList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
