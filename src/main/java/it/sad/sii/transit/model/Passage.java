package it.sad.sii.transit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.sad.sii.transit.utils.DateTimeDeserializer;
import it.sad.sii.transit.utils.DateTimeSerializer;
import it.sad.sii.transit.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.Serializable;

/**
 * Created by ldematte on 9/30/14.
 * <p/>
 * The common information needed to describe and visualize a passage.
 * Notice that this class is plain, technology independent: no assumption about
 * solari monitors, service, JSON or whatever is made here.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Passage implements Serializable {

    public Passage() {}

    public Passage(int lineId, int runId, DateTime scheduledTime, int waypointId, String comCode, String destinationDE,
                   String destinationIT) {
        this.lineId = lineId;
        this.runId = runId;
        this.scheduledTime = scheduledTime;
        this.estimatedTime = scheduledTime;
        this.destinationIT = destinationIT;
        this.destinationDE = destinationDE;
        this.waypointId = waypointId;
        this.comCode = comCode;
    }

    public Passage(int lineId, int runId, DateTime scheduledTime, DateTime estimatedTime, int waypointId,
                   String comCode,
                   String destinationDE, String destinationIT) {
        this.lineId = lineId;
        this.runId = runId;
        this.scheduledTime = scheduledTime;
        this.estimatedTime = estimatedTime;
        this.destinationIT = destinationIT;
        this.destinationDE = destinationDE;
        this.waypointId = waypointId;
        this.comCode = comCode;
    }

    public String getKey() {
        return String.format("W%d-L%d-R%d-%d", waypointId, lineId, runId, scheduledTime.getSecondOfDay());
    }

    private int lineId;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    private int runId;

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime scheduledTime;

    public DateTime getScheduledTime() {
        return scheduledTime;
    }

    /**
     * IDPalina
     */
    private int waypointId;

    public int getWaypointId() {
        return waypointId;
    }

    public void setWaypointId(int waypointId) {
        this.waypointId = waypointId;
    }

    /**
     * NomeLinea
     */
    private String comCode;

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * OraArrivoStimata
     */
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime estimatedTime;

    public void setEstimatedTime(DateTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public DateTime getEstimatedTime() {
        return estimatedTime;
    }

    public int getDueIn() {
        return Minutes.minutesBetween(DateTime.now(), estimatedTime).getMinutes();
    }

    /**
     * Destinazione seconda parte (dopo il delimiter)
     */
    private String destinationDE;

    public String getDestinationDE() {
        return destinationDE;
    }

    public void setDestinationDE(String destinationDE) {
        this.destinationDE = destinationDE;
    }

    /**
     * Destinazione prima parte (prima del delimiter)
     */
    private String destinationIT;

    public String getDestinationIT() {
        return destinationIT;
    }

    public void setDestinationIT(String destinationIT) {
        this.destinationIT = destinationIT;
    }

    @Override
    public String toString() {
        return "Passage{lineId=" + lineId + ", runId=" + runId + ", scheduledTime=" +
               DateUtils.printDateTime(scheduledTime) + ", " + "comCode=" + comCode + ", waypointId=" + waypointId +
               ", estimatedTime=" + DateUtils.printDateTime(estimatedTime) + ", destinationDE=" + destinationDE +
               ", destinationIT=" + destinationIT + "}";
    }

    public String getDelay() {
        // TODO! Distinguish between a real "delay" or a "no news" situation
        return Integer.toString(Minutes.minutesBetween(estimatedTime, scheduledTime).getMinutes());
    }

    public String getLastCommunicationTime() {
        return "TODO!";
    }

    public String getLastCommunicationPoint() {
        return "TODO!";
    }

}
