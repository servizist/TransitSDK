package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.bz.sii.common.conversions.DateTimeDeserializer;
import it.bz.sii.common.conversions.DateTimeSerializer;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by ldematte on 10/29/15.
 */

@XmlRootElement
public class WaypointTimeDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String waypointId;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime arrivalTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime departureTime;
}