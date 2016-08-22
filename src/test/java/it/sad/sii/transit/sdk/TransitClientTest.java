package it.sad.sii.transit.sdk;

import it.sad.sii.transit.sdk.client.TransitClient;
import it.sad.sii.transit.sdk.model.Passage;
import it.sad.sii.transit.sdk.model.Run;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * This class tests the TransitClient
 */
public class TransitClientTest {

    private static TransitClient client;

    private static final int AMOUNT = 20;
    private static final String WAYPOINT_ID = "9422";
    private static final String LINE_ID = "1931";
    private static final String RUN_ID = "3241";
    private static final LocalTime SCHEDULED_TIME = new LocalTime(11, 12, 0);
    private static final DateTime TO_TIME = new DateTime().withTime(23, 59, 59, 999);
    private static final LocalDate VALID_AT = new LocalDate();
    private static final LocalDate VALID_FROM = new LocalDate().minusDays(2);
    private static final LocalDate VALID_TO = new LocalDate().plusDays(2);

    @Before
    public void setUp() {
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        String baseUri = System.getProperty("baseUri");

        if (username != null && !username.isEmpty() &&
            password != null && !password.isEmpty() &&
            baseUri != null && !baseUri.isEmpty()) {
            System.out.printf("Using user:%s baseUri:%s\n", username, baseUri);
            client = new TransitClient(baseUri, username, password);
        }
        else {
            client = new TransitClient();
        }
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
    public void testGetAllPassagesByValidFromAndValidTo() throws Exception {
        List<Passage> passages = client.getPassagesUntil(WAYPOINT_ID, TO_TIME);
        Assert.assertTrue(passages.size() > 0);
        for (Passage passage : passages) {
            // Passage has to be on specified waypoint
            Assert.assertEquals(WAYPOINT_ID, passage.getWaypointId());
            // Passage has to be within specified time interval
            Assert.assertFalse(passage.getScheduledTime().isAfter(TO_TIME));
        }
    }

    @Test
    public void testGetRuns() throws Exception {
        List<Run> runs = client.getLineTimesRuns(LINE_ID);
        Assert.assertTrue(runs.size() > 0);
    }
}