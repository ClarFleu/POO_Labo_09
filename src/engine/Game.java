package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Piece;

public class Game implements ChessController {
    private ChessView view;
    private PlayerColor currentPlayer;
    private User player1, player2;
    private Board board;

    private void init() {
        board = new Board();
        /* White pieces */
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 1, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 2, 0);
        view.putPiece(PieceType.QUEEN, PlayerColor.WHITE, 3, 0);
        view.putPiece(PieceType.KING, PlayerColor.WHITE, 4, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 5, 0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 6, 0);
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 7, 0);
        for (int column = 0; column < 8; column++)
            view.putPiece(PieceType.PAWN, PlayerColor.WHITE, column, 1);

        /* Black pieces */
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 7, 7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 1, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 2, 7);
        view.putPiece(PieceType.QUEEN, PlayerColor.BLACK, 3, 7);
        view.putPiece(PieceType.KING, PlayerColor.BLACK, 4, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 5, 7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 6, 7);
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 0, 7);
        for (int column = 0; column < 8; column++)
            view.putPiece(PieceType.PAWN, PlayerColor.BLACK, column, 6);
    }

    private void clearBoard() {
        view.displayMessage("");
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                view.removePiece(i, j);
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if(board.legalMove(fromX, fromY, toX, toY)) {
            view.putPiece(board.getPiece(fromX, fromY).getType(), board.getPiece(fromX, fromY).getColor(), toX, toY);
            view.removePiece(fromX, fromY);
            board.move(fromX, fromY, toX, toY);
            return true;
        }
        return false;
    }

    @Override
    public void newGame() {
        /* Clear the game before init */
        clearBoard();
        init();
    }
}
