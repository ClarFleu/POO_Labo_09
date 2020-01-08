package engine.board;

public interface Movement {
    /**
     * Check if the move intended by the player is valid
     */
    public boolean isAValidMove(Square from, Square to);

    /**
     * Check if the square that we intend to go to is in the board bounds
     */
    public default boolean check_bounds(Square square){
        return (square.getX() < 8) && (square.getY() < 8);
    }
}
