package it.sad.sii.transit.sdk.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class WaypointTime implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String waypointId;
    public String relativeTime; // HH:mm:ss (time difference)

    public WaypointTime() {
    }

    public WaypointTime(String waypointId, String relativeTime) {
        this.waypointId = waypointId;
        this.relativeTime = relativeTime;
    }

    public String toString() {
        return "WaypointTime{" +
                "waypointId=" + waypointId +
                ", relativeTime=" + relativeTime +
                "}";
    }
}