package it.sad.sii.transit.sdk.model.exceptions;


/**
 * Created by ldematte on 11/14/16.
 */
public class UnknownWaypointException extends UnknownEntityException {
    public UnknownWaypointException(String waypointId) {
        super(waypointId);
    }

    public UnknownWaypointException() {
    }
}
