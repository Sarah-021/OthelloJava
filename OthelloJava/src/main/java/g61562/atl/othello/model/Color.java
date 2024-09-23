package g61562.atl.othello.model;

/**
 * The Color enum represents the colors of the pieces in Othello.
 * It provides two constants: WHITE and BLACK, and a method to get the opposite color.
 */
public enum Color {
    WHITE,
    BLACK;

    Color getOpposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
