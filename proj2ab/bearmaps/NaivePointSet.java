package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> pointList;

    public NaivePointSet(List<Point> points) {
        if (points.size() < 1) {
            throw new IllegalArgumentException("List<Point> has less than one point!");
        }
        pointList = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point dest = new Point(x, y);
        double minDistance = Double.MAX_VALUE;
        Point minPoint = null;
        for (Point point : pointList) {
            double distance = Point.distance(point, dest);
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }
        return minPoint;
    }
}
