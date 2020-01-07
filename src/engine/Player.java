package engine;

import chess.PlayerColor;

public class Player {
    private PlayerColor color;
    private boolean turn;

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
