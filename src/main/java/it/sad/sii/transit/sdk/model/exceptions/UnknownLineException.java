package it.sad.sii.transit.sdk.model.exceptions;


/**
 * Created by ldematte on 11/14/16.
 */
public class UnknownLineException extends UnknownEntityException {
    public UnknownLineException(String lineId) {
        super(lineId);
    }
}
