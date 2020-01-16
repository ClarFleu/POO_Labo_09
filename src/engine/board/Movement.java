package engine.board;

public interface Movement {
    /**
     * Check if the move intended by the player is valid
     */
    boolean isAValidMove(Square from, Square to);

    /**
     * Checks if the path contains an obstacle for the movement
     * @param path (Square[]) path on which the move goes
     * @param pathSize (int) number of squares in the path
     * @return true if there is an obstacle, false otherwise
     */
    boolean hasObstacle(Square[] path, int pathSize);

    /**
     * Check if the square that we intend to go to is in the board bounds
     * @param square (Square) position to test
     * @return true if the given Square is in the bounds of the board, false otherwise
     */
    default boolean checkBounds(Square square){
        return (square.getX() < 8) && (square.getY() < 8);
    }
}
