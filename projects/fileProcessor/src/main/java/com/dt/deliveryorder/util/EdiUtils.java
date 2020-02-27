package com.dt.deliveryorder.util;

import com.dt.deliveryorder.dto.Edi;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EdiUtils {

    private EdiUtils() {}

    public static final String EDI_DELIMITER="[\\r\\n']";
    public static Edi getEdiDetails(File file, String delimiter) {
        String line=null;
        Edi edi=null;
        try(Scanner sc = new Scanner(file).useDelimiter(Pattern.compile(delimiter))) {
            edi = new Edi();
            while (sc.hasNext() ) {
                line = sc.next();
                if (StringUtils.startsWithIgnoreCase(line,"UNB")){
                    edi.setCustomerCode(StringUtils.splitPreserveAllTokens(line,"+")[2]);
                }
                if (StringUtils.startsWithIgnoreCase(line,"UNH")){

                    edi.setStandard(StringUtils.splitPreserveAllTokens(
                            StringUtils.splitPreserveAllTokens(line,"+")[2],":")[0]);
                    edi.setVersion(StringUtils.splitPreserveAllTokens(
                            StringUtils.splitPreserveAllTokens(line,"+")[2],":")[1]);
                    edi.setRelease(StringUtils.splitPreserveAllTokens(
                            StringUtils.splitPreserveAllTokens(line,"+")[2],":")[2]);
                    break;
                }
            }

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return edi;
    }

    public static Boolean isUnEdiFACT(File f){
        String line = Utils.getFirstLine(f,EDI_DELIMITER);
        return StringUtils.startsWith(line,"UNA") || StringUtils.startsWith(line,"UNB");
    }

    public static String getDate(String date, String format){
        String year = null, month=null, day=null;
        if (StringUtils.equals("102",format)){
            year = StringUtils.substring(date,0,4);
            month = StringUtils.substring(date,4,6);
            day = StringUtils.substring(date,6,8);
        }

        return day+"-"+month+"-"+year;
    }
}
