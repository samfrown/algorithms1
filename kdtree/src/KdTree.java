import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class KdTree {

    private class Node {
        final Point2D point;
        final int level;
        final RectHV rect;
        int count;
        Node left;
        Node right;

        Node(Point2D point, int level, RectHV rect) {
            this.point = point;
            this.level = level;
            this.left = null;
            this.right = null;
            this.count = 1;
            this.rect = rect;
        }

        boolean xDivided() {
            return level % 2 == 0;
        }

        boolean isBiggerThan(Point2D point) {
            if (xDivided()) {
                return point.x() < this.point.x();
            } else {
                return point.y() < this.point.y();
            }
        }

        RectHV getNextRightRect() {
            if (xDivided()) {
                return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
            }
        }

        RectHV getNextLeftRect() {
            if (xDivided()) {
                return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
            }
        }

        void draw() {
            if (level == 0) {
                StdDraw.setPenColor(StdDraw.BLACK);
            } else if (xDivided()) {
                StdDraw.setPenColor(StdDraw.BLUE);
            } else {
                StdDraw.setPenColor(StdDraw.RED);
            }
            StdDraw.setPenRadius();
            rect.draw();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            point.draw();
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

        root = put(root, p, 0, new RectHV(0, 0, 1, 1));
    }

    private Node put(Node x, Point2D point, int level, RectHV rectHV) {
        if (x == null)
            return new Node(point, level, rectHV);
        if (!x.point.equals(point)) {
            if (x.isBiggerThan(point)) {
                x.left = put(x.left, point, x.level + 1, x.getNextLeftRect());
            } else {
                x.right = put(x.right, point, x.level + 1, x.getNextRightRect());
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
        x.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        if (isEmpty()) throw new NoSuchElementException();
        List<Point2D> innerPoints = new LinkedList<>();
        range(root, rect, innerPoints);
        return innerPoints;
    }

    private void range(Node x, RectHV rect, List<Point2D> points) {
        if (rect.contains(x.point))
            points.add(x.point);
        if (x.left != null && x.left.rect.intersects(rect)) range(x.left, rect, points);
        if (x.right != null && x.right.rect.intersects(rect)) range(x.right, rect, points);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) throw new NoSuchElementException();
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node x, Point2D p, Point2D closest) {
        Point2D newClosest;
        if (p.distanceTo(closest) > p.distanceTo(x.point)) {
            newClosest = new Point2D(x.point.x(), x.point.y());
        } else {
            newClosest = closest;
        }
        Node first = x.left;
        Node second = x.right;
        if (x.isBiggerThan(newClosest)) {
            first = x.right;
            second = x.left;
        }
        if (first != null && first.rect.distanceTo(p) < p.distanceTo(newClosest)) {
            newClosest = nearest(first, p, newClosest);
        }
        if (second != null && second.rect.distanceTo(p) < p.distanceTo(newClosest)) {
            newClosest = nearest(second, p, newClosest);
        }
        return newClosest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSet = new KdTree();
        // draw the points
        Point2D point1 = new Point2D(0.5, 0.5);
        Point2D point2 = new Point2D(0.2, 0.5);
        Point2D point3 = new Point2D(0.7, 0.5);
        Point2D point4 = new Point2D(0.25, 0.4);

        pointSet.insert(point1);

        if ((!pointSet.contains(point1))) throw new AssertionError("point1 must be in set");

        pointSet.insert(point2);
        if ((!pointSet.contains(point2))) throw new AssertionError("point2 must be in set");

        pointSet.insert(point3);
        if ((!pointSet.contains(point3))) throw new AssertionError("point3 must be in set");
        pointSet.insert(point4);
        if ((!pointSet.contains(point4))) throw new AssertionError("point4 must be in set");

        pointSet.draw();
        StdDraw.show();
    }
}