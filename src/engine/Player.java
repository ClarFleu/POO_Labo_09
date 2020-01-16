package engine;

import chess.PlayerColor;
import engine.board.Square;

public class Player {
    private PlayerColor color;
    private boolean turn;
    private Square king;

    public Player(PlayerColor teamColor, boolean turn){
        this.color = teamColor;
        this.turn = turn;
    }

    /**
     * Get the player's color
     * @return color (PlayerColor)
     */
    PlayerColor getPlayerColor(){
        return color;
    }

    /**
     * Get the position of the player's king
     * @return king (Square)
     */
    Square getKing() {
        return king;
    }

    /**
     * Sets the player's king to the given king position
     * @param king (Square) position of the player's king
     */
    public void setKing(Square king) {
        this.king = king;
    }

    /**
     * Changes the turn of the player
     */
    void changeTurn(){
        this.turn = !turn;
    }

    /**
     * Checks if this player is the current player
     * @return true if it is this player's turn, false otherwise
     */
    boolean isTurn(){
        return turn;
    }
}
