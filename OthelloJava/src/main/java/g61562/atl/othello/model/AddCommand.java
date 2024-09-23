package g61562.atl.othello.model;

import g61562.atl.othello.utils.Command;

/**
 * The AddCommand class represents a command to add a piece to the game board.
 * It implements the Command interface.
 */
public class AddCommand implements Command {
    private final int row;

    private final int col;

    private final Color color;
    private final Board board;
    private final Game game;
    private Color[][] saveStateBeforeAdd;
    private Color[][] saveStateAfterAdd;
    private Color previousPlayerBeforeAdd;
    private Color previousPlayerAfterAdd;
    private int previousScoreBlackAfter;
    private int previousScoreWhiteAfter;
    private int previousScoreBlackBefore ;
    private int previousScoreWhiteBefore;
    private static int size;

    /**
     * Constructs an AddCommand object with the specified row, column, color, board, and game.
     *
     * @param row   The row of the piece to be added.
     * @param col   The column of the piece to be added.
     * @param color The color of the piece to be added.
     * @param board The game board.
     * @param game  The game object.
     */
    public AddCommand(int row, int col, Color color, Board board, Game game) {
        this.col = col;
        this.row = row;
        this.color = color;
        this.board = board;
        this.game = game;
        size = board.getSize();
        saveStateBeforeAdd = new Color[size][size];
        saveStateAfterAdd = new Color[size][size];
    }

    public Color getColor() {
        return color;
    }

    private void saveStateGameBeforeAdd() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                saveStateBeforeAdd[i][j] = board.getColor(i, j);
            }
        }
    }

    private void saveStateGameAfterAdd() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                saveStateAfterAdd[i][j] = board.getColor(i, j);
            }
        }
    }

    @Override
    public void execute() { //do
        previousScoreBlackBefore = game.getBlackCountCummulatif();
        previousScoreWhiteBefore = game.getWhiteCountCummulatif();
        saveStateGameBeforeAdd();
        previousPlayerBeforeAdd = game.getCurrentPlayer();
        board.addPiece(row, col, color);
    }

    @Override
    public void unexecute() { //undo
        previousScoreBlackAfter = game.getBlackCountCummulatif();
        previousScoreWhiteAfter = game.getWhiteCountCummulatif();
        saveStateGameAfterAdd();
        previousPlayerAfterAdd = game.getCurrentPlayer();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board.setColor(i, j, saveStateBeforeAdd[i][j]);
            }
        }
        game.setBlackCount(previousScoreBlackBefore);
        game.setWhiteCount(previousScoreWhiteBefore);
        game.setCurrentPlayer(previousPlayerBeforeAdd);
    }

    @Override
    public void reexecute() {  //redo --> reexecute
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board.setColor(i, j, saveStateAfterAdd[i][j]);
            }
        }
        game.setCurrentPlayer(previousPlayerAfterAdd);
        game.setBlackCount(previousScoreBlackAfter);
        game.setWhiteCount(previousScoreWhiteAfter);
    }

}
