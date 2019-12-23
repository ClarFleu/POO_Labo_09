package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {
    public Pawn(PlayerColor color) {
        this.color = color;
    }

    @Override
    public int[][] getMoves() {
        return new int[0][];
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
