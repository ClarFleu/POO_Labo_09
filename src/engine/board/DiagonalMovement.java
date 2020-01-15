package engine.board;

import static java.lang.Math.abs;

public interface DiagonalMovement extends Movement {
    /**
     * Check if the move intended by the player is diagonal
     */
    default boolean isDiagonalMove(Square from, Square to) {
        int moveX = abs(from.getX() - to.getX());
        int moveY = abs(from.getY() - to.getY());
        if(moveX > 0 && moveY > 0)
            return (check_bounds(to) && moveX == moveY);
        else return false;
    }
}
