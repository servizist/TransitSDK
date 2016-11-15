package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.INotificationProcessor;

import java.io.IOException;

/**
 * Created by ldematte on 11/15/16.
 */
public abstract class JourneyNotification {
    public String transportId;
    public String lineId;
    public String runId;
    public long txTime;

    public abstract void visit(INotificationProcessor processor) throws IOException;
}
