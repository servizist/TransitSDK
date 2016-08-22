package it.sad.sii.transit.sdk.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class JourneyStartedNotification implements Serializable {

    public List<WaypointTime> waypoints;
    public String transportId;
    public String lineId;
    public String runId;
    public TimeTable.Direction direction;
    public boolean isPlanned;
    public long txTime;

    public JourneyStartedNotification() {
    }

    public JourneyStartedNotification(List<WaypointTime> waypoints, String transportId, String lineId, String runId,
                                      TimeTable.Direction direction, boolean isPlanned, long txTime) {
        this.waypoints = waypoints;
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.direction = direction;
        this.isPlanned = isPlanned;
        this.txTime = txTime;
    }

    public String toString() {
        String s = "JourneyStartedNotification{";
        for (WaypointTime waypoint : waypoints) {
            s += waypoint.toString() + ", ";
        }
        s += "transportId=" + transportId +
             ", lineId=" + lineId +
             ", runId=" + runId +
             ", direction=" + direction +
             ", isPlanned=" + (isPlanned ? "true" : "false") +
             ", txTime=" + txTime +
             "}";
        return s;
    }
}
