package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyPassageNotification extends JourneyNotification implements Serializable {

    public String expectedRelativeTime;
    public String measuredRelativeTime;

    public DateTime exitTime;

    public JourneyPassageNotification() { }

    // TODO! Add direction so that we can load information (via, destination, ...) on info panels correctly
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

    public JourneyPassageNotification(String waypointId, String transportId, String lineId, String runId,
                                      DateTime exitTime, long txTime) {
        this.waypointId = waypointId;
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.exitTime = exitTime;
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

    @Override
    public void visit(INotificationProcessor processor) {
        processor.process(this);
    }
}
