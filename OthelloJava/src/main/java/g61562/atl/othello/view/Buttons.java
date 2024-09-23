package g61562.atl.othello.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * This class represents the control buttons panel in the Othello game interface.
 * It provides buttons for actions such as giving up, undoing moves, redoing moves, and resetting the game.
 */
public class Buttons extends HBox {
    private final Button giveUpButton;
    private final Button undoButton;
    private final Button redoButton;
    private final Button resetGameButton;
    private final Button cumulatifScoreButton;

    public Buttons(ViewFx ViewFx) {
        giveUpButton = new Button("Give Up");
        undoButton = new Button("Undo");
        redoButton = new Button("Redo");
        resetGameButton = new Button("Reset Game");
        cumulatifScoreButton = new Button("Cumulatif Score");
        customizeButtons();
        giveUpButton.setOnAction(e -> ViewFx.handleGiveUp());
        undoButton.setOnAction(e -> ViewFx.handleUndo());
        redoButton.setOnAction(e -> ViewFx.handleRedo());
        resetGameButton.setOnAction(e -> ViewFx.handleResetGame());
        cumulatifScoreButton.setOnAction(e->ViewFx.handleCumulatifScore());
        setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(giveUpButton, undoButton, redoButton, resetGameButton,cumulatifScoreButton);
    }

    private void customizeButtons() {
        giveUpButton.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        undoButton.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        redoButton.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        resetGameButton.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        cumulatifScoreButton.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
    }
}
