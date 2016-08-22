package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

/**
 * Created by ericci on 27/11/15.
 */
public enum TransportType implements Serializable {
    OTHER (0),
    BUS (1),
    TRAIN (2),
    CABLEWAY (3);

    private int type;

    TransportType(int type) {
        this.type = type;
    }
}