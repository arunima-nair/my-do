package ae.dt.deliveryorder.exception;

import ae.dt.common.dto.ErrorDto;



public class BoLException extends RuntimeException {

    private String field;

    public BoLException(String message, String field){
        super(message);
        this.field=field;
    }

}
