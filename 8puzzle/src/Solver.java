import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Solver {

    private final List<Board> solutionBoards;
    private final MinPQ<SearchNode> minPq;
    private int moves;
    private boolean solved;

    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode predecessor;

        SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.moves = moves;
            this.predecessor = predecessor;
        }

        int priorityByHamming() {
            return board.hamming() + moves;
        }

        int priorityByManhattan() {
            return board.manhattan() + moves;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        minPq = new MinPQ<>(Comparator.comparing(SearchNode::priorityByHamming));
        minPq.insert(new SearchNode(initial, 0, null));
        solutionBoards = new ArrayList<>();
        solutionBoards.add(initial);

        SearchNode currentMin = minPq.delMin();
        while (!currentMin.board.isGoal()) {
            moves++;
            for (Board neighbor : currentMin.board.neighbors()) {
                if (currentMin.predecessor == null || !neighbor.equals(currentMin.predecessor.board)) {
                    minPq.insert(new SearchNode(neighbor, moves, currentMin));
                }
            }
            currentMin = minPq.delMin();
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionBoards;
    }


    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}