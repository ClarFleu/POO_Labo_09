package engine.board;

public interface LinearMovement extends Movement {
    /**
     * Check if the move intended by the player is horizontal or vertical
     */
    public default boolean isLinearMove(Square from, Square to){
        return ((check_bounds(to) && (from.getX() == to.getX())||(from.getY() == to.getY())));
    }
}

