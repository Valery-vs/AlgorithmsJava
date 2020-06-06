/* *****************************************************************************
 *  Name: Valeriy Smirnov
 *  Date: 05/30/2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private int hamming;
    private int zeroItemRow;
    private int zeroItemCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }

        this.tiles = copyTiles(board);

        this.hamming = -1;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.dimension() + "\n");
        for (int[] row : this.tiles) {
            for (int col = 0; col < row.length; col++) {
                builder.append(String.format("%2d ", row[col]));
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (this.hamming == -1) {
            this.hamming = this.getHamming();
        }
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.getManhattan();
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.getHamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (this.dimension() != other.dimension()) {
            return false;
        }

        if (this.isGoal() && other.isGoal()) {
            return true;
        }

        for (int row = 0; row < this.dimension(); row++) {
            for (int col = 0; col < this.dimension(); col++) {
                if (this.tiles[row][col] != other.tiles[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (this.hamming == -1) {
            // search zero tile
            this.getHamming();
        }

        Queue<Board> arr = new Queue<Board>();
        if (this.zeroItemCol > 0) {
            int[][] board = this.copyTiles(this.tiles);
            this.swap(board, this.zeroItemRow, this.zeroItemCol, this.zeroItemRow,
                      this.zeroItemCol - 1);
            arr.enqueue(new Board(board));
        }

        if (this.zeroItemCol < this.dimension() - 1) {
            int[][] board = this.copyTiles(this.tiles);
            this.swap(board, this.zeroItemRow, this.zeroItemCol, this.zeroItemRow,
                      this.zeroItemCol + 1);
            arr.enqueue(new Board(board));
        }

        if (this.zeroItemRow > 0) {
            int[][] board = this.copyTiles(this.tiles);
            this.swap(board, this.zeroItemRow, this.zeroItemCol, this.zeroItemRow - 1,
                      this.zeroItemCol);
            arr.enqueue(new Board(board));
        }

        if (this.zeroItemRow < this.dimension() - 1) {
            int[][] board = this.copyTiles(this.tiles);
            this.swap(board, this.zeroItemRow, this.zeroItemCol, this.zeroItemRow + 1,
                      this.zeroItemCol);
            arr.enqueue(new Board(board));
        }

        return arr;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board board = new Board(this.tiles);

        for (int row = 0; row < this.dimension() - 1; row++) {
            for (int col = 0; col < this.dimension() - 1; col++) {
                if (this.tiles[row][col] == 0) {
                    continue;
                }

                if (this.tiles[row][col + 1] != 0) {
                    this.swap(board.tiles, row, col, row, col + 1);
                    return board;
                }

                if (this.tiles[row + 1][col] != 0) {
                    this.swap(board.tiles, row, col, row + 1, col);
                    return board;
                }
            }
        }

        return null;
    }

    private void swap(int[][] board, int position1Row, int position1Col, int position2Row,
                      int position2Col) {
        int swap = board[position1Row][position1Col];
        board[position1Row][position1Col] = board[position2Row][position2Col];
        board[position2Row][position2Col] = swap;
    }

    private int getHamming() {
        int result = 0;
        for (int row = 0; row < this.tiles.length; row++) {
            for (int col = 0; col < this.tiles[row].length; col++) {
                if (this.tiles[row][col] == 0) {
                    this.zeroItemRow = row;
                    this.zeroItemCol = col;
                }

                if (!this.isInRightPlace(row, col)) {
                    result++;
                }
            }
        }

        return result;
    }

    // sum of Manhattan distances between tiles and goal
    private int getManhattan() {
        int result = 0;
        for (int row = 0; row < this.tiles.length; row++) {
            for (int col = 0; col < this.tiles[row].length; col++) {
                int item = this.tiles[row][col];
                if (item == 0) {
                    continue;
                }


                int rightPositionRow = (item - 1) / this.dimension();
                int rightPositionCol = item - 1;
                if (rightPositionRow > 0) {
                    rightPositionCol = (item - 1) - rightPositionRow * this.dimension();
                }

                int dRow = Math.abs(rightPositionRow - row);
                int dCol = Math.abs(rightPositionCol - col);
                result += dRow + dCol;
            }
        }

        return result;
    }

    private boolean isInRightPlace(int row, int col) {
        int item = this.tiles[row][col];
        if (item == 0) {
            return true;
        }

        int rightItem = this.getRightItem(row, col);
        if (rightItem == this.dimension() * this.dimension()) {
            rightItem = 0;
        }

        return item == rightItem;
    }

    private int getRightItem(int row, int col) {
        return this.dimension() * row + col + 1;
    }

    private int[][] copyTiles(int[][] value) {
        int[][] result = new int[value.length][value.length];
        for (int row = 0; row < value.length; row++) {
            for (int col = 0; col < value[row].length; col++) {
                result[row][col] = value[row][col];
            }
        }

        return result;
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
        StdOut.println(initial.toString());
    }
}
