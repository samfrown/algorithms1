import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public final class Solver {

    private final SearchNode lastSearchNode;
    private int moves;
    private final boolean solved;

    private class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode predecessor;
        private final int hamming;
        private final int manhattan;

        SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.moves = moves;
            this.predecessor = predecessor;
            this.hamming = board.hamming();
            this.manhattan = board.manhattan();
        }

        int priorityByHamming() {
            return hamming + moves;
        }

        int priorityByManhattan() {
            return manhattan + moves;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<SearchNode> minPq = new MinPQ<>(Comparator.comparing(SearchNode::priorityByManhattan));
        MinPQ<SearchNode> twinPq = new MinPQ<>(Comparator.comparing(SearchNode::priorityByManhattan));
        minPq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode currentMin = minPq.delMin();
        SearchNode currentTwinMin = twinPq.delMin();
        while (!currentMin.board.isGoal()) {
            if (currentTwinMin.board.isGoal()) {
                lastSearchNode = null;
                solved = false;
                return;
            }
            moves = currentMin.moves + 1;
            for (Board neighbor : currentMin.board.neighbors()) {
                if (currentMin.predecessor == null || !neighbor.equals(currentMin.predecessor.board)) {
                    minPq.insert(new SearchNode(neighbor, moves, currentMin));
                }
            }
            for (Board twinNeighbor : currentTwinMin.board.neighbors()) {
                if (currentTwinMin.predecessor == null || !twinNeighbor.equals(currentTwinMin.predecessor.board)) {
                    twinPq.insert(new SearchNode(twinNeighbor, moves, currentTwinMin));
                }
            }
            currentMin = minPq.delMin();
            currentTwinMin = twinPq.delMin();
        }
        solved = true;
        lastSearchNode = currentMin;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solved ? lastSearchNode.moves : -1;
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if ( lastSearchNode == null )
            return null;
        LinkedStack<Board> solutionBoards = new LinkedStack<>();
        SearchNode current = lastSearchNode;
        while (current != null) {
            solutionBoards.push(current.board);
            current = current.predecessor;
        }
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