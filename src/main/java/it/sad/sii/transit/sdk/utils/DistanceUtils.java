package it.sad.sii.transit.sdk.utils;

import it.sad.sii.transit.sdk.model.Location;
import it.sad.sii.transit.sdk.model.Pair;
import it.sad.sii.transit.sdk.model.LineEdge;
import it.sad.sii.transit.sdk.model.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldematte on 7/7/14.
 */
public class DistanceUtils {

    public static double percentageAlongLine(Point P, Point A, Point B) {
        Pair<Point, Point> lowerLine = planeFromSegment(A, B);

        PointPosition positionForLower = beforeOrAfter(P, lowerLine.first, lowerLine.second);
        if (positionForLower == PointPosition.Before)
            return 0.0;

        Pair<Point, Point> upperLine = planeFromSegment(B, A);
        PointPosition positionForUpper = beforeOrAfter(P, upperLine.second, upperLine.first);
        if (positionForUpper == PointPosition.After)
            return 1.0;

        double seg1 = distance(P, lowerLine.first, lowerLine.second);
        double seg2 = distance(P, upperLine.second, upperLine.first);
        return seg1 / (seg1 + seg2);
    }

    public static class EdgeAndPosition {
        private final LineEdge edge;
        private final int position;

        private EdgeAndPosition(LineEdge edge, int position) {
            this.edge = edge;
            this.position = position;
        }

        public LineEdge getEdge() {
            return edge;
        }

        public int getPosition() {
            return position;
        }
    }

    public enum PointPosition {
        OnLine,
        Before,
        After
    }

    // Given a line, identified by two points, it tests if the point is "before", on, or "after" the line
    //         X After
    //         |
    //     B ------ A
    //           |
    //           X Before
    //
    // It computes the dot product to see if it "left-handed" or "right-handed".
    public static PointPosition beforeOrAfter(Point p, Point A, Point B) {
        double dotProduct = (B.x() - A.x()) * (p.y() - A.y()) - (B.y() - A.y()) * (p.x() - A.x());

        if (Math.abs(dotProduct) < 1E-6)
            return PointPosition.OnLine;
        else {
            if (dotProduct > 0)
                return PointPosition.After;
            else
                return PointPosition.Before;
        }
    }

    // Given a vector, it computes the two perpendicular line (as two points) passing on the starting point
    // Segment is (AB)
    public static Pair<Point, Point> planeFromSegment(Point A, Point B) {
        // treat AB as a vector.
        // Subtract A components ("translate" to origin)
        double x = B.x() - A.x();
        double y = B.y() - A.y();

        // If you have a vector v with coordinates (x, y), then a vector perpendicular to v is (y, -x) or (-y, x).
        Point d = new Point();
        d.x(y + A.x());
        d.y(-x + A.y());

        Point c = new Point();
        c.x(-y + A.x());
        c.y(x + A.y());

        return Pair.create(c, d);
    }

    public enum PositionOnLine {
        OnStart,
        OnEnd,
        Between,
        Out,
        Distant,
    }

    // if P is before A or after B -> Out
    // else if P is too far away from AB -> Distant
    // else if P is on perpendicular line crossing A -> OnStart
    // else if P is on perpendicular line crossing B -> OnEnd
    // else -> Between
    public static PositionOnLine isBetween(Point P, Point A, Point B) {
        // location w.r.t. start point A
        Pair<Point, Point> lowerLine = planeFromSegment(A, B);
        PointPosition positionForLower = beforeOrAfter(P, lowerLine.first, lowerLine.second);
        // location w.r.t. end point B
        Pair<Point, Point> upperLine = planeFromSegment(B, A);
        PointPosition positionForUpper = beforeOrAfter(P, upperLine.second, upperLine.first);
        // distance to line AB
        float distance = (float)DistanceUtils.distance(P, A, B);

        // before A or after B
        if (positionForLower == PointPosition.Before || positionForUpper == PointPosition.After) {
            return PositionOnLine.Out;
        }
        // do not consider line if point is located too far away, ca 150 meters
        if (Math.abs(distance) > 1.5 * 1E-3) {
            return PositionOnLine.Distant;
        }
        if (positionForLower == PointPosition.OnLine) {
            return PositionOnLine.OnStart;
        }
        if (positionForUpper == PointPosition.OnLine) {
            return PositionOnLine.OnEnd;
        }
        return PositionOnLine.Between;
    }

    private static double Sq(double v) {
        return v * v;
    }

    public static double distance(Point P, Point A, Point B) {
        double normalLength =
                Math.sqrt(Sq(B.longitude.doubleValue() - A.longitude.doubleValue()) +
                          Sq(B.latitude.doubleValue() - A.latitude.doubleValue()));
        return Math.abs((P.longitude.doubleValue() - A.longitude.doubleValue()) *
                        (B.latitude.doubleValue() - A.latitude.doubleValue()) -
                        (P.latitude.doubleValue() - A.latitude.doubleValue()) *
                        (B.longitude.doubleValue() - A.longitude.doubleValue())) / normalLength;
    }

