package it.sad.sii.transit.sdk.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldematte on 10/29/15.
 */
@XmlRootElement
public class RunDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String lineId;
    public String runId;
    public String direction;
    public String runCategory;

    public List<WaypointTimeDetails> waypoints = new ArrayList<>();

    public RunDetails() {
    }

}