package g61562.atl.othello.model;

import g61562.atl.othello.strategy.*;
import g61562.atl.othello.utils.Command;
import g61562.atl.othello.utils.CommandManager;
import g61562.atl.othello.utils.Observable;
import g61562.atl.othello.utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game logic for Othello.
 * This class manages the game state, including the board, players, and game rules.
 */
public class Game implements Observable {
    private final Board board;
    private Color currentPlayer;
    private ComputerAdversary opponentAdversary;
    private final Level level;
    private int whiteCount;
    private int blackCount;
    private int whiteCountCummulatif =2;
    private int blackCountCummulatif =2;
    private final CommandManager commandManager;
    private final List<Observer> observers;
    private final boolean againstComputer;
    private boolean giveUp;

    /**
     * Initializes a new instance of the Game class with default settings.
     */
    public Game() {
        this(8, Level.EASY, false);
    }

    /**
     * Initializes a new instance of the Game class with custom settings.
     *
     * @param size            The size of the board.
     * @param level           The level of difficulty.
     * @param againstComputer Indicates whether the game is against a computer opponent.
     */
    public Game(int size, Level level, boolean againstComputer) {
        this.board = new Board(size);
        this.currentPlayer = Color.BLACK;
        this.level = level;
        this.againstComputer = againstComputer;
        this.commandManager = new CommandManager();
        if (level == Level.EASY) {
            opponentAdversary = new RandomComputerAdversary();
        } else if (level == Level.HARD) {
            opponentAdversary = new HardComputerAdversary();
        }
        this.observers = new ArrayList<>();
    }


    /**
     * Checks if the game is against a computer opponent.
     *
     * @return True if the game is against a computer opponent, otherwise false.
     */
    public boolean isAgainstComputer() {
        return againstComputer;
    }

    public Level getLevel() {
        return level;
    }

    public Board getBoard() {
        return board;
    }

    public int getBlackCountCummulatif() {
        return blackCountCummulatif;
    }

    public int getWhiteCountCummulatif() {
        return whiteCountCummulatif;
    }

    /**
     * Checks if a move is valid for a given color at the specified position.
     *
     * @param row   The row index of the move.
     * @param col   The column index of the move.
     * @param color The color of the player making the move.
     * @return True if the move is valid, otherwise false.
     */
    public boolean isValidMove(int row, int col, Color color) {
        return !isOccupied(row, col) && capturesOpponent(row, col, color);
    }

