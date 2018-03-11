import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fast. Write a program FastCollinearPoints.java that examines 4 points at a time and checks
 * whether they all lie on the same line segment, returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q,
 * between p and r, and between p and s are all equal.
 */
public class FastCollinearPoints {

    private final List<LineSegment> segments;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        List<LineSegment> allSegments = new ArrayList<>();
        Point prevPoint = null;
        int k = 0;
        while (k < points.length) {
            Point point = sortedPoints[k++];
            if (prevPoint != null && prevPoint.compareTo(point) == 0)
                throw new IllegalArgumentException("Repeated point found: " + point);
            prevPoint = point;
            Arrays.sort(sortedPoints, point.slopeOrder());
            Point min = point;
            Point max = point;
            int count = 0;
            double slope = Double.NEGATIVE_INFINITY;
            for (Point point1 : sortedPoints) {
                if (point.compareTo(point1) == 0) continue;
                double currSlope = point.slopeTo(point1);
                if (Double.compare(currSlope, slope) == 0) {
                    count++;
                    if (min.compareTo(point1) > 0) {
                        min = point1;
                    } else if (max.compareTo(point1) < 0) {
                        max = point1;
                    }
                } else {
                    if (count >= 3) {
                        allSegments.add(new LineSegment(min, max));
                    }
                    count = 0;
                    slope = currSlope;
                    min = point;
                    max = point;
                }
            }
        }
        segments = allSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
}
