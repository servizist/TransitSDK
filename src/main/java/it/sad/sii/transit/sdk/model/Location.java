package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.CoordinateConvert;

import java.io.Serializable;

/**
 * Created by ldematte on 11/11/16.
 */
public class Location implements Serializable {
    private Number longitude;
    private Number latitude;

    public Location() { }

    public Location(Number longitude, Number latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }

    public Number getLatitude() {
        return latitude;
    }

    public void setLatitude(Number latitude) {
        this.latitude = latitude;
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

    public static Location fromUTM(double easting, double northing) {
        double[] latlon = CoordinateConvert.UTM2LatLon(easting, northing, 32, "N");
        return new Location(latlon[1], latlon[0]);
    }
}
