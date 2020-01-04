package engine.board;

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
     * Check if the square that we intend to go to is in the board bounds
     */
    public boolean check_bounds(Square square){
        return (square.getX() < 8) && (square.getY() < 8);
    }

}
