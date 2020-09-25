package bearmaps.proj2ab;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    List<PriorityNode> items;
    Map<T, Integer> map;

    public ArrayHeapMinPQ() {
        items = new ArrayList<PriorityNode>();
        map = new HashMap<T, Integer>();
    }

    private void swap(int i, int j) {
        PriorityNode temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
        map.put(items.get(j).getItem(), j);
        map.put(items.get(i).getItem(), i);
    }

    private int parent(int i) {
        return (i - 1) >> 1;
    }

    private int leftChild(int i) {
        return (i << 1) + 1;
    }

    private int rightChild(int i) {
        return (i << 1) + 2;
    }

    private void swim(int i) {
        int j = parent(i);
        if (j < 0) return;
        int result = items.get(i).compareTo(items.get(j));
        if (result < 0) {
            swap(i, j);
            swim(j);
        }
    }

    private void sink(int i) {
        int j = leftChild(i);
        int k = rightChild(i);
        if (j >= size()) return;
        if (k >= size()) {
            if (items.get(i).compareTo(items.get(j)) > 0)
                swap(i, j);
            return;
        }
        int less = items.get(j).compareTo(items.get(k)) < 0 ? j : k;
        if (items.get(less).compareTo(items.get(i)) < 0) {
            swap(less, i);
            sink(less);
        }
    }

    @Override
    public void add(T item, double priority) {
        if (item == null)
            throw new IllegalArgumentException("item shouldn't be null");
        if (contains(item))
            throw new IllegalArgumentException("item already exist");
        items.add(new PriorityNode(item, priority));
        map.put(item, size() - 1);
        swim(size() - 1);
    }


    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() > 0)
            return items.get(0).getItem();
        return null;
    }

    @Override
    public T removeSmallest() {
        T item = getSmallest();
        if (item == null)
            return item;
        PriorityNode lastNode = items.get(size() - 1);
        items.set(0, lastNode);
        items.remove(size() - 1);
        map.put(lastNode.getItem(), 0);
        map.remove(item);
        sink(0);
        return item;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item))
            throw new NoSuchElementException("no such item: " + item);
        int i = map.get(item);
        items.get(i).setPriority(priority);
        swim(i);
        sink(i);
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
