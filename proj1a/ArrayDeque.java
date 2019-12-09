import java.util.Arrays;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {

    final static int INIT_SIZE = 8;
    final static int MAX_SIZE = Integer.MAX_VALUE >> 1;
    Object[] items;
    int size;
    int firstIndex;
    int lastIndex;

    public ArrayDeque() {
        items = new Object[INIT_SIZE];
        size = 0;
        firstIndex = 0;
        lastIndex = 0;
    }

    public ArrayDeque(Deque<? extends T> other) {

    }

    private static String boundErrorMessage(int destination) {
        return "Index: " + destination + " is out of bounds";
    }

    private int nextIndex(int i, int step) {
        int capacity = items.length;
        return (i + capacity + step) % capacity;
    }

    private void ensureCapacity(int req) {
        int capacity = items.length;
        if (capacity < req) {
            int newCapacity = Math.max(capacity << 1, req);
            if (newCapacity > MAX_SIZE) {
                throw new RuntimeException("capacity exceed limit");
            }

            Object[] newItems = Arrays.copyOf(items, newCapacity);
            for (int i = 0; i < capacity; ++i) {
                items[i] = null;
            }
            items = newItems;
            firstIndex = 0;
            lastIndex = size;
        }
    }

    private void shrinkCapacity() {
        int capacity = items.length;
        if ((double) size / capacity < 0.25 && size > 15) {
            int newCapacity = capacity >> 1;

            Object[] newItems = Arrays.copyOf(items, newCapacity);
            for (int i = 0; i < capacity; ++i) {
                items[i] = null;
            }
            items = newItems;
            firstIndex = 0;
            lastIndex = size;
        }
    }

    @Override
    public void addFirst(T item) {
        ensureCapacity(size + 1);
        firstIndex = nextIndex(firstIndex, -1);
        items[firstIndex] = item;
        ++size;
    }

    @Override
    public void addLast(T item) {
        ensureCapacity(size + 1);
        items[lastIndex] = item;
        lastIndex = nextIndex(lastIndex, 1);
        ++size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (T item: this) {
            System.out.print(item.toString() + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        T item = getItem(firstIndex);
        items[firstIndex] = null;
        firstIndex = nextIndex(firstIndex, 1);
        --size;
        shrinkCapacity();
        return item;
    }

    @Override
    public T removeLast() {
        lastIndex = nextIndex(lastIndex, -1);
        T item = getItem(lastIndex);
        items[lastIndex] = null;
        --size;
        shrinkCapacity();
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(boundErrorMessage(index));
        }
        int location = nextIndex(firstIndex, index);
        return getItem(location);
    }

    @SuppressWarnings("unchecked")
    private T getItem(int location) {
        return (T) items[location];
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {

        int cursor = firstIndex;

        @Override
        public boolean hasNext() {
            return cursor != lastIndex;
        }

        @Override
        public T next() {
            T next = getItem(cursor);
            cursor = nextIndex(cursor, 1);
            return next;
        }
    }

    public static void main(String[] args) {
        System.out.println((double)1 / 2);
    }
}
