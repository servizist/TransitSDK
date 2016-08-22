package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mmutschl on 30/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartureResponse implements Serializable{

    private StopInfo waypoint;
    private List<Departure> departures;

    public DepartureResponse() {
    }

    public DepartureResponse(StopInfo waypoint, List<Departure> departures) {
        this.waypoint = waypoint;
        this.departures = departures;
    }

    public StopInfo getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(StopInfo waypoint) {
        this.waypoint = waypoint;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }
}
