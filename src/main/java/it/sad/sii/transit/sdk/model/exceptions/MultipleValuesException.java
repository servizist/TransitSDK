package it.sad.sii.transit.sdk.model.exceptions;

/**
 * Created by mmutschl on 09/09/14.
 */
public class MultipleValuesException extends RuntimeException {
    public MultipleValuesException() {
        super();
    }

    public MultipleValuesException(String message) {
        super(message);
    }

    public MultipleValuesException(String message, Throwable throwable) {
        super(message, throwable);
    }
}