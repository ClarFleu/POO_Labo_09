package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece {
    private Piece move_rook;
    private Piece move_bishop;

    public Queen(PlayerColor color){
        super(color);
        move_rook = new Rook(color);
        move_bishop = new Bishop(color);

    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        return check_bounds(to) && (move_rook.isAValidMove(from, to) || move_bishop.isAValidMove(from, to));
    }
}
