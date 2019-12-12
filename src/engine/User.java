package engine;

import chess.PlayerColor;

public class User implements UserChoice {
    private PlayerColor color;

    public User(PlayerColor color) {
        this.color = color;
    }

    @Override
    public String textValue() {
        return null;
    }
}
