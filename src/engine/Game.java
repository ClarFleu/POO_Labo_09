package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Piece;
import engine.board.Square;

public class Game implements ChessController {
    private Board board;
    private Player player1, player2;
    private ChessView view;

    public Game() {
        player1 = new Player(PlayerColor.WHITE, true);
        player2 = new Player(PlayerColor.BLACK, false);
        board = new Board();
    }

    @Override
    public void start(ChessView view) {
        //TODO toute la logique doit y être présente y compris les appels a move et a new game();
        // ne pas oublier qu'on peut demander tt un tas de trucs grâce à la vue à l'user!!
        this.view = view;
        initView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece from = board.getSquares()[fromX][fromY].getPiece();

        if (from == null)
            return false;

        if (player1.isTurn()) {
            if (from.getColor() != player1.getPlayerColor())
                return false;
        } else {
            if (from.getColor() == player1.getPlayerColor())
                return false;
        }
        player1.changeTurn();
        player2.changeTurn();

        if (from.isAValidMove(board.getSquares()[fromX][fromY], board.getSquares()[toX][toY])) {
            board.movePiece(from, board.getSquares()[fromX][fromY], board.getSquares()[toX][toY]);
            view.removePiece(fromX, fromY);
            view.putPiece(from.getType(), from.getColor(), toX, toY);
            return true;
        }
        return false;
    }

    @Override
    public void newGame() {
        //TODO gérer la création d'un nouveau jeu
        board = new Board();
    }

    private void initView() {
        for (Piece piece : board.getPieces()) {
            int x = board.getPosition(piece)[0];
            int y = board.getPosition(piece)[1];
            if(x >= 0)
                view.putPiece(piece.getType(), piece.getColor(), x, y);
        }
    }
}
