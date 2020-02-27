package ae.dt.deliveryorder.exception;

import ae.dt.common.exception.ApplicationException;

public class UnAuthorizedResourceAccessException extends ApplicationException {
    public UnAuthorizedResourceAccessException(String string, String string1, Throwable throwable) {
        super(string, string1, throwable);
    }

    public UnAuthorizedResourceAccessException(Throwable throwable) {
        super(throwable);
    }

    public UnAuthorizedResourceAccessException() {
        super();
    }
    

    public UnAuthorizedResourceAccessException(String message) {
        super(message);
    }
}
