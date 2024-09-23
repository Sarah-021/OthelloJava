package g61562.atl.othello.strategy;

import g61562.atl.othello.model.Color;
import g61562.atl.othello.model.Game;
import g61562.atl.othello.model.Position;

/**
 * The ComputerAdversary interface represents a strategy for making moves in the Othello game.
 * Implementing classes must define a method to choose a move based on the current game state and color.
 */
public interface ComputerAdversary {
    Position chooseMove(Game game, Color color);
}
