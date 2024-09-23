package g61562.atl.othello.view;


import g61562.atl.othello.model.Game;
import g61562.atl.othello.utils.Observer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewFx class represents the main view of the Othello game.
 * It displays the game board, game information, and game control buttons.
 */
public class ViewFx extends VBox implements Observer {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private Stage primaryStage;
    private BoardView boardView;
    private final GameInfo gameInfo;
    private final Game game;
    private Label errorLabel;
    private List<ScoreCumulatifView> scoreViews = new ArrayList<>();


    /**
     * Constructs a ViewFx object with the specified game.
     *
     * @param game The game to be displayed.
     */
    public ViewFx(Game game) {
        this.game = game;
        System.out.println(game);
        this.gameInfo = new GameInfo(game);
        game.register(this);
        initializeView();
    }

    private void initializeView() {
        setSpacing(10);
        setPadding(new Insets(10));
    }

    void displayGame(Stage stage) {
        this.primaryStage = stage;
        Buttons buttons = new Buttons(this);
        HBox errorBox = new HBox();
        boardView = new BoardView(game.getBoard(), game);
        errorLabel = boardView.getErrorLabel();
        errorBox.getChildren().add(errorLabel);
        errorBox.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f6d2c5;;");
        getChildren().addAll(gameInfo, errorBox, boardView, buttons);
        Scene scene = new Scene(this, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        scene.setFill(Color.ROYALBLUE);
        primaryStage.show();
    }

    void handleGiveUp() {
        Platform.exit();
    }

    void handleUndo() {
        game.undo();
    }

    void handleRedo() {
        game.redo();
    }

    void handleResetGame() {
        primaryStage.close();
        for (ScoreCumulatifView view : scoreViews) {
            view.close();
        }
        UserInput userInput = new UserInput();
        userInput.display(primaryStage);
    }

    void handleCumulatifScore() {
        ScoreCumulatifView newScoreView = new ScoreCumulatifView(game);
        newScoreView.show();
        scoreViews.add(newScoreView);
    }

    @Override
    public void update() {
        gameInfo.updateCurrentPlayer(game.getCurrentPlayer());
        boardView.updateBoardView();
        gameInfo.updateScore(game.getWhiteCount(), game.getBlackCount());
        if (game.isGameOver()) {
            gameInfo.handleGameOver(game.getWinner());
        }else {
            gameInfo.resetTexGameOver();
        }
    }
}