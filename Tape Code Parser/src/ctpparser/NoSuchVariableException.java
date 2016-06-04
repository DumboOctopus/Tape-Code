package ctpparser;

/**
 * Created on 6/3/16.
 */
public class NoSuchVariableException extends Exception {
    public NoSuchVariableException() {
        super();
    }

    public NoSuchVariableException(String message) {
        super(message);
    }

    public NoSuchVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchVariableException(Throwable cause) {
        super(cause);
    }

    protected NoSuchVariableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
