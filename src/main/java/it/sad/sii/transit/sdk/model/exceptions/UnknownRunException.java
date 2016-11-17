package it.sad.sii.transit.sdk.model.exceptions;

/**
 * Created by ldematte on 11/17/16.
 */
public class UnknownRunException extends UnknownEntityException {
    public UnknownRunException(String runId) {
        super(runId);
    }
}
