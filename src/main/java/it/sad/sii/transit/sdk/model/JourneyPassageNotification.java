package it.sad.sii.transit.sdk.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyPassageNotification implements Serializable {

    public String waypointId;
    public String transportId;
    public String lineId;
    public String runId;
    public String expectedRelativeTime;
    public String measuredRelativeTime;
    public long txTime;

    public JourneyPassageNotification() { }

    // TODO! Add direction so that we can load information (via, destinatio, ...) on info panels correctly
    public JourneyPassageNotification(String waypointId, String transportId, String lineId, String runId,
                                      String expectedRelativeTime, String measuredRelativeTime, long txTime) {
        this.waypointId = waypointId;
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.expectedRelativeTime = expectedRelativeTime;
        this.measuredRelativeTime = measuredRelativeTime;
        this.txTime = txTime;
    }

    public String toString() {
        return "JourneyPassageNotification{" +
               "waypointId=" + waypointId +
               ", transportId=" + transportId +
               ", lineId=" + lineId +
               ", runId=" + runId +
               ", expectedRelativeTime=" + expectedRelativeTime +
               ", measuredRelativeTime=" + measuredRelativeTime +
               ", txTime = " + txTime +
               "}";
    }
}
