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


}
