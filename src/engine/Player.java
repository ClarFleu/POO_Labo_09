package engine;

import chess.PlayerColor;

public class Player {
    PlayerColor teamColor;
    boolean turn;

    public Player(PlayerColor teamColor, boolean turn){
        this.teamColor = teamColor;
        this.turn = turn;
    }

    /**
     *Retourne la couleur du joueur
     */
    PlayerColor getPlayerColor(){
        return teamColor;
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
