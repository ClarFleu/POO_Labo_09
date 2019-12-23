package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    protected PlayerColor color;

    public abstract int[][] getMoves();
    public abstract PieceType getType();

    public PlayerColor getColor() {
        return color;
    }
}


