package g61562.atl.othello.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void testAddPieceToValidPosition() {
        Game game = new Game();
        assertFalse(game.isValidMove(2, 2, Color.BLACK)); // Vérifiez si le mouvement est valide
        game.addPiece(3, 2, Color.BLACK); //non aout d'une pièce
        assertEquals(Color.BLACK, game.getBoard().getColor(3, 2)); // Vérifiez si la pièce a été ajoutée
    }
    @Test
    public void testAddPieceToNotValidPosition() {
        Game game = new Game();
        assertFalse(game.isValidMove(2, 2, Color.BLACK)); // Vérifiez si le mouvement est valide
        game.addPiece(3, 2, Color.BLACK);
        assertEquals(null, game.getBoard().getColor(2, 2)); // Vérifiez si la pièce a été ajoutée
    }


    @Test
    public void testValidMove() {
        Game game = new Game();
        assertTrue(game.isValidMove(3, 2, Color.BLACK));
        assertFalse(game.isValidMove(0, 0, Color.WHITE));
    }

    @Test
    public void testSwitchPlayer() {
        Game game = new Game();
        assertEquals(Color.BLACK, game.getCurrentPlayer());
        game.switchPlayer(); // Changer de joueur
        assertEquals(Color.WHITE, game.getCurrentPlayer());
    }

    @Test
    public void testHasValidMoves() {
        Game game = new Game(4,null,false);
        game.addPiece(0, 1, Color.BLACK); // Ajouter une pièce pour bloquer le joueur blanc
        game.addPiece(2, 0, Color.WHITE);
        game.addPiece(0, 2, Color.WHITE);
        game.addPiece(1, 0, Color.WHITE);
        game.addPiece(0, 3, Color.BLACK); // Ajouter une pièce pour bloquer le joueur blanc
        game.addPiece(0, 0, Color.WHITE);
        assertEquals(false, game.hasValidMove(Color.WHITE)); // Le joueur actuel doit rester le même (noir) car le joueur blanc n'a pas de mouvements valides
    }

    @Test
    public void testChooseOpponentMove() {
        Game game = new Game();
        Position move = game.chooseOpponentMove(Color.WHITE); // Simuler le choix du mouvement par l'adversaire
        assertNotNull(move);
    }

    @Test
    public void testGetWinner() {
        Game game = new Game();
        game.addPiece(3, 2, Color.BLACK);
        game.addPiece(4, 3, Color.WHITE);
        game.addPiece(5, 4, Color.BLACK);
        game.updateScore();
        assertEquals(Color.BLACK, game.getWinner());
    }

    @Test
    public void testNotWinner() {
        Game game = new Game();
        assertNull(game.getWinner()); // Au début du jeu, il ne devrait y avoir aucun gagnant
        game.addPiece(3, 2, Color.BLACK);
        game.addPiece(4, 2, Color.WHITE);
        assertNull(game.getWinner()); // Après avoir placé une pièce, il ne devrait toujours y avoir aucun gagnant
    }


    @Test
    public void testUndo() {
        Game game = new Game();
        game.addPiece(3, 2, Color.BLACK);
        game.undo();
        assertNull(game.getBoard().getColor(3, 2));
    }

    @Test
    public void testRedo() {
        Game game = new Game();
        game.addPiece(3, 2, Color.BLACK);
        game.undo(); // Annuler le dernier mouvement
        assertNull(game.getBoard().getColor(3, 2)); // Vérifier que la case est vide après l'annulation
        game.redo(); // Refaire le dernier mouvement annulé
        assertEquals(Color.BLACK, game.getBoard().getColor(3, 2)); // Vérifier que la pièce a été ajoutée à nouveau après le refaire
    }

    @Test
    public void testIsValidMove() {
        Game game = new Game();
        // Tester isValidMove pour différents scénarios
        assertTrue(game.isValidMove(4, 5, Color.BLACK));
        assertFalse(game.isValidMove(0, 0, Color.WHITE));
    }

    @Test
    public void testCaptureOpponent() {
        Game game = new Game();
        // Joueur peut capturer la piece la,che qui est en 4,4)
        assertTrue(game.capturesOpponent(4, 5, Color.BLACK));
    }

    @Test
    public void testChangeColor() {
        Game game = new Game();
        game.addPiece(4, 5, Color.BLACK);
        assertTrue(game.getBoard().getColor(4, 4) == Color.BLACK);
        game.addPiece(5, 5, Color.WHITE);
        assertTrue(game.getBoard().getColor(4, 4) == Color.WHITE);
    }

}