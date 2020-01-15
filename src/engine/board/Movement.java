package engine.board;

public interface Movement {
    /**
     * Check if the move intended by the player is valid
     */
    boolean isAValidMove(Square from, Square to);

    boolean hasObstacle(Square[] path, int pathSize);

    /**
     * Check if the square that we intend to go to is in the board bounds
     */
    default boolean checkBounds(Square square){
        return (square.getX() < 8) && (square.getY() < 8);
    }
}
