package ctpparser;

/**
 * Created on 6/3/16.
 */
public class InvalidMemoryAddressException extends Exception {
    public InvalidMemoryAddressException() {
        super();
    }

    public InvalidMemoryAddressException(String message) {
        super(message);
    }

    public InvalidMemoryAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMemoryAddressException(Throwable cause) {
        super(cause);
    }

    protected InvalidMemoryAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
