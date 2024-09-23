package g61562.atl.othello.controller;

import g61562.atl.othello.view.ViewConsole;

/**
 * This class serves as the entry point for the Othello game application in console mode.
 * It initializes the console-based user interface and starts the game.
 */
public class MainConsole {

    public MainConsole() {
    }

    /**
     * The main method of the application.
     * Creates a ViewConsole instance and starts the game.
     *
     * @param args The command-line arguments passed to the application (unused).
     */
    public static void main(String[] args) {
        ViewConsole app = new ViewConsole();
        app.run();
    }
}
