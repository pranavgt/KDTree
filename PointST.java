/**
 * Brute-force implementation. Write a mutable data type PointST.java that is symbol table with Point2D. Implement the following API by using
 *  a red-black BST (using either RedBlackBST from algs4.jar or java.util.TreeMap); do not implement your own red-black BST. 
 *
 * Clement Yang and Xin Chen
 * Java 8 3/11/22
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.NullPointerException;

public class PointST<Value> {
    private RedBlackBST bst;
    // construct an empty symbol table of points
    public PointST() {
        bst = new RedBlackBST();
    }
    // is the symbol table empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }
    // number of points
    public int size() {
        return bst.size();
    }
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p==null || val==null) throw new NullPointerException();
        bst.put(p, val);
    }
    // value associated with point p
    public Value get(Point2D p) {
        if (p==null) throw new NullPointerException();
        return (Value) bst.get(p);
    }
    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p==null) throw new NullPointerException();
        return bst.contains(p);
    }
    // all points in the symbol table
    public Iterable<Point2D> points() {
        return bst.keys();
    }
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect==null) throw new NullPointerException();
        Iterable<Point2D> it = points();
        ArrayList<Point2D> arr = new ArrayList<Point2D>();
        for (Point2D point : it)
            if (rect.contains(point)) arr.add(point);
        return arr;
    }
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p==null) throw new NullPointerException();
        Point2D closest = null;
        Iterable<Point2D> it = points();
        for (Point2D point : it) {
            if (closest==null) closest = point;
            else if (p.distanceTo(point) < p.distanceTo(closest)) closest = point;
        }
        return closest;
    }
    // unit testing of the methods (not graded)
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        PointST pst = new PointST();
        int N = scan.nextInt();
        for (int i=0; i<N; ++i) {
            double x = scan.nextDouble();
            double y = scan.nextDouble();
            pst.put(new Point2D(x, y), 0);
        }
        System.out.println("isEmpty(): " + pst.isEmpty());
        System.out.println("size(): " + pst.size());
        System.out.println("contains(0.9, 0.6): " + pst.contains(new Point2D(0.9, 0.6)));
        System.out.print("All points in pst: ");
        Iterable<Point2D> points = pst.points();
        for (Point2D p : points) System.out.print(p + " ");
        System.out.print("\nAll points in range(0.2, 0.2, 0.7, 0.7): ");
        Iterable<Point2D> inRect = pst.range(new RectHV(0.3, 0.3, 0.7, 0.7));
        for (Point2D p : inRect) System.out.print(p + " ");
        System.out.println("\nnearest(0.5, 0.5): " + pst.nearest(new Point2D(0.5, 0.5)));
    }
}
/**
 * Input:
 5
 0.7 0.2
 0.5 0.4
 0.2 0.3
 0.4 0.7
 0.9 0.6
 * Output:
 isEmpty(): false
 size(): 5
 contains(0.9, 0.6): true
 All points in pst: (0.7, 0.2) (0.2, 0.3) (0.5, 0.4) (0.9, 0.6) (0.4, 0.7)
 All points in range(0.2, 0.2, 0.7, 0.7): (0.5, 0.4) (0.4, 0.7)
 nearest(0.5, 0.5): (0.5, 0.4)
 */