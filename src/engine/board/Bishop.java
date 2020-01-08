package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class Bishop extends Piece implements DiagonalMovement{

    public Bishop(PlayerColor color){
        super(color);
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        return isDiagonalMove(from, to);
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
