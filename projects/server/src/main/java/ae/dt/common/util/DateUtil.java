package ae.dt.common.util;

import ae.dt.common.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ae.dt.common.exception.BusinessException;

public class DateUtil {

    public static final String WEB_DATE_FORMAT = "d MMMMM, yyyy";
    public  static String DATE_FORMAT = "dd-MM-yyyy";
    public static String HOUR_FORMAT = "HH";
    public static String MINUTE_FORMAT = "mm";
    public static String TIME_FORMAT = HOUR_FORMAT + ":" + MINUTE_FORMAT;
    public static String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    public static String COMMON_DATE_FORMAT="dd/MM/yyyy";

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat parser = new SimpleDateFormat(pattern);
        parser.setLenient(false);
        try {
            return parser.parse(date);
        } catch (ParseException e) {
           throw new BusinessException(ErrorCodes.INVALID_DATE_CODE,"dateFormat",ErrorCodes.INVALID_DATE);
        }
    }

    public static Date parseDate(String date, String pattern,String field) {
        SimpleDateFormat parser = new SimpleDateFormat(pattern);
        parser.setLenient(false);
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            String errorMessage=field+ErrorCodes.INVALID_DATE;
            throw new DateTimeException(errorMessage);
        }
    }



    public static Date parseDate(String date) {
        return parseDate(date, WEB_DATE_FORMAT);
    }

    public static String getDateVal(Date date) {
        String day=String.valueOf(getDay(date));
        int monthInt=getMonth(date)+1;
        String month=null;
        if(monthInt<10){
            month= "0"+String.valueOf(monthInt);
        }else{
            month=String.valueOf(monthInt);
        }
        String year=String.valueOf(getYear(date));

        String dateVal=day+month+year;
        return dateVal;
    }

    public static int getYear(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getDay(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        int month=cal.get(Calendar.MONTH);
        return cal.get(Calendar.MONTH);
    }
    public static String formatDate(Date date, String pattern) {
        if(date==null)
            return null;
        SimpleDateFormat parser = new SimpleDateFormat(pattern);
        parser.setLenient(false);
        return parser.format(date);
    }
    public static String getCurrentTimeAsString(){
        Calendar c1 =
                GregorianCalendar.getInstance();
        Date dateType = c1.getTime();
        return DateUtil.formatDate(dateType,DATETIME_FORMAT);
    }
}
