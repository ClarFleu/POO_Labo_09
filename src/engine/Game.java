package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class Game implements ChessController {
    private ChessView view;

    private void init() {
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

    public void finish() {

    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        init();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public void newGame() {
        init();
    }
}
