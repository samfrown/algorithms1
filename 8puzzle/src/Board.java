import java.util.Collections;
import java.util.Comparator;

public class Board {
    private final int[][] blocks;
    private int dimension;

    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        if (blocks == null || blocks.length == 0 || blocks.length != blocks[0].length)
            throw new IllegalArgumentException("blocks should be defined n-by-n array of blocks");
        this.blocks = blocks;
        dimension = blocks.length;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
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
        return this;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension) return false;
        for(int i=0; i<dimension; i++)
            for(int j=0; j<dimension; j++)
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return Collections.emptyList();
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
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
}