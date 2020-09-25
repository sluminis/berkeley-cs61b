package bearmaps.proj2ab;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    final static Comparator<Point> comparatorLR = Comparator.comparingDouble(Point::getX);
    final static Comparator<Point> comparatorUD = Comparator.comparingDouble(Point::getY);

    static class TreeNode implements Comparable<TreeNode>{
        final static int TYPE_UNKNOWN = -1;
        final static int TYPE_LR = 0;
        final static int TYPE_UD = 1;

        int type;
        private Point point;

        private TreeNode rightChild;
        private TreeNode leftChild;

        public TreeNode (Point point, int type) {
            this.point = point;
            this.type = type;
        }

        public TreeNode getRightChild() {
            return rightChild;
        }

        public void setRightChild(TreeNode rightChild) {
            rightChild.type = nextType(type);
            this.rightChild = rightChild;
        }

        public TreeNode getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode leftChild) {
            leftChild.type = nextType(type);
            this.leftChild = leftChild;
        }

        public Point getPoint() {
            return point;
        }

        public Point getVerticalPoint(Point target) {
            checkUnknown(this);
            if (TYPE_LR == type) {
                return new Point(point.getX(), target.getY());
            }
            if (TYPE_UD == type) {
                return new Point(target.getX(), point.getY());
            }
            return null;
        }

        @Override
        public int compareTo(TreeNode o) {
            checkUnknown(this);
            if (TYPE_LR == type) {
                return comparatorLR.compare(point, o.point);
            }
            if (TYPE_UD == type) {
                return comparatorUD.compare(point, o.point);
            }
            return 0;
        }

        int nextType(int type) {
            checkUnknown(this);
            if (TYPE_LR == type) {
                return TYPE_UD;
            }
            if (TYPE_UD == type) {
                return TYPE_LR;
            }
            return TYPE_UNKNOWN;
        }

        private void checkUnknown(TreeNode node) {
            if (TYPE_UNKNOWN == node.type)
                throw new IllegalArgumentException("TreeNode's type unknown");
        }
    }

    TreeNode root;

    public KDTree(List<Point> points) {
        if (points.size() < 1) {
            throw new IllegalArgumentException("List<Point> has less than one point!");
        }
        for (Point point : points) {
            add(point);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point point = new Point(x, y);
        TreeNode target = new TreeNode(point, TreeNode.TYPE_UNKNOWN);
        TreeNode best = nearestHelper(root, target, root);
        return best.getPoint();
    }

    private TreeNode nearestHelper(TreeNode root, TreeNode target, TreeNode best) {
        if (root == null)
            return best;
        Point targetPoint = target.getPoint();
        double bestDistance = Point.distance(targetPoint, best.getPoint());
        if (Point.distance(targetPoint, root.getPoint()) < bestDistance) {
            best = root;
        }
        //System.out.println(root.getPoint() + " | " + best.getPoint());
        TreeNode goodSide, badSide;
        if (root.compareTo(target) > 0) {
            goodSide = root.getLeftChild();
            badSide = root.getRightChild();
        } else {
            goodSide = root.getRightChild();
            badSide = root.getLeftChild();
        }
        best = nearestHelper(goodSide, target, best);
        if (bestDistance > Point.distance(targetPoint, root.getVerticalPoint(targetPoint)))
            best = nearestHelper(badSide, target, best);
        return best;
    }

    public void add(Point point) {
        if (point == null)
            throw new IllegalArgumentException("point should not be null");
        if (root == null) {
            root = new TreeNode(point, TreeNode.TYPE_LR);
            return;
        }
        addHelper(root, new TreeNode(point, TreeNode.TYPE_UNKNOWN));
    }

    private void addHelper(TreeNode root, TreeNode child) {
        if (root.compareTo(child) > 0) {
            if (root.getLeftChild() == null) {
                root.setLeftChild(child);
                return;
            }
            addHelper(root.getLeftChild(), child);
        } else {
            if (root.getRightChild() == null) {
                root.setRightChild(child);
                return;
            }
            addHelper(root.getRightChild(), child);
        }
    }
}
