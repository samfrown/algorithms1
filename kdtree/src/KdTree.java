import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class KdTree {

    private class Node {
        Point2D point;
        Node left;
        Node right;
        int level;
        int count;

        Node(Point2D point, int level) {
            this(point, level, null, null);
        }

        Node(Point2D point, int level, Node left, Node right) {
            this.point = point;
            this.level = level;
            this.left = left;
            this.right = right;
            this.count = 1;
        }

        boolean xDivided() {
            return level / 2 == 0;
        }

        boolean isBiggerThan(Point2D point) {
            if (xDivided()) {
                return point.x() < this.point.x();
            } else {
                return point.y() < this.point.y();
            }
        }
    }

    private Node root;

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return isEmpty() ? 0 : root.count;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        root = put(root, p, 0);
    }

    private Node put(Node x, Point2D point, int level) {
        if (x == null)
            return new Node(point, level);
        if (!x.point.equals(point)) {
            if (x.isBiggerThan(point)) {
                x.left = put(x.left, point, x.level + 1);
            } else {
                x.right = put(x.right, point, x.level + 1);
            }
            ++x.count;
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node x = root;
        while (x != null) {
            if (x.point.equals(p)) return true;
            if (x.isBiggerThan(p)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        if (isEmpty()) return;

        draw(root);
    }

    // draw all points to standard draw
    private void draw(Node x) {
        if (x.left != null) draw(x.left);
        if (x.right != null) draw(x.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        if(isEmpty()) throw new NoSuchElementException();
        List<Point2D> innerPoints = new ArrayList();

        return innerPoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if(isEmpty()) throw new NoSuchElementException();
        return root.point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSet = new KdTree();
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        pointSet.draw();
        StdDraw.show();
    }
}