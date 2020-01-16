package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class Pawn extends Piece implements DiagonalMovement, LinearMovement {
    public Pawn(PlayerColor color){
        super(color);
    }

    /**
     * Checks if the current pawn is iin position too be promoted
     * @param y (int) value y of the Pawn's position
     * @return true if the pawn is at the top or the bottom of the board, false otherwise
     */
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
    public boolean hasObstacle(Square[] path, int pathSize) {
        return (isLinearMove(path[0], path[pathSize-1]) && hasLObstacle(path, pathSize));
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    /**
     * Checks if the given move can allow the Pawn to eat
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if the pawn eats a Piece, false otherwise
     */
    private boolean canEat(Square from, Square to) {
        return (checkBounds(to)                  &&
                abs(from.getY() - to.getY()) == 1 &&
                isDiagonalMove(from, to)          &&
                to.getPiece() != null);
    }

    /**
     * Checks if the given move is a "Prise en passant"
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @param prey (Square) position of the possible pray of the move
     * @return true if the move is a "Prise en passant", false otherwise
     */
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
