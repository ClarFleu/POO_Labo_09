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
        return (check_bounds(to) && (abs(from.getX() - to.getX()) + abs(from.getY() - to.getY()) == 3));
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

}