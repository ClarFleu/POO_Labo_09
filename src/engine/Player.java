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
     *Retourne la couleur du joueur
     */
    PlayerColor getPlayerColor(){
        return color;
    }

    Square getKing() {
        return king;
    }

    public void setKing(Square king) {
        this.king = king;
    }
    /**
     * Active ou desactive le tour du joueur courant
     */
    void changeTurn(){
        this.turn = !turn;
    }

    /**
     * Retourne si c'est au tour du joueur de jouer
     */
    boolean isTurn(){
        return turn;
    }
}
