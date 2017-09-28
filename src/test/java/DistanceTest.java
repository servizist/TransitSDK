
import com.google.gson.Gson;
import it.bz.sii.common.Pair;
import it.sad.sii.transit.sdk.model.LineEdge;
import it.sad.sii.transit.sdk.model.Location;
import it.sad.sii.transit.sdk.model.Waypoint;
import it.sad.sii.transit.sdk.utils.DistanceUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldematte on 10/6/14.
 */
public class DistanceTest {

    @Test
    public void test() {
        Location loc = Location.fromUTM(702631, 5177682);
        System.out.println(loc.getLatitude());
        System.out.println(loc.getLongitude());
    }

    //      |      .C
    //      |  . A
    //      |D   . B
    // --------------
    // A-C: plus sign
    // A-D: neg sign
    @Test
    public void getPlane() {

        Waypoint A = new Waypoint();
        Waypoint B = new Waypoint();

        A.x(1);
        A.y(2);
        B.x(2);
        B.y(1);


        Pair<Location, Location> planeLine = DistanceUtils.planeFromSegment(A, B);

        Location C = planeLine.first;
        Location D = planeLine.second;

        Assert.assertEquals(2, C.x(), 1e-6);
        Assert.assertEquals(3, C.y(), 1e-6);
        Assert.assertEquals(0, D.x(), 1e-6);
        Assert.assertEquals(1, D.y(), 1e-6);
    }

    //      |        .C
    //      |
    //      |
    //      |  . A
    //      |    . P
    // --------------
    // .D   |       . B (4, -1)
    // A-C: plus sign
    // A-D: neg sign
    // P is "After" the plane defined by CD, the line perpendicular to AB passing
    // through A
    @Test
    public void planePosition() {

        Waypoint A = new Waypoint();
        Waypoint B = new Waypoint();
        Waypoint P = new Waypoint();

        A.x(1);
        A.y(2);
        B.x(4);
        B.y(-1);
        P.x(2);
        P.y(1);


        Pair<Location, Location> planeLine = DistanceUtils.planeFromSegment(A, B);

        Location C = planeLine.first;
        Location D = planeLine.second;

        Assert.assertEquals(4, C.x(), 1e-6);
        Assert.assertEquals(5, C.y(), 1e-6);
        Assert.assertEquals(-2, D.x(), 1e-6);
        Assert.assertEquals(-1, D.y(), 1e-6);


        DistanceUtils.PointPosition position = DistanceUtils.beforeOrAfter(P, C, D);
        Assert.assertEquals(DistanceUtils.PointPosition.After, position);

        P.x(0);
        P.y(3);
        position = DistanceUtils.beforeOrAfter(P, C, D);
        Assert.assertEquals(DistanceUtils.PointPosition.Before, position);

        P.x(3);
        P.y(4);
        position = DistanceUtils.beforeOrAfter(P, C, D);
        Assert.assertEquals(DistanceUtils.PointPosition.OnLine, position);
        P.x(3);
        P.y(4.0000001);
        position = DistanceUtils.beforeOrAfter(P, C, D);
        Assert.assertEquals(DistanceUtils.PointPosition.OnLine, position);

        P.x(4);
        P.y(-100);
        position = DistanceUtils.beforeOrAfter(P, C, D);
        Assert.assertEquals(DistanceUtils.PointPosition.After, position);

        P.x(-100);
        P.y(4);
        position = DistanceUtils.beforeOrAfter(P, C, D);
        Assert.assertEquals(DistanceUtils.PointPosition.Before, position);
    }

    @Test
    public void pointPositionWrtLine() {

        Waypoint A = new Waypoint();
        Waypoint B = new Waypoint();
        Waypoint P = new Waypoint();

        A.x(1);
        A.y(2);
        B.x(4);
        B.y(-1);

        // exactly on line between the two points
        P.x(2);
        P.y(1);
        DistanceUtils.PositionOnLine position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.Between, position);

