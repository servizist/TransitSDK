package it.sad.sii.transit.sdk.utils;

import it.sad.sii.transit.sdk.model.JourneyEndedNotification;
import it.sad.sii.transit.sdk.model.JourneyPassageNotification;
import it.sad.sii.transit.sdk.model.JourneyPositionNotification;
import it.sad.sii.transit.sdk.model.JourneyStartedNotification;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by ldematte on 11/15/16.
 *
 * Processor interface for double dispatching
 */
public interface INotificationProcessor {
    void process(JourneyStartedNotification notification, Logger logger) throws IOException;
    void process(JourneyEndedNotification notification, Logger logger);
    void process(JourneyPassageNotification notification, Logger logger);
    void process(JourneyPositionNotification notification, Logger logger);
}
