package bearmaps;

import bearmaps.ArrayHeapMinPQ.PriorityNode;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testAdd() {
        List<Integer> list = Arrays.asList(22, 11, 8, 112, 34, 10, 4, 2, 3, 32, 9, 20, 1);
        ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<Integer>();
        for (Integer integer : list) {
            arrayHeapMinPQ.add(integer, (double) integer);
        }
        Object[] result = addFirst(arrayHeapMinPQ.items.toArray());
        PrintHeapDemo.printFancyHeapDrawing(result);
    }

    @Test
    public void testDelete() {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < 10000; ++i) {
            int randInt = (int)(Math.random() * 1000000);
            if (!list.contains(randInt))
                list.add(randInt);
        }

        ExtrinsicMinPQ<Integer> extrinsicMinPQ = new ArrayHeapMinPQ<Integer>();
        for (Integer integer : list) {
            extrinsicMinPQ.add(integer, (double) integer);
        }

        for (int i = 0; i < list.size(); ++i) {
            list1.add(extrinsicMinPQ.removeSmallest());
        }

        list.sort(Comparator.comparingInt(x -> x));

        assertEquals(list, list1);
    }

    @Test
    public void testChangePriority() {
        List<Integer> list = new ArrayList<>();
        for (int i = 11; i > 0; --i) {
            int randInt = i;//(int)(Math.random() * 1000000);
            if (!list.contains(randInt))
                list.add(randInt);
        }

        ArrayHeapMinPQ<Integer> arrayHeapMinPQ = new ArrayHeapMinPQ<Integer>();
        for (Integer integer : list) {
            arrayHeapMinPQ.add(integer, (double) integer);
        }
        arrayHeapMinPQ.changePriority(5, 0.5);
        arrayHeapMinPQ.changePriority(1, 11.);
        Object[] result = addFirst(arrayHeapMinPQ.items.toArray());
        PrintHeapDemo.printFancyHeapDrawing(result);
    }

    @Test
    public void testTime() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1000000; i > 0; --i) {
                list.add(i);
        }

        long t1 = System.currentTimeMillis();
//        ExtrinsicMinPQ<Integer> heapMinPQ = new NaiveMinPQ<>(); //more than 10min
//        ExtrinsicMinPQ<Integer> heapMinPQ = new ArrayHeapMinPQ<Integer>(); //2.452s
        ExtrinsicMinPQ<Integer> heapMinPQ = new TreeMinPQ<>(); //0.594s
        for (Integer integer : list) {
            heapMinPQ.add(integer, (double) integer);
        }

        for (int i = 0; i < list.size(); ++i) {
            heapMinPQ.removeSmallest();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (t2 - t1)/1000.0 +  " seconds.");

    }

    private Object[] addFirst(Object[] objects) {
        Object[] result = new Object[objects.length + 1];
        result[0] = objects[0];
        System.arraycopy(objects, 0, result, 1, objects.length);
        return result;
    }

}
