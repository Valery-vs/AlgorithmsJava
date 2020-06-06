/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private Stack<Board> boardMoves;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.boardMoves = new Stack<Board>();
        this.solve(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!this.solvable) {
            return -1;
        }

        int moves = this.boardMoves.size();
        if (moves > 0) {
            moves--;
        }

        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!this.solvable) {
            return null;
        }

        return this.boardMoves;
    }

    private void solve(Board initial) {
        MinPQ<BoardKey> minPQ = new MinPQ<BoardKey>(new BoardKeyCompartor());
        MinPQ<BoardKey> twinPQ = new MinPQ<BoardKey>(new BoardKeyCompartor());
        minPQ.insert(new BoardKey(0, initial, null));
        twinPQ.insert(new BoardKey(0, initial.twin(), null));

        BoardKey finalPath = null;
        while (finalPath == null) {
            finalPath = this.makeMove(minPQ);
            BoardKey twinMove = this.makeMove(twinPQ);
            if (twinMove != null) {
                return;
            }
        }

        this.solvable = true;
        while (finalPath != null) {
            this.boardMoves.push(finalPath.getBoard());
            finalPath = finalPath.getPrevMove();
        }
    }

    private BoardKey makeMove(MinPQ<BoardKey> pq) {
        BoardKey newMin = pq.delMin();

        if (newMin.getBoard().isGoal()) {
            return newMin;
        }

        int newMove = newMin.getMoves() + 1;
        for (Board board : newMin.getBoard().neighbors()) {
            if (newMin.getPrevMove() != null && board.equals(newMin.getPrevMove().getBoard())) {
                continue;
            }

            BoardKey key = new BoardKey(newMove, board, newMin);
            pq.insert(key);
        }

        return null;
    }

    private class BoardKey {
        private final Board board;
        private int priority;
        private final int moves;
        private final BoardKey prevMove;

        public BoardKey(int move, Board board, BoardKey prev) {
            this.board = board;
            this.prevMove = prev;
            this.moves = move;
            this.setPriority(move + board.manhattan());
        }

        public Board getBoard() {
            return this.board;
        }

        public int getPriority() {
            return this.priority;
        }

        public int getMoves() {
            return this.moves;
        }

        public BoardKey getPrevMove() {
            return this.prevMove;
        }

        private void setPriority(int priority) {
            this.priority = priority;
        }
    }

    private class BoardKeyCompartor implements Comparator<BoardKey> {
        public int compare(BoardKey o1, BoardKey o2) {
            return Integer.compare(o1.getPriority(), o2.getPriority());
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        
        Board initial = new Board(tiles);

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
