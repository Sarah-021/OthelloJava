package g61562.atl.othello.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {
    private static final int DEFAULT_SIZE = 8;

    @Test
    public void testDefaultBoardCreation() {
        Board board = new Board();
        assertEquals(DEFAULT_SIZE, board.getSize());
        // Check if the central pieces are initialized correctly
        Assertions.assertEquals(Color.WHITE, board.getColor(DEFAULT_SIZE/2 - 1, DEFAULT_SIZE/2 - 1));
        assertEquals(Color.BLACK, board.getColor(DEFAULT_SIZE/2 - 1, DEFAULT_SIZE/2));
        assertEquals(Color.BLACK, board.getColor(DEFAULT_SIZE/2, DEFAULT_SIZE/2 - 1));
        assertEquals(Color.WHITE, board.getColor(DEFAULT_SIZE/2, DEFAULT_SIZE/2));
    }

    @Test
    public void testCustomBoardCreation() {
        int customSize = 6;
        Board board = new Board(customSize);
        assertEquals(customSize, board.getSize());
        // Check if the central pieces are initialized correctly
        assertEquals(Color.WHITE, board.getColor(customSize/2 - 1, customSize/2 - 1));
        assertEquals(Color.BLACK, board.getColor(customSize/2 - 1, customSize/2));
        assertEquals(Color.BLACK, board.getColor(customSize/2, customSize/2 - 1));
        assertEquals(Color.WHITE, board.getColor(customSize/2, customSize/2));
    }

    @Test
    public void testInvalidSize() {
        int invalidSize = 3;
        assertThrows(IllegalArgumentException.class, () -> new Board(invalidSize));
    }

    @Test
    public void testGetColor() {
        Board board = new Board();
        assertEquals(Color.WHITE, board.getColor(3, 3));
        assertEquals(Color.BLACK, board.getColor(3, 4));
        assertEquals(null, board.getColor(0, 0)); // Vérifiez une case vide
    }


    @Test
    public void testAddPiece() {
        Board board = new Board();
        board.addPiece(2, 3, Color.WHITE);
        assertEquals(Color.WHITE, board.getColor(2, 3));
    }
    @Test
    public void testSetColor() {
        Board board = new Board();
        board.setColor(4, 5, Color.BLACK);
        assertEquals(Color.BLACK, board.getColor(4, 5));
    }


    @Test
    public void testGetColorInvalidIndices() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.getColor(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> board.getColor(5, -1));
        assertThrows(IllegalArgumentException.class, () -> board.getColor(DEFAULT_SIZE, 5));
        assertThrows(IllegalArgumentException.class, () -> board.getColor(5, DEFAULT_SIZE));
    }
    @Test
    public void testInitBoard() {
        Board board = new Board(6);
        // Check if the central pieces are initialized correctly
        assertEquals(Color.WHITE, board.getColor(2, 2));
        assertEquals(Color.BLACK, board.getColor(2, 3));
        assertEquals(Color.BLACK, board.getColor(3, 2));
        assertEquals(Color.WHITE, board.getColor(3, 3));
        // Check that other cells are null
        assertNull(board.getColor(0, 0));
        assertNull(board.getColor(5, 5));
    }

    @Test
    public void testInitialBoardState() {
        Board board = new Board();
        // Check if the central pieces are initialized correctly
        assertEquals(Color.WHITE, board.getColor(DEFAULT_SIZE/2 - 1, DEFAULT_SIZE/2 - 1));
        assertEquals(Color.BLACK, board.getColor(DEFAULT_SIZE/2 - 1, DEFAULT_SIZE/2));
        assertEquals(Color.BLACK, board.getColor(DEFAULT_SIZE/2, DEFAULT_SIZE/2 - 1));
        assertEquals(Color.WHITE, board.getColor(DEFAULT_SIZE/2, DEFAULT_SIZE/2));
        // Check that other cells are null
        assertNull(board.getColor(0, 0));
        assertNull(board.getColor(DEFAULT_SIZE/2, DEFAULT_SIZE/2 + 1));
    }

    @Test
    public void testGetSizeAfterCustomCreation() {
        int customSize = 6;
        Board board = new Board(customSize);
        assertEquals(customSize, board.getSize());
    }

    @Test
    public void testGetSizeAfterDefaultCreation() {
        Board board = new Board();
        assertEquals(DEFAULT_SIZE, board.getSize());
    }

}