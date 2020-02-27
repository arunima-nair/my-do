package com.dt.deliveryorder.exception;

public class RecoverableException extends RuntimeException {
    private String message;

    public RecoverableException(){
        super();
    }

    public RecoverableException(String message){
        super(message);
        this.message = message;

    }

    public RecoverableException(String message, Exception ex){
        super(message,ex);
        this.message = message;
    }
}
