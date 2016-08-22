package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.sad.sii.transit.sdk.utils.DateTimeDeserializer;
import it.sad.sii.transit.sdk.utils.DateTimeSerializer;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by ldematte on 10/29/15.
 */

@XmlRootElement
public class WaypointTimeDetails implements Serializable {
    public String waypointId;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime arrivalTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime departureTime;
}