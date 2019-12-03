import java.util.*;

public class LinkedListDeque<T> implements Deque<T> {
    
    private class Node {
        Node prev;
        Node next;
        T item;

        void connect(Node other) {
            next = other;
            other.prev = this;
        }
    }

    private Node sentinel;
    int size;

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.connect(sentinel);
        size = 0;
    }

    public LinkedListDeque(Deque<? extends T> other) {
        sentinel = new Node();
        sentinel.connect(sentinel);
        size = 0;
        for (T item: other) {
            addLast(item);
        }
    }

    @Override
    public void addFirst(T item) {
        sentinel = addNode();
        sentinel.next.item = item;
        ++size;
    }

    @Override
    public void addLast(T item) {
        addNode().item = item;
        ++size;
    }

    private Node addNode() {
        Node last = sentinel.prev;
        Node newNode = new Node();
        last.connect(newNode);
        newNode.connect(sentinel);
        return newNode;
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
        Node first = sentinel.next;
        removeNode(first);
        return first.item;
    }

    @Override
    public T removeLast() {
        Node last = sentinel.prev;
        removeNode(last);
        return last.item;
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.connect(next);
        --size;
    }

    @Override
    public T get(int index) {
        Node cursor = sentinel.next;
        for (int i = 0; i < index; ++i) {
            cursor = cursor.next;
        }
        return cursor.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }
    
    private class Itr implements Iterator<T> {
        Node cursor;

        Itr() { cursor = sentinel.next; }

        @Override
        public boolean hasNext() {
            return cursor != sentinel;
        }

        @Override
        public T next() {
            T item = cursor.item;
            cursor = cursor.next;
            return item;
        }
    }
}
