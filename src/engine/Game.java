package engine;

import chess.ChessController;
import chess.ChessView;
import engine.board.Board;
import engine.board.Piece;
import engine.board.Square;

public class Game implements ChessController {
    private Board board;
    private Player player1, player2;

    @Override
    public void start(ChessView view) {
        //toute la logique doit y être présente y compris les appels a move et a new game();
        // ne pas oublier qu'on peut demander tt un tas de trucs grâce à la vue à l'user!!
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Square[][] squares = board.getSquares();
        Piece currentPiece = squares[fromX][fromY].getPiece();

        return false;
    }

    @Override
    public void newGame() {
        board = new Board();

    }

}
