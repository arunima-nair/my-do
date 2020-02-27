package ae.dt.common.exception;


import ae.dt.common.dto.ErrorDto;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String errorCode;
    String field;
    List<ErrorDto> errorDtoList = new ArrayList<>();

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<ErrorDto> getErrorDtoList() {
        return this.errorDtoList;
    }

    public void setErrorDtoList(ErrorDto errorDTO) {
        this.errorDtoList.add(errorDTO);
    }

    public BusinessException(String errorCode, String field, String message){
        super(message);
        this.errorCode=errorCode;
        this.field=field;
    }

    public BusinessException(List<ErrorDto> errorDtoList) {
        super("");
        this.errorCode = "";
        this.field = "";
        this.errorDtoList = errorDtoList;
    }
    public BusinessException(String message) {
        super(message);
    }
}
