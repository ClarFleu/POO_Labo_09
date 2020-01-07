package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {

    private boolean hasDoneFirstMove;

    public Pawn(PlayerColor color){
        super(color);
        hasDoneFirstMove = false;
    }


    @Override
    public boolean isAValidMove(Square from, Square to) {
        int x = from.getX();
        int y = from.getY();
        int x1 = to.getX();
        int y1 = to.getY();
        if (check_bounds(to) && (x1 == x)) {
            if (!hasDoneFirstMove) {
                this.hasDoneFirstMove = true;
                return (((this.color == PlayerColor.WHITE) && ((y1 == y + 2) || y1 == y + 1)) || ((this.color == PlayerColor.BLACK) && ((y1 == y - 2) || (y1 == y - 1))));
            } else {
                return ((this.color == PlayerColor.WHITE) && (y1 == y + 1)) || ((this.color == PlayerColor.BLACK) && ((y1 == y - 1)));
            }
        }else return false;

    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

}
