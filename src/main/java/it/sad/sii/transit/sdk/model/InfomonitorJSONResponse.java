package it.sad.sii.transit.sdk.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lconcli on 08/02/16.
 */

public class InfomonitorJSONResponse implements Serializable {

    private String type;
    private List<String> waypointIds;
    private String onlyLane;
    private int maxPages;
    private boolean hasImage;

    public InfomonitorJSONResponse(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getWaypointIds() {
        return waypointIds;
    }

    public void setWaypointIds(List<String> waypointIds) {
        this.waypointIds = waypointIds;
    }

    public String getOnlyLane() {
        return onlyLane;
    }

    public void setOnlyLane(String onlyLane) {
        this.onlyLane = onlyLane;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasImage() {
        return hasImage;
    }
}
