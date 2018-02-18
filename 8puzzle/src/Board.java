import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int[][] blocks;
    private final int dimension;
    private final int x0;
    private final int y0;

    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        if (blocks == null || blocks.length == 0 || blocks.length != blocks[0].length)
            throw new IllegalArgumentException("blocks should be defined n-by-n array of blocks");
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        int i0 = 0;
        int j0 = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }
        x0 = i0;
        y0 = j0;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int misplaced = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int expected = i * dimension + j + 1;
                if (blocks[i][j] != 0 && blocks[i][j] != expected) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int totalDistance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int expected = i * dimension + j + 1;
                int val = blocks[i][j];
                if (val != 0 && val != expected ) {
                    int iVal = val / dimension;
                    int jVal = val - dimension * iVal;
                    totalDistance += Math.abs(i - iVal) + Math.abs(j - jVal);
                }
            }
        }
        return totalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int val = i * dimension + j + 1;
                if (blocks[i][j] != val && val < dimension * dimension) {
                    return false;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = copyBlocks();
        while (true) {
            int rand = StdRandom.uniform(dimension * dimension);
            int r = rand / dimension;
            int c = rand - dimension * r;
            if (blocks[r][c] != 0) {
                if (r < (dimension - 1) && blocks[r + 1][c] != 0) {
                    exch(twinBlocks, r, c, r + 1, c);
                } else if (r > 0 && blocks[r - 1][c] != 0) {
                    exch(twinBlocks, r, c, r - 1, c);
                } else if (c < (dimension - 1) && blocks[r][c + 1] != 0) {
                    exch(twinBlocks, r, c, r, c + 1);
                } else if (c > 0 && blocks[r][c - 1] != 0) {
                    exch(twinBlocks, r, c, r, c - 1);
                }
                return new Board(twinBlocks);
            }
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension) return false;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> nbors = new LinkedList<>();
        if (x0 < (dimension - 1)) {
            int[][] copy = copyBlocks();
            exch(copy, x0, y0, x0 + 1, y0);
            nbors.add(new Board(copy));
        }
        if (x0 > 0) {
            int[][] copy = copyBlocks();
            exch(copy, x0, y0, x0 - 1, y0);
            nbors.add(new Board(copy));
        }
        if (y0 < (dimension - 1)) {
            int[][] copy = copyBlocks();
            exch(copy, x0, y0, x0, y0 + 1);
            nbors.add(new Board(copy));
        }
        if (y0 > 0) {
            int[][] copy = copyBlocks();
            exch(copy, x0, y0, x0, y0 - 1);
            nbors.add(new Board(copy));
        }
        return nbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension).append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] goalBlocks = {{1, 2}, {3, 0}};
        int[][] initBlocks = {{2, 1}, {0, 3}};
        Board goalBoard = new Board(goalBlocks);
        if (!goalBoard.isGoal()) {
            System.out.println("FAIL: board should be goal: " + goalBoard);
        }
        Board initBoard = new Board(initBlocks);
        if (initBoard.isGoal()) {
            System.out.println("FAIL: board should NOT be goal: " + initBoard);
        }
        if (goalBoard.equals(initBoard)) {
            System.out.println("FAIL: board should NOT be equal");
        }
        if (!goalBoard.equals(new Board(goalBlocks))) {
            System.out.println("FAIL: board should be equal");
        }
        System.out.println("All tests PASSED");
    }

    private void exch(int[][] array2d, int x1, int y1, int x2, int y2) {
        int val = array2d[x1][y1];
        array2d[x1][y1] = array2d[x2][y2];
        array2d[x2][y2] = val;
    }

    private int[][] copyBlocks() {
        int[][] copyBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(blocks[i], 0, copyBlocks[i], 0, dimension);
        }
        return copyBlocks;
    }

}