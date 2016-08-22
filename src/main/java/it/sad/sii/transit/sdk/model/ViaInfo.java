package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

/**
 * Created by lconcli on 10/11/15.
 */
public class ViaInfo implements Serializable {
    public ViaInfo() {
        super();
    }

    public ViaInfo(String waypointId, String nodeId, String originalId, String descriptionIT, String descriptionDE) {
        this.waypointId = waypointId;
        this.nodeId = nodeId;
        this.originalId = originalId;
        this.descriptionIT = descriptionIT;
        this.descriptionDE = descriptionDE;
    }

    public ViaInfo(String waypointId, Integer nodeId, Integer originalId, String descriptionIT, String descriptionDE) {
        this.waypointId = waypointId;
        this.nodeId = null;
        if (nodeId != null)
            this.nodeId = nodeId.toString();
        this.originalId = null;
        if (originalId != null)
            this.originalId = originalId.toString();
        this.descriptionIT = descriptionIT;
        this.descriptionDE = descriptionDE;
    }

    private String waypointId;
    private String nodeId;
    private String originalId;
    private String descriptionIT;
    private String descriptionDE;

    public String getWaypointId() {
        return waypointId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getOriginalId() {
        return originalId;
    }

    public String getDescriptionIT() {
        return descriptionIT;
    }

    public String getDescriptionDE() {
        return descriptionDE;
    }
}