import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Brute force. Write a program BruteCollinearPoints.java that examines 4 points at a time and checks
 * whether they all lie on the same line segment, returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q,
 * between p and r, and between p and s are all equal.
 */
public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        try {
            Arrays.sort(points);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        List<LineSegment> allSegments = new ArrayList<>();
        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                if (points[i1].compareTo(points[i2]) == 0)
                    throw new IllegalArgumentException("Repeated point found: " + points[i1]);
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        Point[] qvartet = {points[i1], points[i2], points[i3], points[i4]};
                        Arrays.sort(qvartet);
                        double[] slopes = new double[3];
                        Point p0 = qvartet[0];
                        for (int j = 0; j < 3; j++) {
                            Point point1 = qvartet[j + 1];
                            if (p0.compareTo(point1) == 0)
                                throw new IllegalArgumentException("Repeated point found: " + point1);
                            slopes[j] = p0.slopeTo(point1);
                        }
                        if (isCollinear(slopes)) {
                            allSegments.add(new LineSegment(qvartet[0], qvartet[qvartet.length - 1]));
                        }
                    }
                }
            }
        }

        segments = allSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        if (args.length <= 0) {
            System.out.println("usage: java BruteCollinearPoints <file>");
        }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private boolean isCollinear(double[] slopes) {
        for (int i = 1; i < slopes.length; i++) {
            if (Double.compare(slopes[0], slopes[i]) != 0) {
                return false;
            }
        }
        return true;
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