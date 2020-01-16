package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece implements Movement {
    private PlayerColor color;
    private int nbrMoves;

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
     * Checks if the move intended by the player is valid
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if the move is valid, false otherwise
     */
    public abstract boolean isAValidMove(Square from, Square to);

    /**
     * Get the number of moves the current piece has done
     * @return nbrMoves (int)
     */
    public int getNbrMoves() {
        return nbrMoves;
    }

    /**
     * Adds a movement to the number of moves of the piece
     */
    public void moved() {
        ++nbrMoves;
    }

    /**
     * Get the type of the current Piece
     * @return type (PieceType)
     */
    public abstract PieceType getType();

    /**
     * Checks if the given king can be eaten by the given piece
     * @param from (Square) square of origin to test
     * @param king (Square) position of the king
     * @return true if the king is in danger from the given piece
     */
    public boolean hasCheckmateTheKing(Square from, Square king){
       return isAValidMove(from, king);
    }

}
