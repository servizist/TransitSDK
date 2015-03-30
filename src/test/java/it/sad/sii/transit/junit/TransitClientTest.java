package it.sad.sii.transit.junit;

import it.sad.sii.transit.client.TransitClient;
import it.sad.sii.transit.model.Passage;
import it.sad.sii.transit.model.Run;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * This class tests the TransitClient
 */
public class TransitClientTest {

    private static TransitClient client;

    private static final int AMOUNT = 20;
    private static final int WAYPOINT_ID = 9422;
    private static final int LINE_ID = 1931;
    private static final int RUN_ID = 3241;
    private static final LocalTime SCHEDULED_TIME = new LocalTime(11, 12, 0);
    private static final LocalTime FROM_TIME = new LocalTime(0, 0, 0);
    private static final LocalTime TO_TIME = new LocalTime(23, 59, 59);
    private static final LocalDate VALID_AT = new LocalDate();
    private static final LocalDate VALID_FROM = new LocalDate().minusDays(10);
    private static final LocalDate VALID_TO = new LocalDate().plusDays(10);

    @BeforeClass
    public static void setUp() {
        client = new TransitClient();
    }

    @Test
    public void testGetNextWaypointPassagesWithAmount() throws Exception {
        List<Passage> passages = client.getNextPassages(WAYPOINT_ID, AMOUNT);
        Assert.assertTrue(passages.size() > 0);
        Assert.assertTrue(passages.size() <= AMOUNT);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Next passage has to be in the future or near past 
            // (passage is regarded as next if it is within a certain interval in the past)
            Assert.assertTrue(passage.getEstimatedTime().isAfter(new DateTime().minusMinutes(10)));
        }
    }

    @Test
    public void testGetNextWaypointPassagesWithoutAmount() throws Exception {
        List<Passage> passages = client.getNextPassages(WAYPOINT_ID);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Next passage has to be in the future or near past
            // (passage is regarded as next if it is within a certain interval in the past)
            Assert.assertTrue(passage.getEstimatedTime().isAfter(new DateTime().minusMinutes(10)));
        }
    }

    @Test
    public void testGetPassagesByLineAndRunAndScheduledTimeAndValidAt() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, RUN_ID, SCHEDULED_TIME, VALID_AT);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled at the specified time
            Assert.assertEquals(SCHEDULED_TIME, passage.getScheduledTime().toLocalTime());
            // Passage has to be scheduled on the specified day
            Assert.assertEquals(VALID_AT, passage.getScheduledTime().toLocalDate());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
            // Passage has to have the specified run id
            Assert.assertEquals(RUN_ID, passage.getRunId());
        }
    }

    @Test
    public void testGetPassagesByLine() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
        }
    }


    @Test
    public void testGetPassagesByLineAndRunAndScheduledTime() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, RUN_ID, SCHEDULED_TIME);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled at the specified time
            Assert.assertEquals(SCHEDULED_TIME, passage.getScheduledTime().toLocalTime());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
            // Passage has to have the specified run id
            Assert.assertEquals(RUN_ID, passage.getRunId());
        }
    }

    @Test
    public void testGetPassagesByLineAndRunAndValidAt() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, RUN_ID, VALID_AT);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled on the specified day
            Assert.assertEquals(VALID_AT, passage.getScheduledTime().toLocalDate());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
            // Passage has to have the specified run id
            Assert.assertEquals(RUN_ID, passage.getRunId());
        }
    }

    @Test
    public void testGetPassagesByLineAndScheduledTimeAndValidAt() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, SCHEDULED_TIME, VALID_AT);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled at the specified time
            Assert.assertEquals(SCHEDULED_TIME, passage.getScheduledTime().toLocalTime());
            // Passage has to be scheduled on the specified day
            Assert.assertEquals(VALID_AT, passage.getScheduledTime().toLocalDate());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
        }
    }

    @Test
    public void testGetPassagesByLineAndRun() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, RUN_ID);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
            // Passage has to have the specified run id
            Assert.assertEquals(RUN_ID, passage.getRunId());
        }
    }

    @Test
    public void testGetPassagesByLineAndScheduledTime() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, SCHEDULED_TIME);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled at the specified time
            Assert.assertEquals(SCHEDULED_TIME, passage.getScheduledTime().toLocalTime());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
        }
    }

    @Test
    public void testGetPassagesByLineAndValidAt() throws Exception {
        List<Passage> passages = client.getPassages(WAYPOINT_ID, LINE_ID, VALID_AT);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled on the specified day
            Assert.assertEquals(VALID_AT, passage.getScheduledTime().toLocalDate());
            // Passage has to have the specified line id
            Assert.assertEquals(LINE_ID, passage.getLineId());
        }
    }

    @Test
    public void testGetPassagesForDayByValidAtAndFromTimeAndToTime() throws Exception {
        List<Passage> passages = client.getAllPassagesForDay(WAYPOINT_ID, VALID_AT, FROM_TIME, TO_TIME);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be within specified time interval
            Assert.assertFalse(passage.getScheduledTime().toLocalTime().isBefore(FROM_TIME));
            Assert.assertFalse(passage.getScheduledTime().toLocalTime().isAfter(TO_TIME));
            // Passage has to be scheduled on the specified day
            Assert.assertEquals(VALID_AT, passage.getScheduledTime().toLocalDate());
        }
    }

    @Test
    public void testGetPassagesForDayByValidAt() throws Exception {
        List<Passage> passages = client.getAllPassagesForDay(WAYPOINT_ID, VALID_AT);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled on the specified day
            Assert.assertEquals(VALID_AT, passage.getScheduledTime().toLocalDate());
        }
    }

    @Test
    public void testGetPassagesForDayByFromTimeAndToTime() throws Exception {
        List<Passage> passages = client.getAllPassagesForDay(WAYPOINT_ID, FROM_TIME, TO_TIME);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be scheduled within specified time interval
            Assert.assertFalse(passage.getScheduledTime().toLocalTime().isBefore(FROM_TIME));
            Assert.assertFalse(passage.getScheduledTime().toLocalTime().isAfter(TO_TIME));
        }
    }

    @Test
    public void testGetPassagesForIntervalByValidFromAndValidTo() throws Exception {
        List<Passage> passages = client.getAllPassagesForInterval(WAYPOINT_ID, VALID_FROM, VALID_TO);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be within specified interval of days
            Assert.assertFalse(passage.getScheduledTime().toLocalDate().isBefore(VALID_FROM));
            Assert.assertFalse(passage.getScheduledTime().toLocalDate().isAfter(VALID_TO));
        }
    }

    @Test
    public void testGetPassagesForInterval() throws Exception {
        List<Passage> passages = client.getAllPassagesForInterval(WAYPOINT_ID);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be today
            Assert.assertFalse(passage.getScheduledTime().toLocalDate().isBefore(new LocalDate()));
            Assert.assertFalse(passage.getScheduledTime().toLocalDate().isAfter(new LocalDate()));
        }
    }

    @Test
    public void testGetRuns() throws Exception {
        List<Run> runs = client.getLineTimesRuns(LINE_ID);
        Assert.assertTrue(runs.size() > 0);
    }
}