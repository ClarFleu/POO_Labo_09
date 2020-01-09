package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece implements Movement {
    PlayerColor color;
    int nbrMoves;

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

    public int getNbrMoves() {
        return nbrMoves;
    }

    public void moved() {
        ++nbrMoves;
    }

    /**
     * Get the type of the current Piece
     * @return type (PieceType)
     */
    public abstract PieceType getType();

}
