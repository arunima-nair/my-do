package com.dt.deliveryorder.exception;

public class UnRecoverableException extends RuntimeException {

   private String message;
   public   UnRecoverableException(){
       super();
   }

   public UnRecoverableException(String message) {
       super(message);
       this.message = message;
   }
    public UnRecoverableException(Exception ex) {
        super(ex);

    }
}
