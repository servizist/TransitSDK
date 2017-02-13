package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by ldematte on 11/15/16.
 */
public abstract class JourneyNotification {
    public String transportId;
    public String lineId;
    public String runId;
    public long txTime;
    public String waypointId;

    public abstract void visit(INotificationProcessor processor, Logger logger) throws IOException;
}
