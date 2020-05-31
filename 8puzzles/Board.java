/* *****************************************************************************
 *  Name: Valeriy Smirnov
 *  Date: 05/30/2020
 *  Description:
 **************************************************************************** */

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int lastItem;
    private int manhatten;
    private int hamming;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        this.tiles = tiles;
        this.n = tiles.length;
        this.lastItem = this.n * this.n;
        this.hamming = this.getHamming();
        if (this.hamming > 0) {
            this.manhatten = this.getManhattan();
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.n + "\n");
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
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhatten;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || (y.getClass() != Board.class)) {
            return false;
        }

        Board other = (Board) y;
        if (this.isGoal() && other.isGoal()) {
            return true;
        }

        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                if (this.tiles[row][col] != other.tiles[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    private int getHamming() {
        int result = 0;
        for (int row = 0; row < this.tiles.length; row++) {
            for (int col = 0; col < this.tiles[row].length; col++) {
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

                Position rightPosition = this.getRightPosition(item);
                int dRow = Math.abs(rightPosition.Row - row);
                int dCol = Math.abs(rightPosition.Col - col);
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
        if (rightItem == this.lastItem) {
            rightItem = 0;
        }

        return item == rightItem;
    }

    private int getRightItem(int row, int col) {
        return this.n * row + col + 1;
    }

    private Position getRightPosition(int item) {
        if (item == 0) {
            return new Position(this.n - 1, this.n - 1);
        }

        int row = (item - 1) / this.n;
        int col = item - 1;
        if (row > 0) {
            col = (item - 1) - row * this.n;
        }

        return new Position(row, col);
    }

    private final class Position {
        public final int Row;
        public final int Col;

        public Position(int row, int col) {
            this.Row = row;
            this.Col = col;
        }
    }


    public static void main(String[] args) {

    }
}
