package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ericci on 30/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopInfo implements Serializable {
    private String id;
    private String timeNodeId;
    private String nameIT;
    private String nameDE;
    private List<String> infoIT;
    private List<String> infoDE;

    public StopInfo() {
    }

    public StopInfo(String waypointOrGroupId, String nameDE, String nameIT, List<String> infoDE, List<String> infoIT) {
        this.id = waypointOrGroupId;
        this.nameDE = nameDE;
        this.nameIT = nameIT;
        this.infoDE = infoDE;
        this.infoIT = infoIT;
    }

    public StopInfo(String waypointOrGroupId, String timeNodeId, String nameDE, String nameIT, List<String> infoDE, List<String> infoIT) {
        this.id = waypointOrGroupId;
        this.timeNodeId = timeNodeId;
        this.nameDE = nameDE;
        this.nameIT = nameIT;
        this.infoDE = infoDE;
        this.infoIT = infoIT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeNodeId() {
        return timeNodeId;
    }

    public void setTimeNodeId(String timeNodeId) {
        this.timeNodeId = timeNodeId;
    }

    public String getNameIT() {
        return nameIT;
    }

    public void setNameIT(String nameIT) {
        this.nameIT = nameIT;
    }

    public String getNameDE() {
        return nameDE;
    }

    public void setNameDE(String nameDE) {
        this.nameDE = nameDE;
    }

    public List<String> getInfoIT() {
        return infoIT;
    }

    public void setInfoIT(List<String> infoIT) {
        this.infoIT = infoIT;
    }

    public List<String> getInfoDE() {
        return infoDE;
    }

    public void setInfoDE(List<String> infoDE) {
        this.infoDE = infoDE;
    }

    public String toString() {
        return "StopInfo {" + "\n" +
               "id=" + id + "\n" +
               ", name DE/It =" + nameDE + "/" + nameIT + "\n" +
               ", info DE/It =" + infoDE + "/" + infoIT + "\n" +
               "}";
    }
}