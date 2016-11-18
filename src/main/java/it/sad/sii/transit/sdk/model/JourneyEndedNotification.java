package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyEndedNotification extends JourneyNotification implements Serializable {

    public JourneyEndedNotification() {
    }

    public JourneyEndedNotification(String transportId, String lineId, String runId, long txTime) {
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.txTime = txTime;
        this.waypointId = null;
    }

    public String toString() {
        return "JourneyEndedNotification{" +
               "transportId=" + transportId +
               ", lineId=" + lineId +
               ", runId=" + runId +
               ", txTime=" + txTime +
               "}";
    }

    @Override
    public void visit(INotificationProcessor processor) {
        processor.process(this);
    }
}
