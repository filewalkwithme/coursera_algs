import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


public class KdTree {
    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return false;
    }

    // number of points in the set
    public int size() {
        return 0;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return false;
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        System.out.printf("oi!\n");
        Node n = new Node(new Point2D(5,5));
        n.insert(new Point2D(4,4));
        n.insert(new Point2D(4,6));
        n.insert(new Point2D(3,3));
        n.insert(new Point2D(3,1));
        n.insert(new Point2D(7,7));
        n.print();
    }
}

class Node {
    public int level;
    public Node left;
    public Node right;
    private Point2D point;

    Node(Point2D point){
        this.point = point;
    }

    public Point2D getPoint(){
        return this.point;
    }


    public void print() {
        if (this.point != null) {
            String traces = "";
            for (int i = 0; i < level * 4; i++) {
                traces = traces + "-";
            }
            System.out.printf(traces+"%f|%f\n", this.point.x(), this.point.y());
            if (this.left != null) {
                this.left.print();
            }
            if (this.right != null) {
                this.right.print();
            }
        }
    }

    public void insert(Point2D point) {
        if (level % 2 == 0) {
            if (point.x() < this.point.x()) {
                if (this.left == null) {
                    this.left = new Node(point);
                    this.left.level = this.level + 1;
                } else {
                    this.left.insert(point);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(point);
                    this.right.level = this.level + 1;
                } else {
                    this.right.insert(point);
                }
            }
        } else {
            if (point.y() < this.point.y()) {
                if (this.left == null) {
                    this.left = new Node(point);
                    this.left.level = this.level + 1;
                } else {
                    this.left.insert(point);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(point);
                    this.right.level = this.level + 1;
                } else {
                    this.right.insert(point);
                }
            }
        }
    }

}
