package it.sad.sii.transit.sdk.model;

import it.sad.sii.transit.sdk.utils.DateUtils;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by ldematte on 9/30/14.
 * <p/>
 */
public class PassageSchedule implements Serializable {
    private final String lineId;
    private final String runId;
    private final String carrierId;
    private final DateTime scheduledArrivalTime;
    private final DateTime scheduledDepartureTime;
    private int recurrence;
    private final TimeTable.Direction direction;
    protected final String siiCode;
    protected final String comCode;
    protected final String RFICode;
    protected final String runCategory;

    // For data sources which do not have "recurrence" as a primitive data
    // we need the progressive to compute recurrence
    private int progressive = 0;

    private PassageSchedule(String lineId, String runId, String carrierId, Integer scheduledArrivalTime,
                            Integer scheduledDepartureTime, String siiCode, String comCode,
                            TimeTable.Direction direction, int validAt) {
        this.lineId = lineId;
        this.runId = runId;
        this.carrierId = carrierId;
        if (scheduledArrivalTime != null)
            this.scheduledArrivalTime =
                    DateUtils.secondsSinceMidnightToDate(scheduledArrivalTime,
                                                         DateUtils.parseDate(String.valueOf(validAt)));
        else
            this.scheduledArrivalTime = null;
        if (scheduledDepartureTime != null)
            this.scheduledDepartureTime =
                    DateUtils.secondsSinceMidnightToDate(scheduledDepartureTime,
                                                         DateUtils.parseDate(String.valueOf(validAt)));
        else
            this.scheduledDepartureTime = null;

        this.direction = direction;
        this.siiCode = siiCode;
        this.comCode = comCode;
        this.RFICode = "";  // RFICode is useful only for TRAIN TIME TABLES, not for BUS!
        this.runCategory = "";  // runCategory is useful only for TRAIN TIME TABLES, not for BUS!
    }

    public PassageSchedule(String lineId, String runId, String carrierId, Integer scheduledArrivalTime,
                           Integer scheduledDepartureTime, String siiCode, String comCode, int recurrence,
                           TimeTable.Direction direction, int validAt, String RFICode, String runCategory) {
        this.lineId = lineId;
        this.runId = runId;
        this.carrierId = carrierId;
        if (scheduledArrivalTime != null)
            this.scheduledArrivalTime =
                    DateUtils.secondsSinceMidnightToDate(scheduledArrivalTime,
                                                         DateUtils.parseDate(String.valueOf(validAt)));
        else
            this.scheduledArrivalTime = null;
        if (scheduledDepartureTime != null)
            this.scheduledDepartureTime =
                    DateUtils.secondsSinceMidnightToDate(scheduledDepartureTime,
                                                         DateUtils.parseDate(String.valueOf(validAt)));
        else
            this.scheduledDepartureTime = null;
        this.recurrence = recurrence;
        this.direction = direction;
        this.siiCode = siiCode;
        this.comCode = comCode;
        this.RFICode = RFICode;
        this.runCategory = runCategory;
    }

    public PassageSchedule(String lineId, String runId, String carrierId, Integer scheduledArrivalTime,
                           Integer scheduledDepartureTime, String siiCode, String comCode, int recurrence,
                           TimeTable.Direction direction, int validAt) {
        this(lineId, runId, carrierId, scheduledArrivalTime, scheduledDepartureTime, siiCode, comCode, direction, validAt);
        this.recurrence = recurrence;
    }

    public PassageSchedule(String lineId, String runId, String carrierId, Integer scheduledArrivalTime,
                           Integer scheduledDepartureTime, String siiCode, String comCode,
                           TimeTable.Direction direction, int validAt, int progressive) {
        this(lineId, runId, carrierId, scheduledArrivalTime, scheduledDepartureTime, siiCode, comCode, direction, validAt);
        this.recurrence = -1;
        this.progressive = progressive;
    }

    public String getLineId() {
        return lineId;
    }

    public String getRunId() {
        return runId;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public DateTime getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public DateTime getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public int getRecurrence() {
        if (recurrence == -1)
            throw new IllegalStateException("You are trying to get recurrence info, but this info was not explicitly set.");
        return recurrence;
    }

    public TimeTable.Direction getDirection() {
        return direction;
    }

    public String getSiiCode() {
        return siiCode;
    }

    public String getComCode() {
        return comCode;
    }

    public String getRFICode() {
        return RFICode;
    }

    public String getRunCategory() {
        return runCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PassageSchedule)) {
            return false;
        }
        PassageSchedule p = (PassageSchedule)o;
        if (!lineId.equals(p.lineId)) {
            return false;
        }
        if (!runId.equals(p.runId)) {
            return false;
        }
        if (!carrierId.equals(p.carrierId)) {
            return false;
        }
        if (scheduledArrivalTime == null && p.scheduledArrivalTime != null) {
            return false;
        }
        if (scheduledArrivalTime != null && p.scheduledArrivalTime == null) {
            return false;
        }
        if (scheduledArrivalTime != null && p.scheduledArrivalTime != null &&
            !scheduledArrivalTime.equals(p.scheduledArrivalTime)) {
            return false;
        }
        if (!scheduledDepartureTime.equals(p.scheduledDepartureTime)) {
            return false;
        }
        if (recurrence != p.recurrence) {
            return false;
        }
        if (direction != p.direction) {
            return false;
        }
        if (!siiCode.equals(p.siiCode)) {
            return false;
        }
        if (!comCode.equals(p.comCode)) {
            return false;
        }
        return true;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    public int getProgressive() { return progressive; }
}