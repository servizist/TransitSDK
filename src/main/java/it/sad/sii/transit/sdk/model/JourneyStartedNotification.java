package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class JourneyStartedNotification extends JourneyNotification implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<WaypointTime> waypoints;
    public TimeTable.Direction direction;
    public boolean isPlanned;

    public JourneyStartedNotification() {
    }

    public JourneyStartedNotification(List<WaypointTime> waypoints, String transportId, String lineId, String runId,
                                      TimeTable.Direction direction, boolean isPlanned, long txTime) {

        if (isPlanned && waypoints != null) {
            throw new IllegalArgumentException("If the line is planned, 'waypoints' should be null");
        }
        this.waypoints = waypoints;
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.direction = direction;
        this.isPlanned = isPlanned;
        this.txTime = txTime;
    }

    public List<WaypointTime> getWaypoints() {
        if (isPlanned)
            throw new IllegalStateException("No waypoint times for a planned run, use a TimeTable instead!");
        return waypoints;
    }

    public String toString() {
        String s = "JourneyStartedNotification{";
        if (!isPlanned) {
            for (WaypointTime waypoint : getWaypoints()) {
                s += waypoint.toString() + ", ";
            }
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

    @Override
    public void visit(INotificationProcessor processor, Logger logger) throws IOException {
        processor.process(this, logger);
    }
}
