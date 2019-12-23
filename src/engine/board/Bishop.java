package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {
    public Bishop(PlayerColor color) {
        this.color = color;
    }

    @Override
    public int[][] getMoves() {
        return new int[0][];
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
