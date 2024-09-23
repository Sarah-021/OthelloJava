package g61562.atl.othello.strategy;

import g61562.atl.othello.model.Color;
import g61562.atl.othello.model.Game;
import g61562.atl.othello.model.Position;

import java.util.List;
import java.util.Random;

/**
 * The RandomComputerAdversary class represents a simple computer adversary strategy
 * that chooses a move randomly from the available legal moves.
 */
public class RandomComputerAdversary implements ComputerAdversary {
    private final Random random;

    /**
     * Constructs a RandomComputerAdversary object.
     * Initializes a Random object for generating random moves.
     */
    public RandomComputerAdversary() {
        this.random = new Random();
    }

    @Override
    public Position chooseMove(Game game, Color color) {
        // Obtenir tous les coups possibles pour le joueur
        List<Position> possibleMoves = game.getPossibleMoves(color);
        // Vérifier si la liste des coups possibles est vide
        if (possibleMoves.isEmpty()) {
            return null;
        }

        // Choisir un coup aléatoire parmi les coups possibles
        int randomIndex = random.nextInt(possibleMoves.size());
        return possibleMoves.get(randomIndex);
    }

}
