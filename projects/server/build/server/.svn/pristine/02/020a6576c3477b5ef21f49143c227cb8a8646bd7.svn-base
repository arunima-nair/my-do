package ae.dt.common.exception;

public class ApplicationException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ApplicationException() {
        super();
    }

    private String code;
    private String message;

    public ApplicationException(Throwable th) {
        super(th);
    }

    public ApplicationException(String code, String message){
        super(message);

    }

    public ApplicationException(String code,String message, Throwable th) {
        super(message,th);
        this.code = code;

    }
    public ApplicationException(String message){
        super(message);
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
