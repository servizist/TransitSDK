package it.sad.sii.transit.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyEndedNotification implements Serializable {

   public int transportId;
   public int lineId;
   public int runId;

   public JourneyEndedNotification() {
   }

   public JourneyEndedNotification(int transportId, int lineId, int runId) {
      this.transportId = transportId;
      this.lineId = lineId;
      this.runId = runId;
   }

   public String toString() {
      return "JourneyEndedNotification{" +
            "transportId=" + transportId +
            ", lineId=" + lineId +
            ", runId=" + runId +
            "}";
   }
}
