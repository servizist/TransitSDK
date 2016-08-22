package it.sad.sii.transit.sdk.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyEndedNotification implements Serializable {

    public String transportId;
    public String lineId;
    public String runId;
    public long txTime;

    public JourneyEndedNotification() {
    }

    public JourneyEndedNotification(String transportId, String lineId, String runId, long txTime) {
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.txTime = txTime;
    }

    public String toString() {
        return "JourneyEndedNotification{" +
               "transportId=" + transportId +
               ", lineId=" + lineId +
               ", runId=" + runId +
               ", txTime=" + txTime +
               "}";
    }
}
