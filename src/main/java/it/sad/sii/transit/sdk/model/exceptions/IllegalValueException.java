package it.sad.sii.transit.sdk.model.exceptions;

/**
 * Created by mmutschl on 09/09/14.
 */
public class IllegalValueException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public IllegalValueException() {
        super();
    }

    public IllegalValueException(String message) {
        super(message);
    }

    public IllegalValueException(String message, Throwable throwable) {
        super(message, throwable);
    }
}