package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

/**
 * Created by ldematte on 10/30/15.
 */
public enum TransitTimeType implements Serializable {
    DEPARTURE_SCHEDULED,
    DEPARTURE_ESTIMATED,
    ARRIVAL_SCHEDULED,
    ARRIVAL_ESTIMATED
}