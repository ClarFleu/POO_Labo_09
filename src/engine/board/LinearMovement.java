package engine.board;

import static java.lang.Math.abs;

public interface LinearMovement extends Movement {
    /**
     * Check if the move intended by the player is horizontal or vertical
     */
    default boolean isLinearMove(Square from, Square to){
        int moveX = abs(from.getX() - to.getX());
        int moveY = abs(from.getY() - to.getY());
        if(checkBounds(to)){
            return (moveX!=0 && moveY==0) || (moveX == 0 && moveY!=0);
        }
        else return false;
    }
}

