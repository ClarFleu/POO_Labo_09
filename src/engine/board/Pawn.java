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
        if (checkBounds(to) && (x1 == x) && to.getPiece() == null) {
            if (super.getNbrMoves() == 0) {
                return ((getColor() == PlayerColor.WHITE) && ((y1 == y + 2) || y1 == y + 1)) ||
                       ((getColor() == PlayerColor.BLACK) && ((y1 == y - 2) || (y1 == y - 1)));
            } else {
                return ((getColor() == PlayerColor.WHITE) && (y1 == y + 1)) ||
                       ((getColor() == PlayerColor.BLACK) && ((y1 == y - 1)));
            }
        } else
            return canEat(from, to);
    }

    @Override
    public boolean hasObstacle(Square[] path) {
        return false;
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
        return (checkBounds(to)                  &&
                abs(from.getY() - to.getY()) == 1 &&
                isDiagonalMove(from, to)          &&
                to.getPiece() != null);
    }

    public boolean isEnPassant(Square from, Square to, Square prey) {
        return (prey.getPiece() != null                                  &&
                prey.getPiece().getNbrMoves() == 1                       &&
                abs(from.getY() - to.getY()) == 1                        &&
                isDiagonalMove(from, to)                                 &&
                prey.getPiece().getType() == PieceType.PAWN              &&
                prey.getPiece().getColor() != from.getPiece().getColor() &&
                prey.didLongStep());
    }
}
