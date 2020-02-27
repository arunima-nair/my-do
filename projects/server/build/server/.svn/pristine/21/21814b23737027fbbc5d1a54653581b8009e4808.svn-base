package ae.dt.common.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya.Grover on 08/10/2018.
 */
public enum GenderType {

    MALE("MALE","الذكر"), FEMALE("FEMALE","إناثا")  ;

    private String value;
    private String arabicValue;

    GenderType(String value, String arabicValue) {
        this.value = value;
        this.arabicValue = arabicValue;
    }

    public String getValue() {
        return value;
    }

    public String getArabicValue() {
        return arabicValue;
    }


    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (GenderType field : GenderType.values()) {
            values.add(field.getValue());
        }
        return values;
    }

    public static List<String> getArabicValues() {
        List<String> values = new ArrayList<>();
        for (GenderType field : GenderType.values()) {
            values.add(field.getArabicValue());
        }
        return values;
    }

   /* public static GenderType getByFieldName(String fieldName) {
        for (GenderType field : GenderType.values()) {
            if (StringUtils.equalsIgnoreCase(field.name(), fieldName)) {
                return field;
            }
        }
        return null;
    }*/

    /*public static GenderType getByValue(String value) {
        for (GenderType field : GenderType.values()) {
            if (StringUtils.equalsIgnoreCase(field.getValue(), value)) {
                return field;
            }
        }
        return null;
    }*/

   /* public static Object getNamesByValues(List<String> invoiceStatus) {
        List<String> names = new ArrayList<>();
        for (String value : invoiceStatus) {
            names.add(GenderType.getByValue(value).name());
        }
        return names;
    }*/

}
