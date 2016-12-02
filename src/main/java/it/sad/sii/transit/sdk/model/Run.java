package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.bz.sii.common.conversions.DateDeserializer;
import it.bz.sii.common.conversions.DateSerializer;
import it.bz.sii.common.conversions.TimeDeserializer;
import it.bz.sii.common.conversions.TimeSerializer;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by ldematte on 10/9/14.
 */
@XmlRootElement
public class Run implements Serializable {
    public String id;

    @JsonSerialize(using = TimeSerializer.class)
    @JsonDeserialize(using = TimeDeserializer.class)
    public LocalTime startTime;

    @JsonSerialize(using = TimeSerializer.class)
    @JsonDeserialize(using = TimeDeserializer.class)
    public LocalTime endTime;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    public LocalDate date;

    public Run() {
    }

    public Run(String id, LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
}