package it.sad.sii.transit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WaypointTime {

   public int waypointId;
   public String relativeTime; // HH:mm:ss (time difference)

   public WaypointTime() {
   }

   public WaypointTime(int waypointId, String relativeTime) {
      this.waypointId = waypointId;
      this.relativeTime = relativeTime;
   }

   public String toString() {
      return "WaypointTime{" +
            "waypointId=" + waypointId +
            ", relativeTime=" + relativeTime +
            "}";
   }

}
