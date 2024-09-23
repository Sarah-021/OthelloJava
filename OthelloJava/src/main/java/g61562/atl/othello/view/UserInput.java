package g61562.atl.othello.view;

import g61562.atl.othello.strategy.Level;
import g61562.atl.othello.model.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the user input interface for configuring an Othello game.
 * This class provides methods to display input dialogs for selecting game settings such as board size and opponent type.
 */

public class UserInput {
    private static final int MIN_BOARD_SIZE = 4;
    private static final int MAX_BOARD_SIZE = 14;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private boolean againstComputer;

    private Game game;
    private Level level;
    private Label errorLabel;
    private Stage primaryStage;

    public UserInput() {
        game = new Game();
    }

    public void display(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Othello");
        errorLabel = new Label();
        askForGameSize(primaryStage);
        askForGameSizeAndOpponent(primaryStage);
    }

    /**
     * Displays a dialog to select the opponent type (human player, easy computer, or hard computer).
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */

    public void askForGameSizeAndOpponent(Stage primaryStage) {
        Label levelLabel = new Label("Select opponent difficulty level:");
        levelLabel.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");

        Button humanButton = new Button("Human Player");
        Button easyButton = new Button("Easy Computer");
        Button hardButton = new Button("Hard Computer");
        customizeButton(humanButton, easyButton, hardButton);

        handleButtonAction(primaryStage, humanButton, easyButton, hardButton);

        VBox levelOptions = creationVBoxLevelOption(levelLabel, humanButton, easyButton, hardButton);
        Scene scene = new Scene(levelOptions, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Configuration Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonAction(Stage primaryStage, Button humanButton, Button easyButton, Button hardButton) {
        humanButton.setOnAction(event -> {
            againstComputer = false;
            askForGameSize(primaryStage);
        });

        easyButton.setOnAction(event -> {
            againstComputer = true;
            level = Level.EASY;
            askForGameSize(primaryStage);
        });

        hardButton.setOnAction(event -> {
            againstComputer = true;
            level = Level.HARD;
            askForGameSize(primaryStage);
        });
    }

    private static VBox creationVBoxLevelOption(Label levelLabel, Button humanButton, Button easyButton, Button hardButton) {
        VBox levelOptions = new VBox(levelLabel, humanButton, easyButton, hardButton);
        levelOptions.setSpacing(10);
        levelOptions.setAlignment(Pos.CENTER);
        levelOptions.setStyle("-fx-background-color: #f6d2c5;");
        return levelOptions;
    }

    private static void customizeButton(Button... buttons) {
        for (Button button : buttons) {
            button.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        }
    }


    /**
     * Displays a dialog to input the board size for the game.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void askForGameSize(Stage primaryStage) {
        Label sizeLabel = new Label("Board Size (" + MIN_BOARD_SIZE + "-" + MAX_BOARD_SIZE + "):");
        sizeLabel.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        TextField sizeField = new TextField();
        Button startButton = startButton(sizeField);
        VBox vBox = new VBox();
        HBox gameInfo = creationHBoxGameInfo(sizeLabel, sizeField, startButton);
        creationErrorVBox();
        vBox.getChildren().addAll(gameInfo, errorLabel);
        VBox gameConfigurationBox = creationVBoxGameConfiguration(vBox);
        Scene scene = new Scene(gameConfigurationBox);
        gameConfigurationBox.setStyle("-fx-background-color: #f6d2c5;;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button startButton(TextField sizeField) {
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-text-fill: #800080; -fx-font-weight: bold; -fx-font-size: 16px;");
        startButton.setOnAction(e -> handleStartGame(sizeField.getText()));
        return startButton;
    }

    private void creationErrorVBox() {
        VBox errorBox = new VBox();
        errorBox.getChildren().add(errorLabel);
        errorBox.setAlignment(Pos.CENTER);
    }

    private static VBox creationVBoxGameConfiguration(VBox vBox) {
        VBox gameConfigurationBox = new VBox();
        gameConfigurationBox.setSpacing(10);
        gameConfigurationBox.setPadding(new Insets(10));
        gameConfigurationBox.getChildren().addAll(vBox);
        return gameConfigurationBox;
    }

    private static HBox creationHBoxGameInfo(Label sizeLabel, TextField sizeField, Button startButton) {
        HBox gameInfo = new HBox();
        gameInfo.setPadding(new Insets(10));
        gameInfo.setSpacing(10);
        gameInfo.setAlignment(Pos.CENTER);
        gameInfo.getChildren().addAll(sizeLabel, sizeField, startButton);
        return gameInfo;
    }

    /**
     * Parses the input text to extract the board size.
     *
     * @param sizeText The text containing the board size input.
     * @return The parsed board size as an integer, or -1 if the input is invalid.
     * @throws NumberFormatException If the input text cannot be parsed as an integer.
     */
    private int parseBoardSize(String sizeText) throws NumberFormatException {
        if (sizeText.isEmpty()) {
            errorLabel.setText("Please enter a board size.");
            errorLabel.setStyle("-fx-text-fill: #88001a; -fx-font-weight: bold; -fx-font-size: 16px;");
            return -1;
        }
        int size = Integer.parseInt(sizeText);
        if (size < MIN_BOARD_SIZE || size > MAX_BOARD_SIZE) {
            errorLabel.setText("Board size must be between " + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
            errorLabel.setStyle("-fx-text-fill: #88001a; -fx-font-weight: bold; -fx-font-size: 16px;");
            return -1;
        }
        return size;
    }

    /**
     * Handles the start game button click event.
     * Parses the board size input and starts the game with the selected settings.
     *
     * @param sizeText The text containing the board size input.
     */
    private void handleStartGame(String sizeText) {
        try {
            int size = parseBoardSize(sizeText);
            if (size != -1) {
                game = new Game(size, level, againstComputer);
                System.out.println(game);
                ViewFx viewApplication = new ViewFx(game);
                primaryStage.setTitle("Othello");
                viewApplication.displayGame(primaryStage);
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Invalid board size. Please enter a valid integer.");
        }
    }

}
