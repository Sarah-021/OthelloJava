package g61562.atl.othello.model;

/**
 * The Position class represents a position on the Othello board, identified by its row and column coordinates.
 */
public class Position {
    private final int row;
    private final int col;

    /**
     * Constructs a Position object with the specified row and column coordinates.
     *
     * @param row The row coordinate.
     * @param col The column coordinate.
     */
    Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
