import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * Brute force. Write a program BruteCollinearPoints.java that examines 4 points at a time and checks
 * whether they all lie on the same line segment, returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q,
 * between p and r, and between p and s are all equal.
 */
public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        segments = new ArrayList<>();
        for (int i = 0; i < points.length - 3; i++) {
            Point p = points[i];
            Point min = p;
            Point max = p;
            double[] slopes = new double[3];
            for (int j = 0; j < 3; j++) {
                int next = i + j + 1;
                if (points[next] == null) throw new IllegalArgumentException("Point " + next + " is null");
                if (points[next].compareTo(p) == 0) {
                    throw new IllegalArgumentException("Repeated point found: " + points[next]);
                }
                slopes[j] = p.slopeTo(points[next]);
                if (points[next].compareTo(min) < 0) {
                    min = points[next];
                } else if (points[next].compareTo(max) > 0) {
                    max = points[next];
                }
            }

            if (isCollinear(slopes)) {
                segments.add(new LineSegment(min, max));
            }
        }
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
}