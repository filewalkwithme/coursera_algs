import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> redBlackTree;

    // construct an empty set of points
    public PointSET() {
        redBlackTree = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return redBlackTree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return redBlackTree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        redBlackTree.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return redBlackTree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p: redBlackTree) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        TreeSet<Point2D> result = new TreeSet<Point2D>();
        for (Point2D p: redBlackTree) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }

        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Point2D nearestPoint = null;
        boolean firstStep = true;
        double smallerDistance = 0;
        for (Point2D currentPoint: redBlackTree) {
            if (firstStep) {
                firstStep = false;
                nearestPoint = currentPoint;
                smallerDistance = currentPoint.distanceTo(p);
            } else {
                double distance = currentPoint.distanceTo(p);
                if (distance < smallerDistance) {
                    nearestPoint = currentPoint;
                    smallerDistance = distance;
                }
            }
        }

        return nearestPoint;
    }
}
