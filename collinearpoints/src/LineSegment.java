public class LineSegment {

    private final Point begin;
    private final Point end;

    // constructs the line segment between points p and q
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) throw new IllegalArgumentException("Both points should be defined");

        if (p.compareTo(q) <= 0) {
            this.begin = p;
            this.end = q;
        } else {
            this.begin = q;
            this.end = p;
        }
    }

    // draws this line segment
    public void draw() {
        begin.drawTo(end);
    }

    // string representation
    public String toString() {
        return begin + "->" + end;
    }
}