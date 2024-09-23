package g61562.atl.othello.view;

import g61562.atl.othello.model.Board;
import g61562.atl.othello.model.Color;
import g61562.atl.othello.model.Game;
import g61562.atl.othello.strategy.Level;

import java.util.Scanner;

/**
 * The View class represents the user interface of the Othello game.
 */
public class ViewConsole {
    private Game game;
    private final Scanner scanner = new Scanner(System.in);
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_DARK_BLUE = "\u001B[34m";
    private final String ANSI_BOLD = "\u001B[1m";
    private final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_GREEN_BOLD = "\u001B[1;32m";

    /**
     * Runs the Othello game.
     */
    public void run() {
        welcolMessage();
        gameModeChoice();
        game.updateScore();
        displayGameStatus();
        displayGame(game);
        if (game != null) {
            playGame();
            endGame();
        }
    }

    private void gameModeChoice() {
        int gameModeChoice = chooseGameMode();
        if (gameModeChoice == 1) {
            startHumanVsHumanGame();
        } else if (gameModeChoice == 2) {
            startHumanVsComputerGame();
        }
    }

    private void welcolMessage() {
        String welcomeMessage = ANSI_BOLD + ANSI_DARK_BLUE + "Welcome to Othello!" + ANSI_RESET;
        System.out.println();
        System.out.println(welcomeMessage);
    }

    /**
     * Prompts the user to choose the game mode.
     *
     * @return The chosen game mode.
     */
    private int chooseGameMode() {
        int choice;
        do {
            System.out.println("");
            System.out.print(ANSI_BLUE + "Choose game mode:" + ANSI_RESET + "\n");
            System.out.println("1. Human vs Human");
            System.out.println("2. Human vs Computer\n");
            choice = readIntInput(ANSI_BLUE + "Enter your choice: \n" + ANSI_RESET);
            if (choice != 1 && choice != 2) {
                System.out.println(ANSI_RED + "Invalid choice. Please enter 1 or 2." + ANSI_RESET);
            }
        } while (choice != 1 && choice != 2);
        return choice;
    }

    /**
     * Starts a Human vs Human game.
     */
    private void startHumanVsHumanGame() {
        int size = askForBoardSize();
        game = new Game(size, null, false);
    }

    /**
     * Starts a Human vs Computer game.
     */
    private void startHumanVsComputerGame() {
        int difficultyChoice = chooseDifficultyLevel();
        System.out.println();
        int size = askForBoardSize();
        game = new Game(size, difficultyChoice == 1 ? Level.EASY : Level.HARD, true);
    }

    /**
     * Displays the current game status.
     */
    private void displayGameStatus() {
        System.out.print(ANSI_PURPLE + "Current Player: " + ANSI_RESET);
        System.out.println(game.getCurrentPlayer());
        System.out.print(ANSI_PURPLE + "White Score: " + ANSI_RESET);
        System.out.println(game.getWhiteCount());
        System.out.print(ANSI_PURPLE + "Black Score: " + ANSI_RESET);
        System.out.println(game.getBlackCount());
        if (game.isAgainstComputer()) {
            System.out.print(ANSI_PURPLE + "Difficulty: " + ANSI_RESET);
            System.out.println(game.getLevel() == Level.EASY ? "Easy" : "Hard \n");
        } else {
            System.out.println();
        }
    }

    /**
     * Asks the user for the board size.
     *
     * @return The chosen board size.
     */
    private int askForBoardSize() {
        String respond = readStringInput(ANSI_BLUE + "Do you want to choose the size of your othellier?" +
                " Y for yes or anythink else for No" + ANSI_RESET);
        System.out.println();
        if (respond.equalsIgnoreCase("Y")) {
            int size;
            do {
                size = readIntInputInRange(ANSI_BLUE + "Enter the size of the othellier (between 4 and 15): "
                        + ANSI_RESET, 4, 15);
                System.out.println();
                if (size < 4 || size > 15) {
                    System.out.println(ANSI_RED + "Invalid size. Please enter a size between 4 and 15." + ANSI_RESET);
                }
            } while (size < 4 || size > 15);
            return size;
        }
        return 8;
    }

    /**
     * Asks the user to choose the difficulty level.
     *
     * @return The chosen difficulty level.
     */
    private int chooseDifficultyLevel() {
        return readIntInputInRange(ANSI_BLUE + "Choose difficulty level:\n " + ANSI_RESET +
                "1. Easy\n 2. Hard\n" + ANSI_BLUE + "\nEnter your choice: ", 1, 2);
    }

