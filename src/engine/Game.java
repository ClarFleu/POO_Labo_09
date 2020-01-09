package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Piece;

public class Game implements ChessController {
    private Board board;
    private Player player1, player2;
    private ChessView view;

    public Game() {
        player1 = new Player(PlayerColor.WHITE, true);
        player2 = new Player(PlayerColor.BLACK, false);
        board = new Board();
    }

    private boolean turnChange(Piece piece) {
        if (player1.isTurn()) {
            if (piece.getColor() != player1.getPlayerColor())
                return false;
        } else {
            if (piece.getColor() == player1.getPlayerColor())
                return false;
        }
        player1.changeTurn();
        player2.changeTurn();
        return true;
    }

    private void movePiece(Piece p, int fromX, int fromY, int toX, int toY ) {
        view.removePiece(fromX, fromY);
        view.putPiece(p.getType(), p.getColor(), toX, toY);
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        initView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece from = board.getSquares()[fromX][fromY].getPiece();
        if (from == null)
            return false;

        if (turnChange(from)) {
            int[] passPos = board.isEnPassant(fromX, fromY, toX, toY);

            if(passPos[0] >= 0 && passPos[1] >= 0) {
                view.removePiece(passPos[0], passPos[1]);
                movePiece(from, fromX, fromY, toX, toY);
                from.moved();
                return true;
            }
            if (board.isValid(fromX, fromY, toX, toY)) {
                movePiece(from, fromX, fromY, toX, toY);
                from.moved();
                return true;
            }
            player1.changeTurn();
            player2.changeTurn();
        }
        return false;
    }

    @Override
    public void newGame() {
        board = new Board();
        if (player2.isTurn()) {
            player2.changeTurn();
            player1.changeTurn();
        }

        this.start(view);
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
