package by.bsu.travelagency.logic.exceptions;

/**
 * Created by Михаил on 9/14/2016.
 */
public class BusinessLogicException extends Exception {

    /**
     * Instantiates a new business logic exception.
     */
    public BusinessLogicException() {
        super();
    }

    /**
     * Instantiates a new business logic exception.
     *
     * @param message the message
     */
    public BusinessLogicException(String message) {
        super(message);
    }

    /**
     * Instantiates a new business logic exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new business logic exception.
     *
     * @param cause the cause
     */
    public BusinessLogicException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new business logic exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BusinessLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
