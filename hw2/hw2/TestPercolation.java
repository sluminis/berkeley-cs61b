package hw2;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class TestPercolation {

    @Test
    public void testUnionFind() {
        WeightedQuickUnionUF unionFind = new WeightedQuickUnionUF(100);
        unionFind.union(1, 99);
        unionFind.union(1, 10);
        assertTrue(unionFind.connected(10, 99));
    }

    @Test
    public void testCoverage() {
        Percolation percolation = new Percolation(10);
        int index = percolation.coordinateToIndex(3, 6);
        assertEquals(36, index);
        Percolation.Coordinate coordinate = new Percolation.Coordinate(3, 6);
        assertEquals(coordinate, percolation.indexToCoordinate(index));
    }
}
