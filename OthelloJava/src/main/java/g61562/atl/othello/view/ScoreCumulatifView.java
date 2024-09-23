package g61562.atl.othello.view;

import g61562.atl.othello.model.Game;
import g61562.atl.othello.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/**
 * The ScoreCumulatifView class represents the view for displaying cumulative scores in an Othello game.
 * It implements the Observer interface to receive updates from the Game model.
 */
public class ScoreCumulatifView  extends VBox implements Observer {
    private final Game game;
    private final Stage stage;
    private final Label scoreLabelWhite;
    private final Label scoreLabelBlack;

    /**
     * Constructs a ScoreCumulatifView with the specified Game instance.
     *
     * @param game The Game instance associated with this view.
     */
    public ScoreCumulatifView(Game game) {
        this.game = game;

        scoreLabelBlack = new Label();
        scoreLabelWhite = new Label();
        updateScoreLabel(); // Mettre à jour le score initial
        VBox score = new VBox(scoreLabelBlack,scoreLabelWhite);
        Scene scene = new Scene(score);
        stage = new Stage();
        stage.setTitle("ScoreCummulatif");
        score.setStyle("-fx-background-color: #f6d2c5;;");
        stage.setScene(scene);
        // observateur du jeu
        game.register(this);
    }

    /**
     * Displays the score view.
     */
    public void show() {
        stage.show();
    }

    /**
     * Closes the score view.
     */
    public void close() {
        stage.close();
    }

    @Override
    public void update() {
        updateScoreLabel();
    }
    private void updateScoreLabel() {
        scoreLabelWhite.setText("Score cumulatif du joueur WHITE : " + game.getWhiteCountCummulatif());
        scoreLabelBlack.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        scoreLabelBlack.setText("Score cumulatif du joueur BLACK : " + game.getBlackCountCummulatif());
        scoreLabelWhite.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
    }
}
