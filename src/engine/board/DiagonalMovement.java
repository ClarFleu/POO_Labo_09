package engine.board;

import static java.lang.Math.abs;

public interface DiagonalMovement extends Movement {
    /**
     * Checks if the movement has an obstacle
     * @param path (Square[]) list of suqares between the beggining and the end of the mave
     * @param pathSize
     * @return true if there is an obstacle, false otherwise
     */
    default boolean hasDObstacle(Square[] path, int pathSize) {
        if(pathSize == 0) {
            return false;
        }
        Square  from = path[0],
                to = path[pathSize-1];
        int cpt = 0;
        for (Square square : path) {
            ++cpt;
            if(to.getX() > from.getX())
                if (square != from && square != to &&
                    square.getX() == from.getX() + cpt &&
                    square.getY() == from.getY() + cpt &&
                    !square.isEmpty())
                    return true;
            else
                if (square != from && square != to &&
                    square.getX() == from.getX() - cpt &&
                    square.getY() == from.getY() + cpt &&
                    !square.isEmpty())
                    return true;
        }
        return  false;
    }

    /**
     * Check if the move intended by the player is diagonal
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if the move is in diagonal, false otherwise
     */
    default boolean isDiagonalMove(Square from, Square to) {
        int moveX = abs(from.getX() - to.getX());
        int moveY = abs(from.getY() - to.getY());
        if(moveX > 0 && moveY > 0)
            return (checkBounds(to) && moveX == moveY);
        else return false;
    }
}
