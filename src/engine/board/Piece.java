package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece implements Movement {
    PlayerColor color;

    public Piece(PlayerColor color){
        this.color = color;
    }
    /**
     * Get the color of the Piece
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Check if the move intended by the player is valid
     */
    public abstract boolean isAValidMove(Square from, Square to);


    /**
     * Get the type of the current Piece
     * @return type (PieceType)
     */
    public abstract PieceType getType();

}
