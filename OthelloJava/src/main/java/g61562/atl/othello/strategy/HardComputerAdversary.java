package g61562.atl.othello.strategy;

import g61562.atl.othello.model.Color;
import g61562.atl.othello.model.Direction;
import g61562.atl.othello.model.Game;
import g61562.atl.othello.model.Position;

import java.util.List;

/**
 * The HardComputerAdversary class represents a strategy for making moves in the Othello game.
 * This strategy aims to choose the move that results in the maximum number of captures.
 */
public class HardComputerAdversary implements ComputerAdversary {

    @Override
    public Position chooseMove(Game game, Color color) {
        List<Position> possibleMoves = game.getPossibleMoves(color);
        Position bestMove = null;
        int maxCaptures = -1;

        //Iterate through all possible moves to find the one with the highest number of captures.
        for (Position move : possibleMoves) {
            int captures = countCaptures(game, move.getRow(), move.getCol(), color);
            if (captures > maxCaptures) {
                maxCaptures = captures;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int countCaptures(Game game, int row, int col, Color color) {
        int count = 0;
        for (Direction direction : Direction.values()) {
            if (game.capturesInDirection(row, col, color, direction)) {
                count++;
            }
        }
        return count;
    }

}
