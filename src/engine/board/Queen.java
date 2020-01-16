package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece implements LinearMovement, DiagonalMovement{
    public Queen(PlayerColor color){
        super(color);
     }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        return isDiagonalMove(from, to) || isLinearMove(from, to);
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public boolean hasObstacle(Square[] path, int pathSize) {
        if (path[0].getX() == path[pathSize-1].getX() ||
            path[0].getY() == path[pathSize-1].getY())
            return hasLObstacle(path, pathSize);
        return hasDObstacle(path, pathSize);
    }

}
