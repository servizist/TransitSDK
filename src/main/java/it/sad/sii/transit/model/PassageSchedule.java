package it.sad.sii.transit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.sad.sii.transit.utils.DateTimeDeserializer;
import it.sad.sii.transit.utils.DateTimeSerializer;
import it.sad.sii.transit.utils.DateUtils;
import org.joda.time.DateTime;

/**
 * Created by ldematte on 9/30/14.
 * <p/>
 */
public class PassageSchedule {
    private final int lineId;
    private final int runId;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private final DateTime scheduledTime;
    private final int recurrence;
    private final String direction;
    protected final int siiCode;
    protected final String comCode;

    public PassageSchedule(int lineId, int runId, int scheduledTime, int siiCode, String comCode, int recurrence,
                           String direction, int validAt) {
        this.lineId = lineId;
        this.runId = runId;
        this.scheduledTime = DateUtils.secondsSinceMidnightToDate(scheduledTime,
                                                                  DateUtils.parseDate(String.valueOf(validAt)).toLocalDate());
        this.recurrence = recurrence;
        this.direction = direction;
        this.siiCode = siiCode;
        this.comCode = comCode;
    }

    public int getLineId() {
        return lineId;
    }

    public int getRunId() {
        return runId;
    }

    public DateTime getScheduledTime() {
        return scheduledTime;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public String getDirection() {
        return direction;
    }

    public int getSiiCode() {
        return siiCode;
    }

    public String getComCode() {
        return comCode;
    }
}