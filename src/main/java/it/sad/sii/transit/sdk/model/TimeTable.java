package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * Created by ldematte on 9/9/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTable implements Serializable, Comparable<TimeTable> {
    protected static final long serialVersionUID = 1L;

    public enum Direction {
        OUTWARDS("A"),
        RETURN("R");

        private final String name;

        private Direction(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        // DO NOT REMOVE! Needed for serialization of object with jackson...
        @JsonValue
        public String toValue(){
            return toString();
        }

        public static Direction fromString(String s) {
            switch (s) {
                case "A":
                case "OUTWARDS":
                case "As":
                    return OUTWARDS;

                case "R":
                case "RETURN":
                case "Di":
                    return RETURN;

                default:
                    throw new IllegalArgumentException("Unknown direction " + s);
            }
        }
    }

    protected String id;
    protected String carrierId;
    protected String lineId;
    protected String runId;
    protected String commercialCode;
    protected Integer validFrom;
    protected Integer validTo;
    protected List<? extends TimeTableData> entries;
    protected List<? extends TimeTableCalendar> periods;

    public TimeTable() {
    }

    public TimeTable(String carrierId, String lineId, String runId, String commercialCode, Integer validFrom, Integer validTo) {
        setCarrierId(carrierId);
        setLineId(lineId);
        setRunId(runId);
        setCommercialCode(commercialCode);
        setValidFrom(validFrom);
        setValidTo(validTo);
    }

    public TimeTable(String carrierId, String lineId, String runId, String commercialCode, Integer validFrom, Integer validTo,
                     List<TimeTableData> ttd, List<TimeTableCalendar> ttc) {
        this(carrierId, lineId, runId, commercialCode, validFrom, validTo);
        setEntries(ttd);
        setPeriods(ttc);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public Integer getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Integer validFrom) {
        this.validFrom = validFrom;
    }

    public Integer getValidTo() {
        return validTo;
    }

    public void setValidTo(Integer validTo) {
        this.validTo = validTo;
    }

    public String getCommercialCode() {
        return commercialCode;
    }

    public void setCommercialCode(String commercialCode) {
        this.commercialCode = commercialCode;
    }

    public void setEntries(List<? extends TimeTableData> entries) {
        this.entries = entries;
    }

    public void setPeriods(List<? extends TimeTableCalendar> periods) {
        this.periods = periods;
    }

    public List<? extends TimeTableData> getEntries() {
        return entries;
    }

    public List<? extends TimeTableCalendar> getPeriods() {
        return periods;
    }

    @JsonIgnore
    public List<? extends TimeTableData> getEntriesOrdered() {
        if (entries == null)
            return null;
        return sort(entries);
    }

    @JsonIgnore
    public List<? extends TimeTableCalendar> getPeriodsOrdered() {
        if (periods == null)
            return null;
        return sort(periods);
    }

    @Override
    public int compareTo(TimeTable timeTable) {
        // sort by validFrom
        return validFrom < timeTable.validFrom ? -1 : (validFrom > timeTable.validFrom ? 1 : 0);
    }

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");

        StringBuffer print = new StringBuffer();
        print.append("\n ********************************* \n");
        print.append(" Id :        ").append(getId()).append(separator);
        print.append(" Carrier id: ").append(carrierId).append(separator);
        print.append(" Line id:    ").append(lineId).append(separator);
        print.append(" Run id:     ").append(runId).append(separator);
        print.append(" Valid from: ").append(validFrom).append(separator);
        print.append(" Valid to:   ").append(validTo).append(separator);
        print.append(" Entries:    ").append(entries).append(separator);
        print.append(" Periods:    ").append(periods).append(separator);
        print.append("\n ********************************* \n");

        return print.toString();
    }

    protected List sort(List list) {
        // DO NOT CHANGE THESE LINES TO Collections.sort() SINCE THERE IS A PROBLEM WITH JAVA 8!!!!
        Object[] array = list.toArray();
        Arrays.sort(array);
        return Arrays.asList(array);
    }
}