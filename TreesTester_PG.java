import java.util.Scanner;
class TreesTester_PG {
    public static void main(String[] args) {
        System.out.println("The program starts now. Use test.txt for KdTreeSd");
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
        // PointST testing method is in the PointST.java


    }
}
