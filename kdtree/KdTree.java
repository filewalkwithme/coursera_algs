import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;

public class KdTree {
    private Node node;
    private int size = 0;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return node == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (node == null) {
            node = new Node(p);
        } else {
            node.insert(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return this.node.find(p) != null;
    }

    // draw all points to standard draw
    public void draw() {
        if (this.node != null) {
            this.node.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {

        TreeSet<Point2D> result = new TreeSet<Point2D>();

        if (this.node != null) {
            this.node.range(rect, result);
        }

        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        boolean firstStep = true;
        double smallerDistance = 0;

        this.node.nearest(p, this.node.getPoint(), this.node.getPoint().distanceTo(p));

        return nearestPoint;
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
        n.draw();

        System.out.printf("Contains 4,6? %b\n", n.find(new Point2D(4,6))!=null);
        System.out.printf("Contains 8,8? %b\n", n.find(new Point2D(8,8))!=null);
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


    public void draw() {
        if (this.point != null) {
            this.point.draw();
            //if (level % 2 == 0){
                this.point.drawTo(new Point2D(0, this.point.y()));
            //}

            if (this.left != null) {
                this.left.draw();
            }

            if (this.right != null) {
                this.right.draw();
            }
        }
    }

    public void nearest(Point2D queryPoint, Point2D nearest, double nearestDistance) {
        if (this.point != null && this.point.distanceTo(queryPoint) < nearestDistance) {
            nearest = this.point;
            nearestDistance = this.point.distanceTo(queryPoint);
        }

        if (level % 2 == 0) {
            if (this.left != null && queryPoint.distanceTo(new Point2D(this.left.point.x(), queryPoint.y())) < nearestDistance) {
                this.left.nearest(queryPoint, nearest, nearestDistance);
            }
            if (this.right != null && queryPoint.distanceTo(new Point2D(this.right.point.x(), queryPoint.y())) < nearestDistance) {
                this.right.nearest(queryPoint, nearest, nearestDistance);
            }
        } else {
            if (this.left != null && queryPoint.distanceTo(new Point2D(queryPoint.x(), this.left.point.y())) < nearestDistance) {
                this.left.nearest(queryPoint, nearest, nearestDistance);
            }
            if (this.right != null && queryPoint.distanceTo(new Point2D(queryPoint.x(), this.right.point.y())) < nearestDistance) {
                this.right.nearest(queryPoint, nearest, nearestDistance);
            }
        }
    }

    public void range(RectHV rect, TreeSet<Point2D> result) {
        if (rect.contains(this.point)) {
            result.add(this.point);
            if (level % 2 == 0) {
                if (this.left != null && rect.xmin() <= this.point.x() || rect.xmax() <= this.point.x()) {
                    this.left.range(rect, result);
                }
                if (this.right != null && rect.xmin() >= this.point.x() || rect.xmax() >= this.point.x()) {
                    this.right.range(rect, result);
                }
            } else {
                if (this.left != null && rect.ymin() <= this.point.y() || rect.ymax() <= this.point.y()) {
                    this.left.range(rect, result);
                }
                if (this.right != null && rect.ymin() >= this.point.y() || rect.ymax() >= this.point.y()) {
                    this.right.range(rect, result);
                }
            }
        }
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


    public Point2D find(Point2D point) {
        if (point.x() == this.point.x() && point.y() == this.point.y()) {
            return this.point;
        } else {
            if (level % 2 == 0) {
                if (point.x() < this.point.x()) {
                    if (this.left == null) {
                        return null;
                    } else {
                        return this.left.find(point);
                    }
                } else {
                    if (this.right == null) {
                        return null;
                    } else {
                        return this.right.find(point);
                    }
                }
            } else {
                if (point.y() < this.point.y()) {
                    if (this.left == null) {
                        return null;
                    } else {
                        return this.left.find(point);
                    }
                } else {
                    if (this.right == null) {
                        return null;
                    } else {
                        return this.right.find(point);
                    }
                }
            }
        }
    }
}