        // between the two points but too far away
        P.x(4);
        P.y(1);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.Distant, position);

        // before start of line (A)
        P.x(0);
        P.y(3);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.Out, position);

        // close to start of line (A)
        P.x(1.001);
        P.y(2.001);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.OnStart, position);

        // close to start of line (A)
        P.x(1);
        P.y(2.0000001);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.OnStart, position);

        // too far away from line
        P.x(4);
        P.y(-100);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.Out, position);

        // too far away from line
        P.x(-100);
        P.y(4);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.Out, position);

        // close to end of line (B)
        P.x(4.001);
        P.y(-0.999);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.OnEnd, position);

        // too far away from line
        P.x(4.5);
        P.y(-0.9);
        position = DistanceUtils.isBetween(P, A, B);
        Assert.assertEquals(DistanceUtils.PositionOnLine.Out, position);
    }


    @Test
    public void distanceFromLine() {

        Waypoint A = new Waypoint();
        Waypoint B = new Waypoint();
        Waypoint P = new Waypoint();

        A.x(1);
        A.y(2);
        B.x(4);
        B.y(-1);
        P.x(2);
        P.y(1);

        float distance = (float)DistanceUtils.distance(P, A, B);
        Assert.assertEquals(0.0, distance, 1e-9);

        P.x(4);
        P.y(1);

        distance = (float)DistanceUtils.distance(P, A, B);
        Assert.assertEquals(Math.sqrt(2), distance, 1e-6);
    }

    @Test
    public void distancePercentageAlongLine() {

        Waypoint A = new Waypoint();
        Waypoint B = new Waypoint();
        Waypoint P = new Waypoint();

        A.x(1);
        A.y(2);
        B.x(4);
        B.y(-1);
        P.x(2);
        P.y(1);

        float distance = (float)DistanceUtils.percentageAlongLine(P, A, B);
        Assert.assertEquals(1.0 / 3.0, distance, 1e-6);

        P.x(4);
        P.y(1);
        distance = (float)DistanceUtils.percentageAlongLine(P, A, B);
        Assert.assertEquals(2.0 / 3.0, distance, 1e-6);
    }


    @Test
    public void closestEdge() {
        List<LineEdge> edges = new ArrayList<LineEdge>();
        edges.add(new LineEdge("1", "2", 0, 0, true));
        edges.add(new LineEdge("2", "3", 0, 0, true));
        edges.add(new LineEdge("3", "4", 0, 0, true));

        Map<String, Waypoint> waypoints = new HashMap<String, Waypoint>();
        waypoints.put("1", new Waypoint(11.091721, 46.796184, "1", "1"));
        waypoints.put("2", new Waypoint(11.092907, 46.796349, "2", "2"));
        waypoints.put("3", new Waypoint(11.091796, 46.795916, "3", "3"));
        waypoints.put("4", new Waypoint(11.090343, 46.795600, "4", "4"));

        List<Waypoint> positions = new ArrayList<Waypoint>();
        positions.add(new Waypoint(11.091451, 46.796072, "1", "1"));
        positions.add(new Waypoint(11.091721, 46.796184, "2", "2"));
        positions.add(new Waypoint(11.091721, 46.796084, "3", "3"));
        positions.add(new Waypoint(11.092631, 46.796512, "4", "4"));
        positions.add(new Waypoint(11.092796, 46.796585, "5", "5"));
        positions.add(new Waypoint(11.093559, 46.796828, "6", "6"));
        positions.add(new Waypoint(11.093007, 46.796417, "7", "7"));
        positions.add(new Waypoint(11.092907, 46.796349, "8", "8"));
        positions.add(new Waypoint(11.092739, 46.796264, "9", "9"));
        positions.add(new Waypoint(11.092363, 46.796046, "10", "10"));
        positions.add(new Waypoint(11.091796, 46.795916, "11", "11"));
        positions.add(new Waypoint(11.091210, 46.795785, "12", "12"));
        positions.add(new Waypoint(11.089933, 46.795712, "13", "13"));

        Waypoint p;
        int edge = 0;
        int tmp;
        for (int i = 0; i < positions.size(); i++) {
            p = positions.get(i);
            tmp = DistanceUtils.closestEdge(edge, p, edges, waypoints);
            if (tmp != -1)
                edge = tmp;
            if (i < 8)
                Assert.assertEquals(edge, 0);
            else if (i < 11)
                Assert.assertEquals(edge, 1);
            else if (i < 12)
                Assert.assertEquals(edge, 2);
            else
                Assert.assertEquals(edge, 2);
        }
    }

    @Test
    public void closestEdgeRealScenario() {
        String waypoints =
                "[{\"numberOfShownPassages\":0,\"descriptionDe\":\"Bahnhof 1\",\"latitude\":46.496534999112590," +
                "\"longitude\":11" +
                ".358056524402151," +
                "\"id\":5352,\"nodeId\":4001,\"descriptionIt\":\"Stazione 1\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Bahnhof 3\"," +
                "\"latitude\":46.497071332263800,\"longitude\":11.357079884267641,\"id\":5106,\"ipAddress\":\"172.24" +
                ".50.5\"," +
                "\"displayIdentifier\":\"BZBST005  \",\"nodeId\":4001,\"descriptionIt\":\"Stazione 3\"}," +
                "{\"numberOfShownPassages\":4," +
                "\"descriptionDe\":\"Waltherplatz\",\"latitude\":46.497940839547030,\"longitude\":11.354198197927058," +
                "\"id\":5029," +
                "\"ipAddress\":\"10.12.12.150\",\"displayIdentifier\":\"BZBST300  \",\"nodeId\":4001," +
                "\"descriptionIt\":\"Piazza Walter\"}," +
                "{\"numberOfShownPassages\":6,\"descriptionDe\":\"Dominikanerplatz\",\"latitude\":46.497861017607470," +
                "\"longitude\":11" +
                ".351523224038434,\"id\":5027,\"ipAddress\":\"172.24.50.23\",\"displayIdentifier\":\"BZBST023  \"," +
                "\"nodeId\":4001," +
                "\"descriptionIt\":\"Piazza Domenicani\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Universitätplatz\",\"latitude\":46" +
                ".498956960604460,\"longitude\":11.350749433901875,\"id\":5025,\"ipAddress\":\"172.24.50.20\"," +
                "\"displayIdentifier\":\"BZBST020  " +
                "\",\"nodeId\":4001,\"descriptionIt\":\"Piazza Università\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Sparkassenstr.\"," +
                "\"latitude\":46.499372856094910,\"longitude\":11.349346788492818,\"id\":5032,\"ipAddress\":\"172.24" +
                ".50.18\"," +
                "\"displayIdentifier\":\"BZBST018  \",\"nodeId\":4001,\"descriptionIt\":\"via Cassa di Risparmio\"}," +
                "{\"numberOfShownPassages\":0," +
                "\"descriptionDe\":\"Siegesplatz 1\",\"latitude\":46.500299084090350,\"longitude\":11" +
                ".344134514311100,\"id\":5022," +
                "\"nodeId\":4001,\"descriptionIt\":\"Piazza Vittoria 1\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Cesare-Battisti-Str" +
                ".\",\"latitude\":46.500539546404150,\"longitude\":11.340313250317447,\"id\":5020,\"ipAddress\":\"172" +
                ".24.50.2\"," +
                "\"displayIdentifier\":\"BZBST002  \",\"nodeId\":4001,\"descriptionIt\":\"via Cesare Battisti\"}," +
                "{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Gericht\",\"latitude\":46.497116883418160,\"longitude\":11.340009808367348," +
                "\"id\":5018,\"ipAddress\":\"172" +
                ".24.50.9\",\"displayIdentifier\":\"BZBST009  \",\"nodeId\":4001,\"descriptionIt\":\"Tribunale\"}]";
        String positions =
                "11.3518054,46.49798871 11.3518054,46.49798871 11.35174751,46.49804123 11.35174751,46.49804123 11" +
                ".35165855,46.49806833 " +
                "11.35165855,46.49806833 11.35153507,46.49802319 11.35153507,46.49802319 11.35132997,46.49795278 11" +
                ".35132997,46.49795278 " +
                "11.35126802,46.49792921 11.35126802,46.49792921 11.35120062,46.49795251 11.35120062,46.49795251 11" +
                ".35104231,46.49808023 " +
                "11.35104231,46.49808023 11.3509596,46.4981473 11.3509596,46.4981473 11.35086863,46.49826592 11" +
                ".35086863,46.49826592 " +
                "11.35089247,46.49835092 11.35089247,46.49835092 11.35088053,46.49843246 11.35088053,46.49843246 11" +
                ".35088338,46.498478 " +
                "11.35088338,46.498478 11.35089594,46.49852325 11.35089594,46.49852325 11.35090944,46.49858209 11" +
                ".35090944,46.49858209 " +
                "11.35089664,46.49864375 11.35089664,46.49864375 11.35090185,46.49870481 11.35090185,46.49870481 11" +
                ".35095431,46.49879094 " +
                "11.35095431,46.49879094 11.35088574,46.49890964 11.35088574,46.49890964 11.3507967,46.49893783 11" +
                ".3507967,46.49893783 " +
                "11.35069568,46.49895046 11.35069568,46.49895046 11.35058799,46.49897958 11.35058799,46.49897958 11" +
                ".35035096,46.49901081 " +
                "11.35035096,46.49901081 11.3502184,46.4991415 11.3502184,46.4991415 11.35010238,46.4991489 11" +
                ".35010238,46.4991489 " +
                "11.35000374,46.49911738 11.35000374,46.49911738 11.34994596,46.49908099 11.34994596,46.49908099 11" +
                ".34981224,46.49904981 " +
                "11.34981224,46.49904981 11.34970042,46.49907291 11.34970042,46.49907291 11.34961106,46.49912803 11" +
                ".34961106,46.49912803 " +
                "11.3495584,46.49915034 11.3495584,46.49915034 11.3493627,46.49912581 11.3493627,46.49912581 11" +
                ".34920868,46.49914379 " +
                "11.34920868,46.49914379 11.34911676,46.49919933 11.34911676,46.49919933 11.34903507,46.49928689 11" +
                ".34903507,46.49928689 " +
                "11.34899172,46.49936505 11.34899172,46.49936505 11.34899398,46.49944945 11.34899398,46.49944945 11" +
                ".34907126,46.49953523 " +
                "11.34907126,46.49953523 11.34912898,46.4996073 11.34912898,46.4996073 11.34915439,46.49967484 11" +
                ".34915439,46.49967484 " +
                "11.3491813,46.49980253 11.3491813,46.49980253 11.34913471,46.49987606 11.34913471,46.49987606 11" +
                ".34916331,46.49998922 " +
                "11.34916331,46.49998922 11.34923124,46.50002692 11.34923124,46.50002692 11.34903568,46.50003423 11" +
                ".34903568,46.50003423 " +
                "11.34893175,46.50006969 11.34893175,46.50006969 11.34888304,46.50013499 11.34888304,46.50013499 11" +
                ".34881761,46.50016543 " +
                "11.34881761,46.50016543 11.34872506,46.50016399 11.34872506,46.50016399 11.34863116,46.50016502 11" +
                ".34863116,46.50016502 " +
                "11.348537,46.50013123 11.348537,46.50013123 11.348537,46.50013123 11.348537,46.50013123 11.3475923," +
                "46.50013254 " +
                "11.3475923,46.50013254 11.348172,46.4999561 11.348172,46.4999561 11.3477252,46.5000168 11.3477252,46" +
                ".5000168 " +
                "11.3465044,46.5001068 11.3465044,46.5001068 11.34464968,46.49997028 11.34464968,46.49997028 11" +
                ".34467505,46.50008334 " +
                "11.34467505,46.50008334 11.34462862,46.500123 11.34462862,46.500123 11.3445844,46.50015913 11" +
                ".3445844,46.50015913 " +
                "11.34447984,46.50031193 11.34447984,46.50031193 11.34441775,46.50033685 11.34441775,46.50033685 11" +
                ".34434213,46.50034482 " +
                "11.34434213,46.50034482 11.34425888,46.50035878 11.34425888,46.50035878 11.34415706,46.50033463 11" +
                ".34415706,46.50033463 " +
                "11.34406278,46.50029096 11.34406278,46.50029096 11.34391605,46.50024013 11.34391605,46.50024013 11" +
                ".34377644,46.50021669 " +
                "11.34377644,46.50021669 11.34367084,46.50023083 11.34367084,46.50023083 11.34356712,46.50030497 11" +
                ".34356712,46.50030497 " +
                "11.3434574,46.50033719 11.3434574,46.50033719 11.34334049,46.50033419 11.34334049,46.50033419 11" +
                ".34323799,46.50035396 " +
                "11.34323799,46.50035396 11.34308078,46.50023145 11.34308078,46.50023145 11.34294824,46.50027606 11" +
                ".34294824,46.50027606 " +
                "11.34287437,46.50028342 11.34287437,46.50028342 11.34266648,46.50030349 11.34266648,46.50030349 11" +
                ".34249549,46.50045087 " +
                "11.34249549,46.50045087 11.34242688,46.50052555 11.34242688,46.50052555 11.34237689,46.50059471 11" +
                ".34237689,46.50059471 " +
                "11.34228599,46.50059924 11.34228599,46.50059924 11.34216567,46.50056972 11.34216567,46.50056972 11" +
                ".34200052,46.50043181 " +
                "11.34200052,46.50043181 11.34189428,46.50037402 11.34189428,46.50037402 11.34178949,46.50036815 11" +
                ".34178949,46.50036815 " +
                "11.34169039,46.50037285 11.34169039,46.50037285 11.34156578,46.50037334 11.34156578,46.50037334 11" +
                ".3413924,46.50035464 " +
                "11.3413924,46.50035464 11.34125342,46.50044032 11.34125342,46.50044032 11.3410887,46.50041952 11" +
                ".3410887,46.50041952 " +
                "11.34093819,46.50030769 11.34093819,46.50030769 11.34091081,46.50016444 11.34091081,46.50016444 11" +
                ".34090082,46.5001068 " +
                "11.34090082,46.5001068 11.34082688,46.50014781 11.34082688,46.50014781 11.34072648,46.50017126 11" +
                ".34072648,46.50017126 " +
                "11.34064482,46.50015061 11.34064482,46.50015061 11.34054682,46.50010482 11.34054682,46.50010482 11" +
                ".34047443,46.50008773 " +
                "11.34047443,46.50008773 11.34033258,46.5000219 11.34033258,46.5000219 11.34020472,46.49998703 11" +
                ".34020472,46.49998703 " +
                "11.34014958,46.49999654 11.34014958,46.49999654 11.34010995,46.49997618 11.34010995,46.49997618";

        execute(waypoints, positions);
    }

    @Test
    public void closestEdgeRealScenario2() {
        String waypoints =
                "[{\"numberOfShownPassages\":0,\"descriptionDe\":\"Bahnhof 1\",\"latitude\":46.496534999112590," +
                "\"longitude\":11" +
                ".358056524402151," +
                "\"id\":5352,\"nodeId\":4001,\"descriptionIt\":\"Stazione 1\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Bahnhof 3\"," +
                "\"latitude\":46.497071332263800,\"longitude\":11.357079884267641,\"id\":5106,\"ipAddress\":\"172.24" +
                ".50.5\"," +
                "\"displayIdentifier\":\"BZBST005  \",\"nodeId\":4001,\"descriptionIt\":\"Stazione 3\"}," +
                "{\"numberOfShownPassages\":4," +
                "\"descriptionDe\":\"Waltherplatz\",\"latitude\":46.497940839547030,\"longitude\":11.354198197927058," +
                "\"id\":5029," +
                "\"ipAddress\":\"10.12.12.150\",\"displayIdentifier\":\"BZBST300  \",\"nodeId\":4001," +
                "\"descriptionIt\":\"Piazza Walter\"}," +
                "{\"numberOfShownPassages\":6,\"descriptionDe\":\"Dominikanerplatz\",\"latitude\":46.497861017607470," +
                "\"longitude\":11" +
                ".351523224038434,\"id\":5027,\"ipAddress\":\"172.24.50.23\",\"displayIdentifier\":\"BZBST023  \"," +
                "\"nodeId\":4001," +
                "\"descriptionIt\":\"Piazza Domenicani\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Universitätplatz\",\"latitude\":46" +
                ".498956960604460,\"longitude\":11.350749433901875,\"id\":5025,\"ipAddress\":\"172.24.50.20\"," +
                "\"displayIdentifier\":\"BZBST020  " +
                "\",\"nodeId\":4001,\"descriptionIt\":\"Piazza Università\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Sparkassenstr.\"," +
                "\"latitude\":46.499372856094910,\"longitude\":11.349346788492818,\"id\":5032,\"ipAddress\":\"172.24" +
                ".50.18\"," +
                "\"displayIdentifier\":\"BZBST018  \",\"nodeId\":4001,\"descriptionIt\":\"via Cassa di Risparmio\"}," +
                "{\"numberOfShownPassages\":0," +
                "\"descriptionDe\":\"Siegesplatz 1\",\"latitude\":46.500299084090350,\"longitude\":11" +
                ".344134514311100,\"id\":5022," +
                "\"nodeId\":4001,\"descriptionIt\":\"Piazza Vittoria 1\"},{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Cesare-Battisti-Str" +
                ".\",\"latitude\":46.500539546404150,\"longitude\":11.340313250317447,\"id\":5020,\"ipAddress\":\"172" +
                ".24.50.2\"," +
                "\"displayIdentifier\":\"BZBST002  \",\"nodeId\":4001,\"descriptionIt\":\"via Cesare Battisti\"}," +
                "{\"numberOfShownPassages\":6," +
                "\"descriptionDe\":\"Gericht\",\"latitude\":46.497116883418160,\"longitude\":11.340009808367348," +
                "\"id\":5018,\"ipAddress\":\"172" +
                ".24.50.9\",\"displayIdentifier\":\"BZBST009  \",\"nodeId\":4001,\"descriptionIt\":\"Tribunale\"}]";
        String positions =
                "11.35572765,46.49751458 11.35376013,46.49785308 11.35375828,46.49775965 11.35366676,46.49784921 11" +
                ".35355133,46.49780328 " +
                "11.35342476,46.49778744 11.354626,46.4975732 11.3571682,46.4977984 11.35221547,46.49784159 11" +
                ".35203376,46.49786702 " +
                "11.35198647,46.49787924 11.35193509,46.49788636 11.35191215,46.49792385 11.3518054,46.49798871";

        execute(waypoints, positions);
    }

    private void execute(String waypointsString, String positionsString) {
        List<Waypoint> positions = new ArrayList<Waypoint>();
        List<LineEdge> edges = new ArrayList<LineEdge>();
        Map<String, Waypoint> waypoints = new HashMap<String, Waypoint>();

        Gson gson = new Gson();
        Waypoint[] wps = gson.fromJson(waypointsString, Waypoint[].class);
        Waypoint wpOld = null;
        for (Waypoint wp : wps) {
            waypoints.put(wp.id, wp);
            if (wpOld != null)
                edges.add(new LineEdge(wpOld.id, wp.id, 0, 0, true));
            wpOld = wp;
        }

        int index = 0;
        String[] coords;
        for (String pt : positionsString.split(" ")) {
            coords = pt.split(",");
            positions.add(new Waypoint(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), "" + index,
                                       "" + index));
            index++;
        }

        Waypoint p;
        int edge = 0;
        int oldEdge = -1;
        int tmp;
        for (int i = 0; i < positions.size(); i++) {
            p = positions.get(i);
            tmp = DistanceUtils.closestEdge(edge, p, edges, waypoints);
            if (tmp != -1)
                edge = tmp;
            if (oldEdge != edge) {
                System.out.println(i + ": " + p.x() + "," + p.y() + " edge " + edge);
                oldEdge = edge;
            }
        }
    }
}
