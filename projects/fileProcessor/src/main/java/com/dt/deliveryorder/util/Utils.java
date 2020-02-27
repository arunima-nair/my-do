package com.dt.deliveryorder.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Utils {

    private Utils(){}
    public static String getRandomNumber(){
        UUID corrId = UUID.randomUUID();
        return corrId.toString();
    }
    public static String convertObjToJsonString(Object obj){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String getFirstLine(File file, String delimiter) {
        String firstLine=null;
        try (Scanner sc = new Scanner(file).useDelimiter(Pattern.compile(delimiter))) {
            while (sc.hasNext() ) {
                firstLine = sc.next();
                if (!StringUtils.isEmpty(firstLine))
                    break;
            }
        } catch(Exception ex) {}
        return firstLine;
    }

    public static String getTodayDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return  sdf.format(new Date());
    }


    public static String getFileSystemPathSeperator(){
        return File.separator;
    }

    private static Date getDate(SimpleDateFormat sdf, String date){
        try {
        return     sdf.parse(date);
        } catch (Exception ex) {

        }
        return null;
    }

    public static String getFormattedDate(String date, String format){
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        return  format2.format(getDate(format1, date) );
    }





}
