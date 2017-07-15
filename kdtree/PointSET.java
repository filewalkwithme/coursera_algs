import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;

public class PointSET {
    TreeSet<Point2D> redBlackTree;

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
        redBlackTree.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
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
        Point2D nearestPoint = null;
        boolean firstStep = true;
        double smallerDistance = 0;
        for (Point2D currentPoint: redBlackTree) {
            if (currentPoint == p) {
                continue;
            }
            if (firstStep = true) {
                firstStep = false;
                nearestPoint = p;
                smallerDistance = currentPoint.distanceTo(p);
            } else {
                double distance = currentPoint.distanceTo(p);
                if (distance < smallerDistance) {
                    nearestPoint = p;
                    smallerDistance = distance;
                }
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
