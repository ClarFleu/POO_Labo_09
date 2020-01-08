package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece implements LinearMovement {

    public Rook(PlayerColor color){
        super(color);
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        return isLinearMove(from, to);
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

}
