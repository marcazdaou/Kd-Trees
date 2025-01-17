import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;

public class BrutePointST<Value> implements PointST<Value> {
    private RedBlackBinarySearchTreeST<Point2D, Value> bst;  // bst

    // Constructs an empty symbol table.
    public BrutePointST() {
        bst = new RedBlackBinarySearchTreeST<Point2D, Value>();
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return bst.size();
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        // corner case
        // p
        // value
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        bst.put(p, value);
    }

    // Returns the value associated with the given point in this symbol table, or null.
    public Value get(Point2D p) {
        // corner p
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.get(p);
    }

    // Returns true if this symbol table contains the given point, and false otherwise.
    public boolean contains(Point2D p) {
        // corner case p
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.contains(p);
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // Returns all the points in this symbol table that are inside the given rectangle.
    public Iterable<Point2D> range(RectHV rect) {
        // corner rect
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> temp = new LinkedQueue<>();
        for (Point2D point : points()) {
            if (rect.contains(point)) {
                temp.enqueue(point);
            }
        }
        return temp;
    }

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        // corner case p
        // nearst = null
        // nearst_distance = positive_infinity
        // for loop d distancesquareto(point)
        // update d
        // return key
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        Point2D nearest = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : points()) {
            double d = p.distanceSquaredTo(point);
            if (nearest == null || d < nearestDistance && !point.equals(p)) {
                nearest = point;
                nearestDistance = d;
            }
        }
        return nearest;
    }

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // corner case p
        // MinPQ
        // insertion
        // delmin
        MinPQ<Point2D> temp = new MinPQ<Point2D>(p.distanceToOrder());
        for (Point2D point : points()) {
            if (!point.equals(p)) {
                temp.insert(point);
            }
        }
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        for (int i = 0; i < k && !temp.isEmpty(); i++) {
            queue.enqueue(temp.delMin());
        }
        return queue;
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
