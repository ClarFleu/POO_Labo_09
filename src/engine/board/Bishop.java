package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece implements DiagonalMovement{

    public Bishop(PlayerColor color){
        super(color);
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        return isDiagonalMove(from, to);
    }

    @Override
    public boolean hasObstacle(Square[] path, int pathSize) {
        return hasDObstacle(path, pathSize);
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
