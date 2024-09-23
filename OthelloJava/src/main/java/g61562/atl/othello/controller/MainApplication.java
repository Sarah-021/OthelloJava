package g61562.atl.othello.controller;

import g61562.atl.othello.view.UserInput;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class serves as the entry point for the Othello game application.
 * It extends JavaFX's Application class and initializes the user interface.
 */
public class MainApplication extends Application {
    /**
     * The main method of the application.
     * Launches the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        UserInput usrInput = new UserInput();
        usrInput.display(primaryStage);
    }

}

