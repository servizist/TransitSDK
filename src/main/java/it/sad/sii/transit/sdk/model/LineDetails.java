package it.sad.sii.transit.sdk.model;

import it.bz.sii.common.BilingualString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public List<Waypoint> forwardWaypoints;
    public List<Waypoint> backwardWaypoints;

    public String descriptionDe;
    public String descriptionIt;
    public String description;
    public String forwardTerminusIt;
    public String forwardTerminusDe;
    public String backwardTerminusIt;
    public String backwardTerminusDe;
    public String carrierDescriptionDe;
    public String carrierDescriptionIt;
    public String carrierName;

    public boolean urban;
    public boolean mixed;
    public boolean citybus;
    public boolean nightliner;

    public Number carrierId;
    public Number lineId;

    public Map<String, BilingualString> zones = new HashMap<>();

    public String commercialCode;
}