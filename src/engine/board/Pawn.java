package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {
    public Pawn(PlayerColor color){
        super(color);
    }

    public boolean isPromotion(int y) {
        return ((this.getColor() == PlayerColor.BLACK && y == 0) ||
                (this.getColor() == PlayerColor.WHITE && y == 7));
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        int x = from.getX();
        int y = from.getY();
        int x1 = to.getX();
        int y1 = to.getY();
        if (check_bounds(to) && (x1 == x)) {
            if (super.nbrMoves == 0) {
                return (((this.color == PlayerColor.WHITE) && ((y1 == y + 2) || y1 == y + 1)) || ((this.color == PlayerColor.BLACK) && ((y1 == y - 2) || (y1 == y - 1))));
            } else {
                return ((this.color == PlayerColor.WHITE) && (y1 == y + 1)) || ((this.color == PlayerColor.BLACK) && ((y1 == y - 1)));
            }
        } else
            return false;

    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

}
