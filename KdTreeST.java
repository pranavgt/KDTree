/**
 Write a mutable data type KdTreeST.java that uses a 2d-tree to implement the same API (but renaming PointST to KdTreeST). A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence, starting with the x-coordinates.

 Xin & Clement
 Java 8 3/14/22
 */
import java.lang.Math;
import java.util.Scanner;
public class KdTreeST<Value> {
    // construct an empty symbol table of points

    private Node root;             // root of BST
    //    private int size = 0;
    private int levelHelper;

    private class Node {
        private Point2D p;         // associated data
        private Value val;
        private Node left, right;  // left and right subtrees
        private int NodeLevel;
        private int size =0;// number of nodes in subtree


        public Node(Point2D p, Value val, int Nodelevel) {
            this.p=p;
            this.val = val;
            this.NodeLevel = Nodelevel;
            this.size=0;
        }
        // public double compareTo(Node other){
        //   if(NodeLevel%2==0) return p.y()-other.p.y();
        //   return p.x()-other.p.x();
        // }
    }
    public KdTreeST(){

    }
    // is the symbol table empty?
    public boolean isEmpty(){
        return root.size==0;
    }
    // number of points
    public int size(Node x ){
        return x.size;
    }
    public int size(){
        return root.size;
    }
    // associate the value val with point p
    public void put(Point2D p, Value val){
        if (p == null) throw new IllegalArgumentException("calls put() with a null point");

        levelHelper = 1;
        root = put(root, p, val);
    }
    private Node put(Node x, Point2D p, Value val) {
        if (x == null) return new Node(p, val, levelHelper);
        int cmp;
        if(levelHelper%2==1){
            if(p.x()<x.p.x()){
                cmp = -1;
            }
            else if(p.x()>x.p.x()){
                cmp=1;
            }
            else{
                cmp=0;
            }
        }
        else{    //level helper is a multiple of 2
            if(p.y()<x.p.y()){
                cmp = -1;
            }
            else if(p.y()>x.p.y()){
                cmp=1;
            }
            else{
                cmp=0;
            }
        }
        //int cmp = key.compareTo(x.key);
        if      (cmp < 0) {
            levelHelper ++;
            x.left  = put(x.left,  p, val);

        }
        else if (cmp > 0) {
            levelHelper ++;
            x.right = put(x.right, p, val);

        }
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }
    public Value get(Point2D p)    {
        levelHelper = 1;
        return get(root,  p);
    }             // value associated with point p
    private Value get(Node x, Point2D p){
        if (p == null) throw new IllegalArgumentException("calls get() with a null point");
        if (x == null) return null;
        int cmp=0;
        if(levelHelper%2==1){
            if(p.x()<x.p.x()){
                cmp = -1;
            }
            else if(p.x()>x.p.x()){
                cmp=1;
            }
            else{
                cmp=0;
            }
        }
        else{    //level helper is a multiple of 2
            if(p.y()<x.p.y()){
                cmp = -1;
            }
            else if(p.y()>x.p.y()){
                cmp=1;
            }
            else{
                cmp=0;
            }
        }
        if      (cmp < 0) {
            levelHelper++;
            return get(x.left, p);
        }
        else if (cmp > 0) {
            levelHelper++;
            return get(x.right, p);
        }
        else              return x.val;

    }
    public           boolean contains(Point2D p){
        if (p == null) throw new IllegalArgumentException("argument to contains() is point");
        return get(p) != null;
    }            // does the symbol table contain point p?
    public Iterable<Point2D> points() {
        Queue<Node> q = new Queue<Node>();
        Queue<Point2D> q2 = new Queue<Point2D>();
        q.enqueue(root);
        while (!q.isEmpty())
        {
            Node x = q.dequeue();
            if (x == null) continue;
            q2.enqueue(x.p);
            q.enqueue(x.left);
            q.enqueue(x.right);
        }
        return q2;
    }                      // all points in the symbol table
    public Iterable<Point2D> range(RectHV rect){
        double xmax = rect.xmax();
        double xmin=rect.xmin();
        double ymax = rect.ymax();
        double ymin = rect.ymin();
        Queue<Node> q = new Queue<Node>();
        Queue<Point2D> q2 = new Queue<Point2D>();
        q.enqueue(root);
        while (!q.isEmpty())
        {
            Node x = q.dequeue();
            if (x == null) continue;
            if(x.p.x()>=xmin&&x.p.x()<=xmax&&x.p.y()>=ymin&&x.p.y()<=ymax){
                q2.enqueue(x.p);
            }
            q.enqueue(x.left);
            q.enqueue(x.right);
        }
        return q2;
    }             // all points that are inside the rectangle



    public           Point2D nearest(Point2D p) {
        Queue<Node> q = new Queue<Node>();
        q.enqueue(root);
        Point2D near=null;
        while (!q.isEmpty())
        {
            Node x = q.dequeue();
            if (x == null) continue;
            if (near==null || distance(p,x.p)<distance(p,near)) near = x.p;
            q.enqueue(x.left);
            q.enqueue(x.right);
        }
        return near;
    }            // a nearest neighbor to point p; null if the symbol table is empty
    public double distance(Point2D p, Point2D x ){
        double xdistance = Math.abs(x.x()-p.x());
        double ydistance = Math.abs(x.y()-p.y());
        double totalDistance= Math.sqrt(Math.pow(xdistance, 2)+Math.pow(ydistance, 2));
        return totalDistance;
    }
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

/*
Input:
5
0.7 0.2
0.5 0.4
0.2 0.3
0.4 0.7
0.9 0.6

Output:
isEmpty(): true
size(): 0
contains(0.9, 0.6): true
All points in pst: (0.7, 0.2) (0.5, 0.4) (0.9, 0.6) (0.2, 0.3) (0.4, 0.7) 
All points in range(0.2, 0.2, 0.7, 0.7): (0.5, 0.4) (0.4, 0.7) 
nearest(0.5, 0.5): (0.5, 0.4)
  */