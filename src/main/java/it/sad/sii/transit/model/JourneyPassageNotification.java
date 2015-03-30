package it.sad.sii.transit.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JourneyPassageNotification implements Serializable {

   public int waypointId;
   public int transportId;
   public int lineId;
   public int runId;
   public String expectedRelativeTime;
   public String measuredRelativeTime;

   public JourneyPassageNotification() {

   }

   public JourneyPassageNotification(int waypointId, int transportId, int lineId, int runId, String expectedRelativeTime,
                                     String measuredRelativeTime) {
      this.waypointId = waypointId;
      this.transportId = transportId;
      this.lineId = lineId;
      this.runId = runId;
      this.expectedRelativeTime = expectedRelativeTime;
      this.measuredRelativeTime = measuredRelativeTime;
   }

   public String toString() {
      return "JourneyPassageNotification{" +
            "waypointId=" + waypointId +
            ", transportId=" + transportId +
            ", lineId=" + lineId +
            ", runId=" + runId +
            ", expectedRelativeTime=" + expectedRelativeTime +
            ", measuredRelativeTime=" + measuredRelativeTime +
            "}";
   }
}
