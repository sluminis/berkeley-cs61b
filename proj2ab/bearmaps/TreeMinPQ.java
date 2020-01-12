package bearmaps;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

public class TreeMinPQ<T> implements ExtrinsicMinPQ<T> {

    TreeSet<PriorityNode> set;

    public TreeMinPQ() {
        set = new TreeSet<PriorityNode>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item))
            throw new IllegalArgumentException("item already exist");
        set.add(new PriorityNode(item, priority));
    }

    @Override
    public boolean contains(T item) {
        return set.contains(new PriorityNode(item, 0));
    }

    @Override
    public T getSmallest() {
        return set.first().getItem();
    }

    @Override
    public T removeSmallest() {
        PriorityNode priorityNode = set.pollFirst();
        if (priorityNode == null)
            return null;
        return priorityNode.getItem();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        set.remove(new PriorityNode(item, 0));
        add(item, priority);
    }

    class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

        @Override
        public String toString() {
            return "[item: " + item + " priority: " + priority + "]";
        }

    }
}
