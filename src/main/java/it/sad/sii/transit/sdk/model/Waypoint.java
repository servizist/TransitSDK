package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

public class Waypoint extends Location implements Serializable {
    public String descriptionDe;
    public String descriptionIt;
    public String id;
    public String nodeId;
    public String ipAddress;
    public String displayIdentifier;
    public Number displayPosition;

    public Number numberOfShownPassages;
    public Number timeNodeId;
    public Number position;

    public boolean isService;
    public boolean isStop;
    public boolean isRailway;

    public Waypoint() {}

    public Waypoint(Number longitude, Number latitude) {
        super(longitude, latitude);
    }

    public Waypoint(Number longitude, Number latitude, String descriptionDe, String descriptionIt) {
        super (longitude, latitude);
        this.descriptionIt = descriptionIt;
        this.descriptionDe = descriptionDe;
    }
}