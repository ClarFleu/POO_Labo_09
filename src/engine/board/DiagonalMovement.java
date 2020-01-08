package engine.board;

import static java.lang.Math.abs;

public interface DiagonalMovement extends Movement {
    /**
     * Check if the move intended by the player is diagonal
     */
    public default boolean isDiagonalMove(Square from, Square to) {
        return (check_bounds(to) && abs(from.getX() - to.getX()) == abs(from.getY() - to.getY()));
    }
}
