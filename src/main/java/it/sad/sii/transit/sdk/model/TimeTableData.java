package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by ldematte on 9/9/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableData implements Serializable, Comparable<TimeTableData> {
    private static final long serialVersionUID = 1L;

    protected String nodeId;
    protected String waypointId;
    protected Integer recurrence;
    protected Integer recurrencePerWaypoint;
    protected Integer progressive;
    protected Integer expectedArrival;
    protected Integer expectedDeparture;
    protected TimeTable.Direction direction;

    public TimeTableData() {
    }

    public TimeTableData(String nodeId, String waypointId, Integer recurrence, Integer recurrencePerWaypoint,
                         Integer progressive, Integer expectedArrival, Integer expectedDeparture,
                         TimeTable.Direction direction) {
        setNodeId(nodeId);
        setWaypointId(waypointId);
        setRecurrence(recurrence);
        setRecurrencePerWaypoint(recurrencePerWaypoint);
        setProgressive(progressive);
        setExpectedArrival(expectedArrival);
        setExpectedDeparture(expectedDeparture);
        setDirection(direction);
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getWaypointId() {
        return waypointId;
    }

    public void setWaypointId(String waypointId) {
        this.waypointId = waypointId;
    }

    public Integer getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Integer recurrence) {
        this.recurrence = recurrence;
    }

    public Integer getRecurrencePerWaypoint() {
        return recurrencePerWaypoint;
    }

    public void setRecurrencePerWaypoint(Integer recurrencePerWaypoint) {
        this.recurrencePerWaypoint = recurrencePerWaypoint;
    }

    public Integer getProgressive() {
        return progressive;
    }

    public void setProgressive(Integer progressive) {
        this.progressive = progressive;
    }

    public Integer getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(Integer expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    public Integer getExpectedDeparture() {
        return expectedDeparture;
    }

    public void setExpectedDeparture(Integer expectedDeparture) {
        this.expectedDeparture = expectedDeparture;
    }

    public TimeTable.Direction getDirection() {
        return direction;
    }

    public void setDirection(TimeTable.Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");

        StringBuffer print = new StringBuffer();
        print.append("\n ********************************* \n");
        print.append(" Node id:            ").append(nodeId).append(separator);
        print.append(" Way point id:       ").append(waypointId).append(separator);
        print.append(" Recurrence:         ").append(recurrence).append(separator);
        print.append(" Recurrence per wp:  ").append(recurrencePerWaypoint).append(separator);
        print.append(" Progressive:        ").append(progressive).append(separator);
        print.append(" Expected arrival:   ").append(expectedArrival).append(separator);
        print.append(" Expected departure: ").append(expectedDeparture).append(separator);
        print.append(" Direction:          ").append(direction).append(separator);
        print.append("\n ********************************* \n");

        return print.toString();
    }

    @Override
    public int compareTo(TimeTableData timeTableData) {
        // first, sort by direction (first "A", then "R"), then by departure time
        if (direction == null)
            return -1;
        if (timeTableData.direction == null)
            return 1;

        int ret = direction.compareTo(timeTableData.direction);
        if (ret != 0)
            return ret;

        // expected departure null -> last waypoint
        if (expectedDeparture == null && timeTableData.expectedDeparture != null)
            return 1;
        if (timeTableData.expectedDeparture == null && expectedDeparture != null)
            return -1;
        if (expectedArrival == null && timeTableData.expectedArrival != null)
            return -1;
        if (timeTableData.expectedArrival == null && expectedArrival != null)
            return 1;

        if (expectedDeparture != null && timeTableData.expectedDeparture != null &&
            expectedDeparture < timeTableData.expectedDeparture)
            return -1;
        if (expectedDeparture != null && timeTableData.expectedDeparture != null &&
            expectedDeparture > timeTableData.expectedDeparture)
            return 1;

        if (progressive < timeTableData.progressive)
            return -1;
        if (timeTableData.progressive < progressive)
            return 1;

        return 0;
    }
}