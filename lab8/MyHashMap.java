import java.nio.channels.MulticastChannel;
import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    final static int DEFAULT_CAPACITY = 16;
    final static float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    final float loadFactor;
    int capacity;
    int size;

    static class Entry<K, V> implements Map.Entry<K, V> {

        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V returnValue = this.value;
            this.value = value;
            return returnValue;
        }
    }

    Entry<K, V>[] table;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, float loadFactor) {
        this.capacity = initialSize;
        this.loadFactor = loadFactor;
        table = newTable(capacity);
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        capacity = DEFAULT_CAPACITY;
        table = newTable(DEFAULT_CAPACITY);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(hash(key), capacity);
        return getHelper(table[index], key);
    }

    private V getHelper(Entry<K, V> entry, K key) {
        if (entry == null)
            return null;
        if (entry.key.equals(key))
            return entry.value;
        return getHelper(entry.next, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if ((float)size / capacity > loadFactor) {
            resize(capacity << 1);
        }
        int index = Math.floorMod(hash(key), capacity);
        table[index] = putHelper(table[index], newEntry(hash(key), key, value, null));
    }

    private Entry<K, V> putHelper(Entry<K, V> entry, Entry<K, V> target) {
        if (entry == null) {
            size += 1;
            return target;
        }
        if (entry.key.equals(target.key)) {
            entry.value = target.value;
            return entry;
        }
        entry.next = putHelper(entry.next, target);
        return entry;
    }

    void resize(int capacity) {
        capacity = tableSizeFor(capacity);
        Entry<K, V>[] resizeTable = newTable(capacity);
        int size = this.size;

        for (Entry<K, V> entry : table) {
            if (entry == null)
                continue;
            while (entry != null) {
                Entry<K, V> next = entry.next;
                entry.next = null;
                int index = Math.floorMod(entry.hash, capacity);
                resizeTable[index] = putHelper(resizeTable[index], entry);
                entry = next;
            }
        }

        table = resizeTable;
        this.size = size;
        this.capacity = capacity;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }
    
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
        for (EntryIterator iter = new EntryIterator(); iter.hasNext();) {
            Entry<K, V> entry = iter.next();
            set.add(entry);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    public class KeyIterator implements Iterator<K> {

        EntryIterator entryIterator = new EntryIterator();

        @Override
        public boolean hasNext() {
            return entryIterator.hasNext();
        }

        @Override
        public K next() {
            return entryIterator.next().getKey();
        }
    }

    public class EntryIterator implements Iterator<Entry<K, V>> {

        int count = 0;
        int index = 0;
        Entry<K, V> currentEntry = table[0];

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Entry<K, V> next() {
            count += 1;
            while (currentEntry == null) {
                index += 1;
                currentEntry = table[index];
            }
            Entry<K, V> dest = currentEntry;
            currentEntry = currentEntry.next;
            return dest;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V>[] newTable(int size) {
        return (Entry<K, V>[]) new Entry[size];
    }

    private Entry<K, V> newEntry(int hash, K key, V value, Entry<K, V> next) {
        return new Entry<>(hash, key, value, next);
    }

    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


}
