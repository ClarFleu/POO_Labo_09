package engine.board;

public interface Movement {
    /**
     * Check if the move intended by the player is valid
     */
    boolean isAValidMove(Square from, Square to);

    /**
     * Checks if the movement has an obstacle
     * @param path (Square[]) list of suqares between the beggining and the end of the mave
     * @return true if there is an obstacle, false otherwise
     */
    boolean hasObstacle(Square[] path);

    /**
     * Check if the square that we intend to go to is in the board bounds
     */
    default boolean checkBounds(Square square){
        return (square.getX() < 8) && (square.getY() < 8);
    }
}