    /**
     * Plays the Othello game.
     */
    private void playGame() {
        while (!game.isGameOver()) {
            try {
                String input = readInput();
                String[] parts = parseInput(input);
                int row = extractRow(parts);
                int col = extractColumn(parts);
                if (validateAndPlayMove(row, col)) {
                    game.addPiece(row, col, game.getCurrentPlayer());
                } else {
                    displayInvalidMoveMessage();
                }
            } catch (NumberFormatException e) {
                displayErrorMessage("Error: Please enter valid integers for row and column.");
            } catch (IllegalArgumentException e) {
                displayErrorMessage(ANSI_RED + e.getMessage() + ANSI_RESET);
            }
            displayGameStatus();
            game.updateScore();
            displayGame(game);
        }
    }

    private String readInput() {
        return readStringInput(ANSI_BLUE + "Enter the row and column where you want to play ('y x'): " + ANSI_RESET);
    }

    private String[] parseInput(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Please enter both row and column separated by a space.");
        }
        return parts;
    }

    private int extractRow(String[] parts) {
        if (!parts[0].matches("\\d+")) {
            throw new IllegalArgumentException("Please enter a valid integer for the row.");
        }
        return Integer.parseInt(parts[0]);
    }

    private int extractColumn(String[] parts) {
        if (!parts[1].matches("\\d+")) {
            throw new IllegalArgumentException("Please enter a valid integer for the column.");
        }
        return Integer.parseInt(parts[1]);
    }

    private void displayInvalidMoveMessage() {
        System.out.println(ANSI_RED + "Invalid move. Please try again." + ANSI_RESET);
        System.out.println();
    }

    private void displayErrorMessage(String message) {
        System.out.println(message);
    }


    /**
     * Validates and plays a move.
     *
     * @param row The row where the player wants to place their piece.
     * @param col The column where the player wants to place their piece.
     * @return True if the move is valid and played successfully, false otherwise.
     */
    private boolean validateAndPlayMove(int row, int col) {
        if (row < 0 || row >= game.getBoard().getSize() || col < 0 || col >= game.getBoard().getSize()) {
            return false;
        }
        if (game.isOccupied(row, col) || !game.isValidMove(row, col, game.getCurrentPlayer())) {
            return false;
        }
        return true;
    }

    /**
     * Displays the end of the game message.
     */
    private void endGame() {
        Color winner = game.getWinner();
        displayWinner(winner);
        scanner.close();
    }

    private String readStringInput(String message) {
        System.out.println(message);
        return scanner.nextLine().trim();
    }

    private int readIntInput(String message) {
        System.out.print(message);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Please enter a valid integer." + ANSI_RESET);
            }
        }
    }


    private int readIntInputInRange(String message, int min, int max) {
        int input;
        do {
            input = readIntInput(message);
            if (input < min || input > max) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            }
        } while (input < min || input > max);
        return input;
    }

    /**
     * Displays the current state of the game board.
     *
     * @param game The current game state.
     */
    public void displayGame(Game game) {
        Board board = game.getBoard();
        int size = board.getSize();
        System.out.print("  ");
        for (int col = 0; col < size; col++) {
            System.out.print(col + " ");
        }
        System.out.println();
        printBoard(size, board);
        System.out.println();
    }

    private void printBoard(int size, Board board) {
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
                if (board.getColor(i, j) == null) {
                    // If the cell is empty, print a gray dot
                    System.out.print("\u001B[42m  \u001B[0m");
                } else if (board.getColor(i, j) == Color.BLACK) {
                    // If the cell is occupied by a black piece, print a black piece
                    System.out.print("\u001B[42m\u001B[30m● \u001B[0m");
                } else if (board.getColor(i, j) == Color.WHITE) {
                    // If the cell is occupied by a white piece, print a white piece
                    System.out.print("\u001B[42m● \u001B[0m");
                }
                // Reset the background color after printing the cell
                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }
    }

    /**
     * Displays the winner of the game.
     *
     * @param winner The color of the winner.
     */
    public void displayWinner(Color winner) {
        if (winner == null) {
            System.out.println(ANSI_GREEN_BOLD + "The game ends in a draw." + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN_BOLD + "The winner is " + winner + "!" + ANSI_RESET);
        }
    }
}