    /**
     * Checks if the current player can capture an opponent's piece by examining all directions.
     *
     * @param row                The row index of the position to check.
     * @param col                The column index of the position to check.
     * @param currentPlayerColor The color of the current player.
     * @return True if the current player can capture an opponent's piece in any direction, otherwise false.
     */
    public boolean capturesOpponent(int row, int col, Color currentPlayerColor) { //public for test
        for (Direction direction : Direction.values()) {
            if (capturesInDirection(row, col, currentPlayerColor, direction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current player can capture an opponent's piece in a specific direction.
     *
     * @param row                The row index of the position to check.
     * @param col                The column index of the position to check.
     * @param currentPlayerColor The color of the current player.
     * @param direction          The direction to check for capturing.
     * @return True if the current player can capture an opponent's piece in the specified direction, otherwise false.
     */
    public boolean capturesInDirection(int row, int col, Color currentPlayerColor, Direction direction) {
        Color opponentColor = currentPlayerColor.getOpposite();
        return exploreDirection(row, col, currentPlayerColor, direction);
    }

    /**
     * Calculates the next position in the specified direction from the current position.
     *
     * @param currentPosition The current position.
     * @param direction       The direction to explore.
     * @return The next position in the specified direction.
     */
    private Position getNextPosition(Position currentPosition, Direction direction) {
        int size = board.getSize();
        int x = currentPosition.getRow() + direction.getDx();
        int y = currentPosition.getCol() + direction.getDy();

        return new Position(x, y);
    }

    /**
     * Checks if an opponent's piece is found at the specified position.
     *
     * @param currentPosition The position to check.
     * @param opponentColor   The color of the opponent's pieces.
     * @return True if an opponent's piece is found, otherwise false.
     */

    private boolean isOpponentPieceFound(Position currentPosition, Color opponentColor) {
        return board.getColor(currentPosition.getRow(), currentPosition.getCol()) == opponentColor;
    }

    /**
     * Checks if the current player's piece is found at the specified position.
     *
     * @param currentPosition    The position to check.
     * @param currentPlayerColor The color of the current player's pieces.
     * @return True if the current player's piece is found, otherwise false.
     */
    private boolean isCurrentPlayerPieceFound(Position currentPosition, Color currentPlayerColor) {
        return board.getColor(currentPosition.getRow(), currentPosition.getCol()) == currentPlayerColor;
    }

    /**
     * Explores the specified direction from the given position to determine if opponent's pieces are captured.
     *
     * @param row                The row index of the starting position.
     * @param col                The column index of the starting position.
     * @param currentPlayerColor The color of the current player's pieces.
     * @param direction          The direction to explore.
     * @return True if opponent's pieces are captured in the specified direction, otherwise false.
     */
    private boolean exploreDirection(int row, int col, Color currentPlayerColor, Direction direction) {
        int size = board.getSize();
        Position currentPosition = new Position(row + direction.getDx(), col + direction.getDy());
        boolean foundOpponentPiece = false;
        while (currentPosition.getRow() >= 0 && currentPosition.getRow() < size &&
                currentPosition.getCol() >= 0 && currentPosition.getCol() < size) {
            if (board.getColor(currentPosition.getRow(), currentPosition.getCol()) == null) {
                break; // Case vide
            } else if (isCurrentPlayerPieceFound(currentPosition, currentPlayerColor)) {
                if (foundOpponentPiece) {
                    return true;
                } else {
                    break; // Pas de capture dans cette direction
                }
            } else if (isOpponentPieceFound(currentPosition, currentPlayerColor.getOpposite())) {
                foundOpponentPiece = true;
            }
            currentPosition = getNextPosition(currentPosition, direction);
        }
        return false;
    }


    /**
     * Checks if there is a piece occupied at the specified position.
     *
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if there is a piece occupied at the position, otherwise false.
     */
    public boolean isOccupied(int row, int col) {
        return board.getColor(row, col) == Color.BLACK || board.getColor(row, col) == Color.WHITE;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, otherwise false.
     */
    public boolean isGameOver() {
        if (giveUp) {
            return true;
        }
        if (!hasValidMove(currentPlayer.getOpposite()) && !hasValidMove(currentPlayer)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the count of white pieces on the board.
     *
     * @return The count of white pieces.
     */
    public int getWhiteCount() {
        return whiteCount;
    }

    /**
     * Gets the count of black pieces on the board.
     *
     * @return The count of black pieces.
     */
    public int getBlackCount() {
        return blackCount;
    }

    /**
     * Adds a piece to the board at the specified position for the given color.
     *
     * @param row   The row index of the position.
     * @param col   The column index of the position.
     * @param color The color of the piece to be added.
     */
    public void addPiece(int row, int col, Color color) {
        Command commandAd = new AddCommand(row, col, getCurrentPlayer(), getBoard(), this);
        commandManager.add(commandAd);
        changeColor(row, col, color);
        switchPlayer();
        notifyObservers();
    }

    /**
     * Allows the computer opponent to make a move.
     * This method is called when playing against a computer opponent.
     */
    public void playAgainstComputer() {
        if (!isGameOver()) {
            Position computerMove = chooseOpponentMove(Color.WHITE);
            if (computerMove != null) {
                int row = computerMove.getRow();
                int col = computerMove.getCol();
                addPiece(row, col, Color.WHITE);
            }
        }
    }

    /**
     * Updates the score by counting the number of white and black pieces on the board.
     * This method iterates through each cell on the board, checks its color, and updates the respective count.
     */
    public void updateScore() {
        int size = getBoard().getSize();
        whiteCount = 0;
        blackCount = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Color color = getBoard().getColor(row, col);
                if (color == Color.WHITE) {
                    whiteCount++;
                } else if (color == Color.BLACK) {
                    blackCount++;
                }
            }
        }
    }

    /**
     * Updates the cumulative count of black items.
     *
     * @param blackCount The new cumulative count of black items to set.
     */
    public void setBlackCount(int blackCount) {
        this.blackCountCummulatif = blackCount;
    }

    /**
     * Updates the cumulative count of white items.
     *
     * @param whiteCount The new cumulative count of white items to set.
     */
    public void setWhiteCount(int whiteCount) {
        this.whiteCountCummulatif = whiteCount;
    }

    /**
     * Determines the winner of the game based on the piece count.
     *
     * @return The color of the winning player (Color.WHITE or Color.BLACK), or null if it's a tie.
     */
    public Color getWinner() {
        int whiteCount = 0, blackCount = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getColor(i, j) == Color.WHITE) whiteCount++;
                else if (board.getColor(i, j) == Color.BLACK) blackCount++;
            }
        }
        if (whiteCount > blackCount) return Color.WHITE;
        else if (blackCount > whiteCount) return Color.BLACK;
        else return null;
    }

    /**
     * Gets the color of the current player.
     *
     * @return The color of the current player.
     */
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the color of the current player.
     *
     * @param currentPlayer The color of the current player.
     */
    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Switches the current player.
     */
    public void switchPlayer() {
        if (!isAgainstComputer()) {
            switchHuman();
        } else {
            switchComputer();
        }
        updateScore();
    }

    /**
     * Switches the current player when the game is against computer
     */
    private void switchComputer() {
        if ((currentPlayer == Color.BLACK && hasValidMove(Color.WHITE))
                || (currentPlayer == Color.WHITE && !hasValidMove(Color.BLACK))) {
            currentPlayer = Color.WHITE;
            playAgainstComputer();
        } else if ((currentPlayer == Color.BLACK && !hasValidMove(Color.WHITE)
                || (currentPlayer == Color.WHITE && hasValidMove(Color.BLACK)))) {
            currentPlayer = Color.BLACK;
        }
    }

    /**
     * Switches the current player when the game is not against computer
     */
    private void switchHuman() {
        if ((currentPlayer == Color.BLACK && hasValidMove(Color.WHITE))
                || (currentPlayer == Color.WHITE && !hasValidMove(Color.BLACK))) {
            currentPlayer = Color.WHITE;
        } else if ((currentPlayer == Color.BLACK && !hasValidMove(Color.WHITE))
                || (currentPlayer == Color.WHITE && hasValidMove(Color.BLACK))) {
            currentPlayer = Color.BLACK;
        }
    }

    /**
     * Checks if a player has valid moves.
     *
     * @param color The color of the player to check.
     * @return True if the player has valid moves, otherwise false.
     */
    public boolean hasValidMove(Color color) {
        int size = getBoard().getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (isValidMove(row, col, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Changes the color of opponent's pieces to the current player's color based on the rules of Othello.
     *
     * @param row                The row index of the position to check.
     * @param col                The column index of the position to check.
     * @param currentPlayerColor The color of the current player.
     */
    private void changeColor(int row, int col, Color currentPlayerColor) {
        int size = board.getSize();
        Color opponentColor = currentPlayerColor.getOpposite();
        // Vérification des captures dans toutes les directions
        captureInDirection(row, col, currentPlayerColor, size, opponentColor);
        if(currentPlayer == Color.BLACK) {
            blackCountCummulatif++;
        }else{
            whiteCountCummulatif++;
        }
    }


    private void captureInDirection(int row, int col, Color currentPlayerColor, int size, Color opponentColor) {
        for (Direction direction : Direction.values()) {
            int dx = direction.getDx();
            int dy = direction.getDy();
            boolean foundOpponentPiece = false;
            boolean foundPlayerPiece = false;
            // Initialisation des variables pour suivre l'exploration dans la direction donnée
            int x = row + dx;
            int y = col + dy;
            // Boucle while pour explorer la direction spécifiée
            while (x >= 0 && x < size && y >= 0 && y < size) {
                Color color = board.getColor(x, y);
                if (color == null) {
                    break; // Sortir de la boucle si la case est vide
                } else if (color == currentPlayerColor) {
                    foundPlayerPiece = true; // Marquer qu'une pièce du joueur a été trouvée
                    break; // Sortir de la boucle car il n'y a pas d'encerclement
                } else if (color == opponentColor) {
                    foundOpponentPiece = true; // Marquer qu'une pièce de l'adversaire a été trouvée
                }
                // Mettre à jour les coordonnées pour passer à la case suivante dans la direction spécifiée
                x += dx;
                y += dy;
            }
            if (foundPlayerPiece && foundOpponentPiece) {
                // Si une pièce du joueur et une pièce de l'adversaire ont été trouvées, changer la couleur des pions capturés
                changeColorOpponentCapturedPieces(row, col, currentPlayerColor, opponentColor, foundPlayerPiece, foundOpponentPiece, dx, dy);

            }
        }
    }

    private void changeColorOpponentCapturedPieces(int row, int col, Color currentPlayerColor, Color opponentColor, boolean foundPlayerPiece, boolean foundOpponentPiece, int dx, int dy) {
        int x;
        int y;
        int capturedPiecesCount=0;
        if (foundPlayerPiece && foundOpponentPiece) {
            x = row + dx;
            y = col + dy;
            while (board.getColor(x, y) == opponentColor) {
                board.setColor(x, y, currentPlayerColor);
                x += dx;
                y += dy;
                capturedPiecesCount ++;
            }
        }
        if (currentPlayerColor == Color.WHITE) {
            whiteCountCummulatif += capturedPiecesCount;
        } else if (currentPlayerColor==Color.BLACK){
            blackCountCummulatif += capturedPiecesCount ;
        }
    }


    /**
     * Gets a list of possible moves for a given player.
     *
     * @param color The color of the player.
     * @return A list of possible moves for the player.
     */
    //TODO: mettre dans stratégie ? dans interface
    public List<Position> getPossibleMoves(Color color) {
        List<Position> possibleMoves = new ArrayList<>();
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.getColor(row, col) == null && isValidMove(row, col, color)) {
                    possibleMoves.add(new Position(row, col));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Chooses the opponent's move if playing against a computer opponent.
     *
     * @param color The color of the opponent player.
     * @return The position chosen by the opponent.
     * @throws IllegalStateException If the opponent adversary is not set.
     */
    public Position chooseOpponentMove(Color color) { //public for juste for test
        if (opponentAdversary != null) {
            return opponentAdversary.chooseMove(this, color);
        } else {
            throw new IllegalStateException("Opponent adversary not set");
        }
    }

    /**
     * Notifies all observers that the game state has changed.
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }


    /**
     * Undoes the last move made in the game. If the game is being played against the computer, it continues undoing moves until it's the black player's turn.
     * After undoing, updates the game score and notifies observers.
     */
    public void undo() {
        commandManager.undo();
        if (isAgainstComputer()) {
            while (currentPlayer != Color.BLACK) {
                commandManager.undo();
                updateScore();
            }
        }
        updateScore();
        notifyObservers();
    }

    /**
     * Redoes the last undone move in the game. If the game is being played against the computer, it continues redoing moves until it's the black player's turn or the game is over.
     * After redoing, updates the game score and notifies observers.
     */
    public void redo() {
        commandManager.redo();
        if (isAgainstComputer()) {
            while (currentPlayer != Color.BLACK && !isGameOver()) {
                commandManager.redo();
                updateScore();
            }
        }
        updateScore();
        notifyObservers();
    }
}
