package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class Pawn extends Piece implements DiagonalMovement {
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
        if (check_bounds(to) && (x1 == x) && to.getPiece() == null) {
            if (super.nbrMoves == 0) {
                return ((this.color == PlayerColor.WHITE) && ((y1 == y + 2) || y1 == y + 1)) || ((this.color == PlayerColor.BLACK) && ((y1 == y - 2) || (y1 == y - 1)));
            } else {
                return ((this.color == PlayerColor.WHITE) && (y1 == y + 1)) || ((this.color == PlayerColor.BLACK) && ((y1 == y - 1)));
            }
        } else
            return canEat(from, to);
    }

    @Override
    public boolean canBeBlocked() {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    private boolean canEat(Square from, Square to) {
        return (check_bounds(to) && abs(from.getY() - to.getY()) == 1 && isDiagonalMove(from, to) && to.getPiece() != null);
    }

    public boolean isEnPassant(Square from, Square to, Square pray) {
        return (pray.getPiece() != null &&
                pray.getPiece().getNbrMoves() == 1 &&
                abs(from.getY() - to.getY()) == 1 &&
                isDiagonalMove(from, to) &&
                pray.getPiece().getType() == PieceType.PAWN &&
                pray.didLongStep());
    }


}
