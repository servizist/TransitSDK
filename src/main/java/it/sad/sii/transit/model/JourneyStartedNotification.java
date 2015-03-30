package it.sad.sii.transit.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JourneyStartedNotification implements Serializable {

   public List<WaypointTime> waypoints;
   public int transportId;
   public int lineId;
   public int runId;
   public String direction;
   public boolean isPlanned;

   public JourneyStartedNotification() {
   }

   public JourneyStartedNotification(List<WaypointTime> waypoints, int transportId, int lineId, int runId, String direction, boolean isPlanned) {
      this.waypoints = waypoints;
      this.transportId = transportId;
      this.lineId = lineId;
      this.runId = runId;
      this.direction = direction;
      this.isPlanned = isPlanned;
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
            "}";
      return s;
   }
}
