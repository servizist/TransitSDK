package it.sad.sii.transit.sdk.utils;

import it.sad.sii.transit.sdk.model.*;
import it.sad.sii.transit.sdk.model.Location;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldematte on 7/7/14.
 */
public class DistanceUtils {

    public static double percentageAlongLine(Location P, Location A, Location B) {
        Pair<Location, Location> lowerLine = planeFromSegment(A, B);

        PointPosition positionForLower = beforeOrAfter(P, lowerLine.first, lowerLine.second);
        if (positionForLower == PointPosition.Before)
            return 0.0;

        Pair<Location, Location> upperLine = planeFromSegment(B, A);
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
    public static PointPosition beforeOrAfter(Location p, Location A, Location B) {
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
    public static<L extends Location> Pair<Location, Location> planeFromSegment(L A, L B) {
        // treat AB as a vector.
        // Subtract A components ("translate" to origin)
        double x = B.x() - A.x();
        double y = B.y() - A.y();

        // If you have a vector v with coordinates (x, y), then a vector perpendicular to v is (y, -x) or (-y, x).
        Location d = new Location();
        d.x(y + A.x());
        d.y(-x + A.y());

        Location c = new Location();
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
    public static PositionOnLine isBetween(Location P, Location A, Location B) {
        // location w.r.t. start point A
        Pair<Location, Location> lowerLine = planeFromSegment(A, B);
        PointPosition positionForLower = beforeOrAfter(P, lowerLine.first, lowerLine.second);
        // location w.r.t. end point B
        Pair<Location, Location> upperLine = planeFromSegment(B, A);
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

    public static double distance(Location P, Location A, Location B) {
        double normalLength =
                Math.sqrt(Sq(B.x() - A.x()) +
                          Sq(B.y() - A.y()));
        return Math.abs((P.x() - A.x()) *
                        (B.y() - A.y()) -
                        (P.y() - A.y()) *
                        (B.x() - A.x())) / normalLength;
    }

    public static<L1 extends Location, L2 extends Location> int closestEdge(int currentEdgeIndex,
                                                                            L1 p, List<LineEdge> edges,
                                                                            Map<String, L2> waypointMap) {
        if (currentEdgeIndex >= edges.size()) {
            return -1;
        }
        Location pointA;
        Location pointB;
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

    private static<L extends Location> EdgeAndPosition nextEdge(int i, List<LineEdge> currentVertices,
                                                                HashMap<String, L> waypoints) {
        String currentStart = null;
        String currentEnd = null;
        long currentTime = 0;

        // find start
        while (i < currentVertices.size() && currentStart == null) {
            if (waypoints.containsKey(currentVertices.get(i).getIdA())) {
                currentStart = currentVertices.get(i).getIdA();
                currentTime = 0;
            } else {
                ++i; // Increment only if not found
            }
        }

        // find end
        while (i < currentVertices.size() && currentEnd == null) {
            currentTime += currentVertices.get(i).getDurationInMillis();

            if (waypoints.containsKey(currentVertices.get(i).getIdB())) {
                currentEnd = currentVertices.get(i).getIdB();
            }
            ++i; // Increment anyway
        }

        if (currentStart != null && currentEnd != null)
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
    public static Map<String, Waypoint> trimEdgesToWaypoints(List<Waypoint> currentPoints, List<LineEdge> currentEdges,
                                                              List<Waypoint> trimmedPoints,
                                                              List<LineEdge> trimmedEdges) {

        if (currentEdges.size() == 0) {
            return Collections.emptyMap();
        }
        // Add the first point
        for (Waypoint point : currentPoints) {
            if (point.id.equals(currentEdges.get(0).getIdA())) {
                trimmedPoints.add(point);
                break;
            }
        }

        // Add all the other ones
        for (LineEdge lineEdge : currentEdges) {
            for (Waypoint point : currentPoints) {
                if (point.id.equals(lineEdge.getIdB())) {
                    trimmedPoints.add(point);
                    break;
                }
            }
        }
        HashMap<String, Waypoint> waypointMap = new HashMap<>();
        // Build the waypoint map with the available ones (we need it now)
        for (Waypoint point : trimmedPoints) {
            waypointMap.put(point.id, point);
        }


        int i = 0;
        while (i < currentEdges.size()) {
            EdgeAndPosition nextEdge = nextEdge(i, currentEdges, waypointMap);
            i = nextEdge.getPosition();
            if (nextEdge.getEdge() != null)
                trimmedEdges.add(nextEdge.getEdge());
        }

        return waypointMap;
    }
}
