package g61562.atl.othello.view;

import g61562.atl.othello.model.Board;
import g61562.atl.othello.model.Color;

import g61562.atl.othello.model.Game;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The BoardView class represents the graphical representation of the Othello board.
 * It contains methods to update the view of the board based on the game state.
 */
public class BoardView extends GridPane {
    private final Board board;
    private final Game game;
    private final Circle[][] circles;
    private final Label errorLabel;
    private static final int RECTANGLE_SIZE = 50;

    /**
     * Constructs a BoardView object with the specified board and game.
     *
     * @param board The Othello board.
     * @param game  The Othello game.
     */
    public BoardView(Board board, Game game) {
        this.board = board;
        this.game = game;
        int size = board.getSize();
        circles = new Circle[size][size];
        setHgap(5);
        setVgap(5);
        setAlignment(Pos.CENTER);
        errorLabel = new Label();
        getChildren().add(errorLabel);
        initializeBoardView();
    }

    private void initializeBoardView() {
        updateBoardView();
    }

    private void handleCircleClick(int row, int col) {
        if (!game.isGameOver()) {
            errorLabel.setText("");
            if (!game.isValidMove(row, col, game.getCurrentPlayer())) {
                errorLabel.setText("Invalid Move. Please try again");
                errorLabel.setStyle("-fx-text-fill: #88001a; -fx-font-weight: bold; -fx-font-size: 16px;");
            } else {
                game.addPiece(row, col, game.getCurrentPlayer());
            }
        }
    }

    /**
     * Retrieves the text from the error label.
     *
     * @return The text from the error label.
     */
    Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Updates the view of the board.
     */
    void updateBoardView() {
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle rect = rectangleCreation(row, col);
                int currentRow = row;
                int currentCol = col;
                rectangleHover(rect, currentRow, currentCol);
                rect.setOnMouseClicked(e -> handleCircleClick(currentRow, currentCol));
                createCircles(row, col);
            }
        }
    }

    private Rectangle rectangleCreation(int row, int col) {
        Rectangle rect = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE);
        rect.setFill(javafx.scene.paint.Color.GREEN);
        this.add(rect, col, row);
        return rect;
    }

    private void rectangleHover(Rectangle rect, int currentRow, int currentCol) {
        // Hover event to change the color of the rectangle."
        rect.setOnMouseEntered(e -> {
            if (!game.isGameOver() && game.isValidMove(currentRow, currentCol, game.getCurrentPlayer())) {
                rect.setFill(javafx.scene.paint.Color.LIGHTGREEN);
            } else {
                rect.setFill(javafx.scene.paint.Color.LIGHTCORAL);
            }
        });
        // Reset the color when the cursor leaves the rectangle
        rect.setOnMouseExited(e -> {
            rect.setFill(javafx.scene.paint.Color.GREEN);
        });

    }

    private void createCircles(int row, int col) {
        Color color = board.getColor(row, col);
        if (color != null) {
            Circle circle = new Circle(20);
            circles[row][col] = circle;
            if (color == Color.BLACK) {
                circle.setFill(javafx.scene.paint.Color.BLACK);
            } else if (color == Color.WHITE) {
                circle.setFill(javafx.scene.paint.Color.WHITE);
            }
            setRowIndex(circle, row);
            setColumnIndex(circle, col);
            setHalignment(circle, javafx.geometry.HPos.CENTER);
            this.add(circle, col, row);
        }
    }
}
