package hw2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Objects;

public class Percolation {

    private final static int[] dr = new int[]{-1, 1, 0, 0};
    private final static int[] dc = new int[]{0, 0, -1, 1};

    private final int N;
    private int count;
    private boolean[] isOpen;
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF unionFindNoBottom;

    public Percolation(int N) {
        if (N < 0)
            throw new IllegalArgumentException("N shounld greater than 0");
        this.N = N;
        isOpen = new boolean[N*N];
        unionFind = new WeightedQuickUnionUF(N*N + 2);
        unionFindNoBottom = new WeightedQuickUnionUF(N*N + 2);
        int bottomOffset = N*(N - 1);
        for (int i = 0; i < N; ++i) {
            unionFind.union(i, topNodeIndex());
            unionFindNoBottom.union(i, topNodeIndex());
            unionFind.union(i + bottomOffset, bottomNodeIndex());
        }
    }

    public void open(int row, int col) {
        if (isOpen(row, col))
            return;
        int index = coordinateToIndex(row, col);
        isOpen[index] = true;
        count += 1;
        for (int i = 0; i < 4; ++i) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            if (checkCoordinate(newRow, newCol) && isOpen(newRow, newCol)) {
                unionFind.union(index, coordinateToIndex(newRow, newCol));
                unionFindNoBottom.union(index, coordinateToIndex(newRow, newCol));
            }
        }
    }

    public boolean isOpen(int row, int  col) {
        if (!checkCoordinate(row, col))
            throw new IndexOutOfBoundsException(String.format("coordinate out of range (%d, %d)", row, col));
        int index = coordinateToIndex(row, col);
        return isOpen[index];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && unionFindNoBottom.connected(topNodeIndex(), coordinateToIndex(row, col));
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return unionFind.connected(topNodeIndex(), bottomNodeIndex());
    }

    int coordinateToIndex(int row, int col) {
        return row * N + col;
    }

    int topNodeIndex() {
        return N*N;
    }

    int bottomNodeIndex() {
        return N*N + 1;
    }

    Coordinate indexToCoordinate(int index) {
        int row = index / N;
        int col = index % N;
        return new Coordinate(row, col);
    }

    boolean checkCoordinate(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static class Coordinate {
        int row;
        int col;

        Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinate)) return false;
            Coordinate that = (Coordinate) o;
            return row == that.row &&
                    col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public static void main(String[] args) {

    }
}
