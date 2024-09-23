package g61562.atl.othello.model;

/**
 * The Direction enum represents the eight possible directions on an Othello board.
 * It provides constants for each direction along with methods to retrieve the change in coordinates (dx and dy) for each direction.
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    int getDx() {
        return dx;
    }

    int getDy() {
        return dy;
    }
}
