package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mmutschl on 30/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrivalResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private StopInfo waypoint;
    private List<Arrival> arrivals;

    public ArrivalResponse() {
    }

    public ArrivalResponse(StopInfo waypoint, List<Arrival> arrivals) {
        this.waypoint = waypoint;
        this.arrivals = arrivals;
    }

    public StopInfo getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(StopInfo waypoint) {
        this.waypoint = waypoint;
    }

    public List<Arrival> getArrivals() {
        return arrivals;
    }

    public void setArrivals(List<Arrival> arrivals) {
        this.arrivals = arrivals;
    }
}
