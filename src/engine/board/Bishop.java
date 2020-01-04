package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class Bishop extends Piece {
    public Bishop(PlayerColor color){
        super(color);
    }


    @Override
    public boolean isAValidMove(Square from, Square to) {
        if(abs(from.getX() - to.getX()) == abs(from.getY() - to.getY()))
            return true;
        else return false;
    }
}
