package com.dt.deliveryorder.parser;

import com.dt.deliveryorder.util.FileMappingUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.dt.deliveryorder.util.EdiUtils.EDI_DELIMITER;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

public abstract class EdiFileParser {


 protected  FileMappingUtil fileMappingUtil = new FileMappingUtil();
 protected Map lineConfMap = new HashMap();

    protected enum EdiConstants {
        RFF("RFF"),
        TDT("TDT"),
        LOC("LOC"),
        DTM("DTM"),
        NAD("NAD"),
        UNT("UNT"),
        UNH("UNH"),
        BGM("BGM"),
        BM("BM"),
        MOA("MOA"),
        AEX("AEX"),
        TCC("TCC");

        String ediConstant;

        EdiConstants(String s) {
            this.ediConstant = s;
        }

        @Override
        public String toString() {
            return ediConstant;
        }
    }

    protected String getSegmentValue(String segment, Integer parentPos, Integer childPos) {
        return splitPreserveAllTokens(this.getSegmentValue(segment, parentPos), ":")[childPos];
    }

    protected String getSegmentValue(String segment, Integer parentPos) {
        return splitPreserveAllTokens(segment, "+")[parentPos];
    }


    protected void scanFile(File f, Map segments) {
        String segement=null;
        try (Scanner sc = new Scanner(f).useDelimiter(Pattern.compile(EDI_DELIMITER))) {
            while (sc.hasNext()) {
                segement = sc.next();
                while (segement.endsWith("?"))
                    segement = segement + sc.next();

                if (segments.get(getSegmentValue(segement, 0)) != null)
                    segmentCallback(segement.trim());
            }
        }catch (Exception ex) {
                throw new RuntimeException("Unable to process File", ex);
        }

        System.out.println(" END ");

    }

    protected abstract void segmentCallback(String s);

    public abstract Object parse();

}
