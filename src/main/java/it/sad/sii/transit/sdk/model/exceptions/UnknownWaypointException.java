package it.sad.sii.transit.sdk.model.exceptions;

/**
 * Created by ldematte on 11/14/16.
 */
public class UnknownWaypointException extends Exception {
    private final String waypointId;

    public UnknownWaypointException(String waypointId) {

        this.waypointId = waypointId;
    }

    public String getWaypointId() {
        return waypointId;
    }
}
