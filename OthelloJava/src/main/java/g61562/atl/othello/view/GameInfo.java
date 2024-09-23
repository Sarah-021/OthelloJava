package g61562.atl.othello.view;

import g61562.atl.othello.model.Color;
import g61562.atl.othello.model.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * This class represents the game information panel in the Othello game interface.
 * It displays information such as scores, current player, and game over status.
 */
public class GameInfo extends HBox {
    private final Label whiteScoreLabel;
    private final Label blackScoreLabel;
    private final Label currentPlayerLabel;
    private final Label gameOverLabel;

    public GameInfo(Game game) {
        whiteScoreLabel = new Label("White Score: 2");
        blackScoreLabel = new Label("Black Score: 2");
        currentPlayerLabel = new Label("Current Player: " + game.getCurrentPlayer());
        gameOverLabel = new Label();
        custumizeLabel();
        HBox hboxLabel = creationHBoxLabel();
        HBox hboxErrorGameOverLabel = creationHBoxErrorGameOverLabel();
        VBox vbox = creationVBoxAll(hboxLabel, hboxErrorGameOverLabel);
        setAlignment(Pos.CENTER);
        getChildren().addAll(vbox);
    }

    private static VBox creationVBoxAll(HBox hboxLabel, HBox hboxErrorGameOverLabel) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hboxLabel, hboxErrorGameOverLabel);
        return vbox;
    }

    private HBox creationHBoxErrorGameOverLabel() {
        HBox HboxErrorGameOverLabel = new HBox();
        Label errorLabel = new Label();
        HboxErrorGameOverLabel.getChildren().addAll(errorLabel, gameOverLabel);
        return HboxErrorGameOverLabel;
    }

    private HBox creationHBoxLabel() {
        HBox hboxLabel = new HBox();
        hboxLabel.setSpacing(10);
        hboxLabel.setAlignment(Pos.CENTER);
        hboxLabel.getChildren().addAll(whiteScoreLabel, blackScoreLabel, currentPlayerLabel);
        return hboxLabel;
    }

    private void custumizeLabel() {
        gameOverLabel.setTextFill(javafx.scene.paint.Color.RED);
        whiteScoreLabel.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        blackScoreLabel.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        currentPlayerLabel.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
    }

    /**
     * Updates the displayed scores.
     *
     * @param whiteCount The score of the white player.
     * @param blackCount The score of the black player.
     */
    public void updateScore(int whiteCount, int blackCount) {
        whiteScoreLabel.setText("White Score: " + whiteCount);
        blackScoreLabel.setText("Black Score: " + blackCount);
    }

    /**
     * Updates the displayed current player.
     *
     * @param currentPlayer The color of the current player.
     */
    public void updateCurrentPlayer(Color currentPlayer) {
        currentPlayerLabel.setText("Current Player: " + currentPlayer);
    }

    /**
     * Handles the game over event.
     *
     * @param winner The color of the winning player, or null if it's a draw.
     */
    public void handleGameOver(Color winner) {
        if (winner != null) {
            if (winner == Color.BLACK) {
                gameOverLabel.setText("Game Over! Winner: " + winner);
                gameOverLabel.setStyle("-fx-text-fill: #c53cf3; -fx-font-weight: bold; -fx-font-size: 16px;");
            } else {
                gameOverLabel.setText("Game Over! Winner: " + winner);
                gameOverLabel.setStyle("-fx-text-fill: #88001a; -fx-font-weight: bold; -fx-font-size: 16px;");
            }
        } else {
            gameOverLabel.setText("Game Over!");
            gameOverLabel.setStyle("-fx-text-fill: #88001a; -fx-font-weight: bold; -fx-font-size: 20px;");
        }
        gameOverLabel.setTextAlignment(TextAlignment.CENTER);
    }


    /**
     * Resets the game over label.
     * Sets the text of the gameOverLabel to an empty string.
     */
    public void resetTexGameOver() {
        gameOverLabel.setText("");
    }
}
