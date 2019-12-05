package engine;

import chess.ChessController;
import chess.ChessView;

public class Game implements ChessController {
    User
        player1 = new User(1),
        player2 = new User(2);

    @Override
    public void start(ChessView view) {
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public void newGame() {
    }
}
