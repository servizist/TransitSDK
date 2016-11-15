package it.sad.sii.transit.sdk.model.exceptions;

/**
 * Created by ldematte on 11/14/16.
 */
public class UnknownLineException extends Exception {
    private final String lineId;

    public UnknownLineException(String lineId) {

        this.lineId = lineId;
    }

    public String getLineId() {
        return lineId;
    }
}
