package it.sad.sii.transit.sdk.model.exceptions;


/**
 * Created by ldematte on 11/14/16.
 */
public class UnknownLineException extends UnknownEntityException {
    private static final long serialVersionUID = 1L;
    
    public UnknownLineException(String lineId) {
        super(lineId);
    }
}
