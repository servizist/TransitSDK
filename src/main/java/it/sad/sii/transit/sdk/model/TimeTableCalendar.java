package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by ldematte on 9/9/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableCalendar implements Serializable, Comparable<TimeTableCalendar> {
    private static final long serialVersionUID = 1L;

    protected Integer runningFrom;
    protected Integer runningTo;

    public TimeTableCalendar() {
    }

    public TimeTableCalendar(Integer runningFrom, Integer runningTo) {
        this.runningFrom = runningFrom;
        this.runningTo = runningTo;
    }

    public Integer getRunningFrom() {
        return runningFrom;
    }

    public void setRunningFrom(Integer runningFrom) {
        this.runningFrom = runningFrom;
    }

    public Integer getRunningTo() {
        return runningTo;
    }

    public void setRunningTo(Integer runningTo) {
        this.runningTo = runningTo;
    }

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");

        StringBuffer print = new StringBuffer();
        print.append("\n ********************************* \n");
        print.append(" Running from:  ").append(runningFrom).append(separator);
        print.append(" Running to:    ").append(runningTo).append(separator);
        print.append("\n ********************************* \n");

        return print.toString();
    }

    @Override
    public int compareTo(TimeTableCalendar timeTableData) {
        // sort by runningFrom
        return runningFrom < timeTableData.runningFrom ? -1 : (runningFrom > timeTableData.runningFrom ? 1 : 0);
    }
}