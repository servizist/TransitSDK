package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;
import org.apache.log4j.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class JourneyEndedNotification extends JourneyNotification implements Serializable {
    private static final long serialVersionUID = 1L;
    
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
    public void visit(INotificationProcessor processor, Logger logger) {
        processor.process(this, logger);
    }
}
