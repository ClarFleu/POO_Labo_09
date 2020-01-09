package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class Knight extends Piece {
    public Knight(PlayerColor color){
        super(color);
    }


    @Override
    public boolean isAValidMove(Square from, Square to) {
       int moveX = abs(from.getX() - to.getX());
       int moveY = abs(from.getY() - to.getY());
       return (((moveX == 1) && (moveY == 2)) || ((moveX == 2) && (moveY == 1))) && check_bounds(to);
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

}
