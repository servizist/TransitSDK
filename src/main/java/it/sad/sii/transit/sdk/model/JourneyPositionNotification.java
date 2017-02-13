package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyPositionNotification extends JourneyNotification implements Serializable {

    public double edgePercentage;

    public JourneyPositionNotification() { }

    public JourneyPositionNotification(String waypointId, String transportId, String lineId, String runId,
                                       double edgePercentage, long txTime) {
        this.waypointId = waypointId;
        this.edgePercentage = edgePercentage;
        this.transportId = transportId;
        this.lineId = lineId;
        this.runId = runId;
        this.txTime = txTime;
    }

    public String toString() {
        return "JourneyPositionNotification{" +
               "waypointId=" + waypointId +
               ", transportId=" + transportId +
               ", lineId=" + lineId +
               ", runId=" + runId +
               ", txTime = " + txTime +
               "}";
    }

    @Override
    public void visit(INotificationProcessor processor, Logger logger) {
        processor.process(this, logger);
    }
}
