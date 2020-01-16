package engine.board;

import static java.lang.Math.abs;

public interface LinearMovement extends Movement {
    /**
     * Checks if the movement has an obstacle
     * @param path (Square[]) list of suqares between the beggining and the end of the mave
     * @param pathSize
     * @return true if there is an obstacle, false otherwise
     */
    default boolean hasLObstacle(Square[] path, int pathSize) {
        if(pathSize == 0) {
            return false;
        }
        Square  from = path[0],
                to = path[pathSize-1];

        if(from.getY() == to.getY()) {
            for (Square square : path)
                if (!square.isEmpty() &&
                    square.getY() == from.getY() &&
                    square.getPiece() != null)
                    return true;
        } else if (from.getX() == to.getX()) {
            for (Square square : path)
                if (!square.isEmpty() &&
                    square.getX() == from.getX() &&
                    square.getPiece() != null)
                    return true;
        }
        return false;
    }

    /**
     * Check if the move intended by the player is horizontal or vertical
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if the move is linear, false otherwise
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

