package ctpparser;

/**
 * Created on 6/3/16.
 */
public class OutOfMemoryException extends Exception {
    protected OutOfMemoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OutOfMemoryException(Throwable cause) {
        super(cause);
    }

    public OutOfMemoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfMemoryException(String message) {
        super(message);
    }

    public OutOfMemoryException() {
        super();
    }
}
