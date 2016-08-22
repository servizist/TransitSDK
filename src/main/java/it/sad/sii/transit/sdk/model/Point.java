package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

public class Point implements Serializable {
    public String descriptionDe;
    public String descriptionIt;
    public String id;
    public String nodeId;
    public String ipAddress;
    public String displayIdentifier;
    public Number displayPosition;

    public Number numberOfShownPassages;
    public Number latitude;
    public Number longitude;
    public Number timeNodeId;
    public Number position;

    public boolean isService;
    public boolean isStop;
    public boolean isRailway;

    public Point() {}

    public Point(Number longitude, Number latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Point(Number longitude, Number latitude, String descriptionDe, String descriptionIt) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.descriptionIt = descriptionIt;
        this.descriptionDe = descriptionDe;
    }

    public double x() {
        return longitude.doubleValue();
    }

    public double y() {
        return latitude.doubleValue();
    }

    public void x(double p0) {
        longitude = p0;
    }

    public void y(double p0) {
        latitude = p0;
    }
}