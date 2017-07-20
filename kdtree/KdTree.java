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
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int inserted = 0;
        if (node == null) {
            node = new Node(p);
            inserted = 1;
        } else {
            inserted = node.insert(p);
        }
        size = size + inserted;

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (this.node != null) {
            return this.node.find(p) != null;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        if (this.node != null) {
            this.node.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        TreeSet<Point2D> result = new TreeSet<Point2D>();

        if (this.node != null) {
            this.node.range(rect, result);
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D queryPoint) {
        if (queryPoint == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (this.node != null && this.node.getPoint() != null) {
            Point2D nearestPoint = new Point2D(this.node.getPoint().x(), this.node.getPoint().y());
            nearestPoint = this.node.nearest(queryPoint, nearestPoint, nearestPoint.distanceTo(queryPoint));

            return nearestPoint;
        }
        return null;
    }

    private class Node {
        private int level;
        private Node left;
        private Node right;
        private final Point2D point;

        Node(Point2D point) {
            this.point = point;
        }

        public Point2D getPoint() {
            return this.point;
        }


        public void draw() {
            if (this.point != null) {
                this.point.draw();
                this.point.drawTo(new Point2D(0, this.point.y()));

                if (this.left != null) {
                    this.left.draw();
                }

                if (this.right != null) {
                    this.right.draw();
                }
            }
        }

        public Point2D nearest(Point2D queryPoint, Point2D nearest, double nearestDistance) {
            if (this.point != null && this.point.distanceTo(queryPoint) < nearestDistance) {
                nearest = this.point;
                nearestDistance = this.point.distanceTo(queryPoint);
            }

            if (level % 2 == 0) {
                if (queryPoint.x() < this.point.x()) {
                    if (this.left != null) {
                        nearest = this.left.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                    if (this.right != null && nearestDistance > queryPoint.distanceTo(new Point2D(this.point.x(), queryPoint.y()))) {
                        nearest = this.right.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                } else {
                    if (this.right != null) {
                        nearest = this.right.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                    if (this.left != null && nearestDistance > queryPoint.distanceTo(new Point2D(this.point.x(), queryPoint.y()))) {
                        nearest = this.left.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                }
            } else {
                if (queryPoint.y() < this.point.y()) {
                    if (this.left != null) {
                        nearest = this.left.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                    if (this.right != null && nearestDistance > queryPoint.distanceTo(new Point2D(queryPoint.x(), this.point.y()))) {
                        nearest = this.right.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                } else {
                    if (this.right != null) {
                        nearest = this.right.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                    if (this.left != null && nearestDistance > queryPoint.distanceTo(new Point2D(queryPoint.x(), this.point.y()))) {
                        nearest = this.left.nearest(queryPoint, nearest, nearestDistance);
                        nearestDistance = nearest.distanceTo(queryPoint);
                    }
                }
            }
            return nearest;
        }

        public void range(RectHV rect, TreeSet<Point2D> result) {
            if (rect.contains(this.point)) {
                result.add(this.point);
            }

            if (level % 2 == 0) {
                if (this.left != null && (rect.xmin() <= this.point.x() || rect.xmax() <= this.point.x())) {
                    this.left.range(rect, result);
                }
                if (this.right != null && (rect.xmin() >= this.point.x() || rect.xmax() >= this.point.x())) {
                    this.right.range(rect, result);
                }
            } else {
                if (this.left != null && (rect.ymin() <= this.point.y() || rect.ymax() <= this.point.y())) {
                    this.left.range(rect, result);
                }
                if (this.right != null && (rect.ymin() >= this.point.y() || rect.ymax() >= this.point.y())) {
                    this.right.range(rect, result);
                }
            }

        }

        public int insert(Point2D p) {
            if (this.point.compareTo(p) == 0) {
                return 0;
            }
            if (level % 2 == 0) {
                if (p.x() < this.point.x()) {
                    if (this.left == null) {
                        this.left = new Node(p);
                        this.left.level = this.level + 1;
                        return 1;
                    } else {
                        return this.left.insert(p);
                    }
                } else {
                    if (this.right == null) {
                        this.right = new Node(p);
                        this.right.level = this.level + 1;
                        return 1;
                    } else {
                        return this.right.insert(p);
                    }
                }
            } else {
                if (p.y() < this.point.y()) {
                    if (this.left == null) {
                        this.left = new Node(p);
                        this.left.level = this.level + 1;
                        return 1;
                    } else {
                        return this.left.insert(p);
                    }
                } else {
                    if (this.right == null) {
                        this.right = new Node(p);
                        this.right.level = this.level + 1;
                        return 1;
                    } else {
                        return this.right.insert(p);
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
}
