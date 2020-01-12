package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class KDTreeTest {

    static List<Point> pointList = new ArrayList<Point>();
    static Point a = new Point(2, 3);
    static Point b = new Point(4, 2);
    static Point c = new Point(4, 5);
    static Point f = new Point(4, 4);
    static Point d = new Point(3, 3);
    static Point e = new Point(1, 5);
    static {
        pointList.add(a);
        pointList.add(b);
        pointList.add(c);
        pointList.add(d);
        pointList.add(e);
        pointList.add(f);
    }

    @Test
    public void testPointSet() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(ret, p2);
    }

    @Test
    public void testRandomDouble() {
        Random random = new Random(424);
        for (int i = 0; i < 100; ++i) {
            System.out.println(random.nextDouble());
        }
    }

    @Test
    public void testKDTreeInitialization() {
        KDTree kdTree = new KDTree(pointList);
        assertEquals(a, kdTree.root.getPoint());
        assertEquals(b, kdTree.root.getRightChild().getPoint());
        assertEquals(c, kdTree.root.getRightChild().getRightChild().getPoint());
        assertEquals(d, kdTree.root.getRightChild().getRightChild().getLeftChild().getPoint());
        assertEquals(f, kdTree.root.getRightChild().getRightChild().getRightChild().getPoint());
        assertEquals(e, kdTree.root.getLeftChild().getPoint());
    }

    @Test
    public void testKDTreeNearestSimple() {
        KDTree kdTree = new KDTree(pointList);
        Point result = kdTree.nearest(0, 7);
        assertEquals(e, result);
    }

    @Test
    public void testKDTreeNearest() {
        List<Point> points = generatePoints(100000, 424);
        List<Point> targets = generatePoints(10000, 357);
        PointSet naivePointSet = new NaivePointSet(points);
        PointSet kDTreeSet = new KDTree(points);
        for (Point target : targets) {
            Point expected = naivePointSet.nearest(target.getX(), target.getY());
            Point actual = kDTreeSet.nearest(target.getX(), target.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testKDTreeSpeed() {
        List<Point> points = generatePoints(10000000, 424);
        List<Point> targets = generatePoints(100000, 357);
        PointSet naivePointSet = new NaivePointSet(points);
        PointSet kDTreeSet = new KDTree(points);
        Stopwatch sw;
        sw = new Stopwatch();
        for (Point target : targets) {
            Point expected = naivePointSet.nearest(target.getX(), target.getY());
        }
        double t1 = sw.elapsedTime();
        System.out.println("NaivePointSet takes " + t1 + "s");
        sw = new Stopwatch();
        for (Point target : targets) {
            Point actual = kDTreeSet.nearest(target.getX(), target.getY());
        }
        double t2 = sw.elapsedTime();
        System.out.println("KDTree takes " + t2 + "s");
        System.out.println("Speed faster " + t1 / t2 + " times");
        assertTrue(t1 / t2 > 30.);

        /*
         * NaivePointSet takes 2978.886s
         * KDTree takes 2.735s
         * Speed faster 1089.1722120658135 times
         *
         * Process finished with exit code 0
         */
    }

    public List<Point> generatePoints(int amount, int seed) {
        Random random = new Random(seed);
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            pointList.add(new Point(random.nextDouble(), random.nextDouble()));
        }

        return pointList;
    }
}
