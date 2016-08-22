package it.sad.sii.transit.sdk.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldematte on 10/29/15.
 */
@XmlRootElement
public class RunDetails implements Serializable {
    public String lineId;
    public String runId;
    public String direction;
    public String runCategory;

    public List<WaypointTimeDetails> waypoints = new ArrayList<>();

    public RunDetails() {
    }

}