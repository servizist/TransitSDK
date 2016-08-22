package it.sad.sii.transit.sdk.model;

/**
 * Created by ldematte on 9/9/14.
 */
public class TimeTableDataExtended extends TimeTableData {
    protected static final long serialVersionUID = 1L;

    protected String destinationIT;
    protected String destinationDE;
    protected int displayPosition;

    public TimeTableDataExtended() {
    }

    public TimeTableDataExtended(TimeTableData timeTableData, String destinationIT, String destinationDE,
                                 int displayPosition) {
        super(timeTableData.nodeId, timeTableData.waypointId, timeTableData.recurrence,
              timeTableData.recurrencePerWaypoint, timeTableData.progressive, timeTableData.expectedArrival,
              timeTableData.expectedDeparture, timeTableData.direction);
        this.destinationIT = destinationIT;
        this.destinationDE = destinationDE;
        this.displayPosition = displayPosition;
    }

    public String getDestinationIT() {
        return destinationIT;
    }

    public void setDestinationIT(String destinationIT) {
        this.destinationIT = destinationIT;
    }

    public String getDestinationDE() {
        return destinationDE;
    }

    public void setDestinationDE(String destinationDE) {
        this.destinationDE = destinationDE;
    }

    public int getDisplayPosition() {
        return displayPosition;
    }

    public void setDisplayPosition(int displayPosition) {
        this.displayPosition = displayPosition;
    }
}