package engine.board;

import chess.PlayerColor;

public class Rook extends Piece {

    public Rook(PlayerColor color){
        super(color);
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        return ((check_bounds(to) && (from.getX() == to.getX())||(from.getY() == to.getY())));
    }
}
