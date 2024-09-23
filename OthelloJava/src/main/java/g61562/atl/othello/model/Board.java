package g61562.atl.othello.model;

/**
 * The Board class represents the game board for Othello.
 * It contains methods to manage the board, such as initializing the board, validating size, getting color at a position,
 * adding a piece, setting color at a position, and getting the size of the board.
 */
public class Board {
    private static final int DEFAULT_SIZE = 8;
    private static final int MIN_SIZE = 4;
    private static final int MAX_SIZE = 14;
    private final Color[][] board;

    /**
     * Constructs a board with the default size.
     */
    Board() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructs a board with the specified size.
     *
     * @param size The size of the board.
     * @throws IllegalArgumentException if the size is not within the allowed range.
     */
    Board(int size) {
        validateSize(size);
        this.board = new Color[size][size];
        initBoard();
    }

    private void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("The size of board must be between " + MIN_SIZE + " and " + MAX_SIZE + "!");
        }
    }

    private void initBoard() {
        int size = board.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = null;
            }
        }
        int mid = size / 2;
        board[mid - 1][mid - 1] = Color.WHITE;
        board[mid - 1][mid] = Color.BLACK;
        board[mid][mid - 1] = Color.BLACK;
        board[mid][mid] = Color.WHITE;
    }

    public Color getColor(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            throw new IllegalArgumentException("Invalid position!");
        }
        return board[row][col];
    }

    public int getSize() {
        return board.length;
    }

    /**
     * Adds a piece of the specified color at the specified position on the board.
     *
     * @param row   The row index.
     * @param col   The column index.
     * @param color The color of the piece to be added.
     */
    void addPiece(int row, int col, Color color) {
        board[row][col] = color;
    }

    /**
     * Sets the color of the piece at the specified position on the board.
     *
     * @param row   The row index.
     * @param col   The column index.
     * @param color The color to be set.
     */
    void setColor(int row, int col, Color color) {
        board[row][col] = color;
    }


}

