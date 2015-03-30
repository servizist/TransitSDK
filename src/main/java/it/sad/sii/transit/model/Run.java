package it.sad.sii.transit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.sad.sii.transit.utils.DateTimeDeserializer;
import it.sad.sii.transit.utils.DateTimeSerializer;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ldematte on 10/9/14.
 */
@XmlRootElement
public class Run {
    public int id;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime startTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime endTime;

    public Run() { }

    public Run(int id, DateTime startTime, DateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