    public static double pointToLineDistance(Point lineStart, Point lineEnds, Location P) {
        double normalLength = Math.sqrt(Sq(lineEnds.longitude.doubleValue() - lineStart.longitude.doubleValue()) +
                                        Sq(lineEnds.latitude.doubleValue() - lineStart.latitude.doubleValue()));
        return Math.abs((P.getLongitude() - lineStart.longitude.doubleValue()) *
                        (lineEnds.latitude.doubleValue() - lineStart.latitude.doubleValue()) -
                        (P.getLatitude() - lineStart.latitude.doubleValue()) *
                        (lineEnds.longitude.doubleValue() - lineStart.longitude.doubleValue())) /
               normalLength;
    }

    public static int closestEdge(int currentEdgeIndex, Point p, List<LineEdge> edges,
                                  Map<Integer, Point> waypointMap) {
        if (currentEdgeIndex >= edges.size()) {
            return -1;
        }
        Point pointA;
        Point pointB;
        DistanceUtils.PositionOnLine positionOnLine;
        // return -1 if current position lies not within any edge
        int closestEdgeIndex = -1;
        float bestDistance = Float.MAX_VALUE;

        //never backtrack!
        for (int i = currentEdgeIndex; i < edges.size(); ++i) {
            pointA = waypointMap.get(edges.get(i).getIdA());
            pointB = waypointMap.get(edges.get(i).getIdB());
            positionOnLine = DistanceUtils.isBetween(p, pointA, pointB);

            if (currentEdgeIndex == edges.size() - 1) {
                if (positionOnLine == PositionOnLine.OnEnd || positionOnLine == PositionOnLine.Out ||
                    positionOnLine == PositionOnLine.Distant) {
                    return currentEdgeIndex + 1;
                }
            }

            if (positionOnLine != DistanceUtils.PositionOnLine.Out && positionOnLine != PositionOnLine.Distant) {
                // if we are on in between... compute the distance FROM THE LINE.
                // There might be more than one!
                float distance = (float)DistanceUtils.distance(p, pointA, pointB);
                int edgeDistance = (i - currentEdgeIndex) + 1;
                // Weighted distance - farther away we are form the current point,
                // lower is the probability this is our point
                float weightedDistance = distance * edgeDistance;
                if (weightedDistance < bestDistance) {
                    closestEdgeIndex = i;
                    bestDistance = weightedDistance;
                }
            }
        }
        return closestEdgeIndex;
    }

    private static EdgeAndPosition nextEdge(int i, List<LineEdge> currentVertices, HashMap<Integer, Point> waypoints) {
        int currentStart = -1;
        int currentEnd = -1;
        long currentTime = 0;

        // find start
        while (i < currentVertices.size() && currentStart < 0) {
            if (waypoints.containsKey(currentVertices.get(i).getIdA())) {
                currentStart = currentVertices.get(i).getIdA();
                currentTime = 0;
            } else {
                ++i; // Increment only if not found
            }
        }

        // find end
        while (i < currentVertices.size() && currentEnd < 0) {
            currentTime += currentVertices.get(i).getDurationInMillis();

            if (waypoints.containsKey(currentVertices.get(i).getIdB())) {
                currentEnd = currentVertices.get(i).getIdB();
            }
            ++i; // Increment anyway
        }

        if (currentStart >= 0 && currentEnd >= 0)
            return new EdgeAndPosition(
                    new LineEdge(currentStart, currentEnd, currentTime, currentVertices.get(0).getLineId(),
                                 currentVertices.get(0).isForward()), i);
        else
            return new EdgeAndPosition(null, i);
    }

    // Given:
    // A list of points
    // a list of line edges
    // Find out the edges present in both, and trim them.
    // Adjust edges and times accordingly (A-B-C -> no B -> A-C)
    public static void trimEdgesToWaypoints(List<Point> currentPoints, List<LineEdge> currentEdges,
                                            List<Point> trimmedPoints,
                                            List<LineEdge> trimmedEdges) {

        if (currentEdges.size() == 0) {
            return;
        }
        // Add the first point
        for (Point point : currentPoints) {
            if (Integer.parseInt(point.id) == currentEdges.get(0).getIdA()) {
                trimmedPoints.add(point);
                break;
            }
        }

        // Add all the other ones
        for (LineEdge lineEdge : currentEdges) {
            for (Point point : currentPoints) {
                if (Integer.parseInt(point.id) == lineEdge.getIdB()) {
                    trimmedPoints.add(point);
                    break;
                }
            }
        }
        HashMap<Integer, Point> waypointMap = new HashMap<Integer, Point>();
        // Build the waypoint map with the available ones (we need it now)
        for (Point point : trimmedPoints) {
            waypointMap.put(Integer.parseInt(point.id), point);
        }


        int i = 0;
        while (i < currentEdges.size()) {
            EdgeAndPosition nextEdge = nextEdge(i, currentEdges, waypointMap);
            i = nextEdge.getPosition();
            if (nextEdge.getEdge() != null)
                trimmedEdges.add(nextEdge.getEdge());
        }
    }
}
