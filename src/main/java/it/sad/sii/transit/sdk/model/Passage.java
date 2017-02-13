package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.bz.sii.common.DateUtils;
import it.bz.sii.common.conversions.DateTimeDeserializer;
import it.bz.sii.common.conversions.DateTimeSerializer;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by lconcli on 10/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Passage implements Serializable {
    public Passage() {
    }

    public Passage(String lineId, String runId, String comCode, String waypointId,
                   DateTime scheduledTime, DateTime estimatedTime,
                   Integer dueIn, String delay, Integer delayInSeconds,
                   String destinationDE, String destinationIT,
                   Long lastTxTime, String lastTxPoint) {
        this.lineId = lineId;
        this.runId = runId;
        this.comCode = comCode;
        this.waypointId = waypointId;
        this.scheduledTime = scheduledTime;
        this.estimatedTime = estimatedTime;
        this.dueIn = dueIn;
        this.delay = delay;
        this.delayInSeconds = delayInSeconds;
        this.destinationIT = destinationIT;
        this.destinationDE = destinationDE;
        this.lastTxTime = lastTxTime;
        this.lastTxPoint = lastTxPoint;
    }

    private String lineId;
    private String runId;
    private DateTime scheduledTime;
    private String waypointId;
    private String comCode;
    private DateTime estimatedTime;
    protected Integer delayInSeconds;
    private Integer dueIn;
    private String destinationDE;
    private String destinationIT;
    private long lastTxTime;
    private String lastTxPoint;
    private String delay;

    public String getKey() {
        return String.format("W%s-L%s-R%s-%d", waypointId, lineId, runId, scheduledTime.getSecondOfDay());
    }

    public String getLineId() {
        return lineId;
    }

    public String getRunId() {
        return runId;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime getScheduledTime() {
        return scheduledTime;
    }

    public String getWaypointId() {
        return waypointId;
    }

    public String getComCode() {
        return comCode;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime getEstimatedTime() {
        return estimatedTime;
    }

    public Integer getDelayInSeconds() {
        return delayInSeconds;
    }

    public Integer getDueIn() {
        return dueIn;
    }

    public String getDestinationDE() {
        return destinationDE;
    }

    public String getDestinationIT() {
        return destinationIT;
    }

    public long getLastTxTime() {
        return lastTxTime;
    }

    public String getLastTxPoint() {
        return lastTxPoint;
    }

    public String getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Transit{lineId=" + lineId + ", runId=" + runId + ", scheduledTime=" +
                DateUtils.printDateTime(getScheduledTime()) + ", " + "comCode=" + comCode + ", waypointId=" + waypointId +
                ", estimatedTime=" + DateUtils.printDateTime(getEstimatedTime()) + ", destinationDE=" + destinationDE +
                ", destinationIT=" + destinationIT + "}";
    }
}
