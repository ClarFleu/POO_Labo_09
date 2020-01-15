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
    public boolean canBeBlocked() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public boolean hasObstacle(Square[] path) {
        // TODO checker la présence d'obstacle
        return false;
    }

}
