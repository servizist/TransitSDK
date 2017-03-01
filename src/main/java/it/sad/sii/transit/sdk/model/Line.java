package it.sad.sii.transit.sdk.model;

import java.io.Serializable;


public class Line implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String id;

    public String description;

    public boolean urban;

    public boolean mixed;

    public Number carrierId;

    public boolean citybus;

    public boolean nightliner;

    public String commercialCode;

    public boolean waypointclockAllowed = false;
}
