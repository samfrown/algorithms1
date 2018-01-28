import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int OPEN = 1;
    private static final int FULL = 2;
    private final int gridDimension;
    private final WeightedQuickUnionUF quickUnion;
    private final int[] openSites;
    private final int top;
    private final int bottom;
    private int opened = 0;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size '" + n + "' is out of bounds");
        }
        this.gridDimension = n;
        int size = n * n + 2;
        top = size - 2;
        bottom = size - 1;
        quickUnion = new WeightedQuickUnionUF(size);
        openSites = new int[size];
        for (int i = 0; i < size; i++) {
            openSites[i] = 0;
        }
        openSites[top] = FULL;
    }

    /* open site (row, col) if it is not open already */
    public void open(int row, int col) {
        checkIndex(row);
        checkIndex(col);
        if (!isOpen(row, col)) {
            doOpen(row, col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row);
        checkIndex(col);
        return openSites[xyTo1D(row, col)] > 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndex(row);
        checkIndex(col);
        int index = xyTo1D(row, col);

        if (openSites[index] == FULL) {
            return true;
        } else if (openSites[index] == OPEN) {
            if (quickUnion.connected(top, index)) {
                openSites[index] = FULL;
                return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnion.connected(top, bottom);
    }

    private void doOpen(int row, int col) {
        int index = xyTo1D(row, col);
        if (col < gridDimension) {
            connectToNeighbor(index, row, col + 1);
        }
        if (col > 1) {
            connectToNeighbor(index, row, col - 1);
        }
        if (row > 1) {
            connectToNeighbor(index, row - 1, col);
        } else {
            quickUnion.union(index, top);
        }
        if (row < gridDimension) {
            connectToNeighbor(index, row + 1, col);
        } else {
            quickUnion.union(index, bottom);
        }
        openSites[index] = (row == 1) ? FULL : OPEN;
        ++opened;
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * gridDimension + col - 1;
    }

    private void connectToNeighbor(int index, int row, int col) {
        if (isOpen(row, col)) {
            quickUnion.union(index, xyTo1D(row, col));
        }
    }

    private void checkIndex(int i) {
        if (i <= 0 || i > gridDimension) {
            throw new IllegalArgumentException("index '" + i + "' is out of bounds");
        }
    }

}
