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

    public Game() {
        player1 = new Player(PlayerColor.WHITE, true);
        player2 = new Player(PlayerColor.BLACK, false);
    }

    @Override
    public void start(ChessView view) {
        //TODO toute la logique doit y être présente y compris les appels a move et a new game();
        // ne pas oublier qu'on peut demander tt un tas de trucs grâce à la vue à l'user!!
        board = new Board();
        initView(view);
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece from = board.getSquares()[fromX][fromY].getPiece();

        if (from.isAValidMove(board.getSquares()[fromX][fromY], board.getSquares()[toX][toY])) {
            //TODO faire le mouvement

            return true;
        }

        return false;
    }

    @Override
    public void newGame() {
        //TODO gérer la création d'un nouveau jeu
    }

    private void initView(ChessView view) {
        for (Piece piece : board.getPieces()) {
            int x = board.getPosition(piece)[0];
            int y = board.getPosition(piece)[1];
            if(x >= 0)
                view.putPiece(piece.getType(), piece.getColor(), x, y);
        }
    }
}
