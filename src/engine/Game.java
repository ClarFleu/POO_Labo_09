package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class Game implements ChessController {
    private ChessView view;
    private PlayerColor currentPlayer;
    private User player1, player2;
    private PieceType[][] board = new PieceType[8][8];

    private void init() {
        /* White pieces */
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 0);
        board[0][0] = PieceType.ROOK;
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 1, 0);
        board[1][0] = PieceType.KNIGHT;
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 2, 0);
        board[2][0] = PieceType.BISHOP;
        view.putPiece(PieceType.QUEEN, PlayerColor.WHITE, 3, 0);
        board[3][0] = PieceType.QUEEN;
        view.putPiece(PieceType.KING, PlayerColor.WHITE, 4, 0);
        board[4][0] = PieceType.KING;
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 5, 0);
        board[5][0] = PieceType.BISHOP;
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 6, 0);
        board[6][0] = PieceType.KNIGHT;
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 7, 0);
        board[7][0] = PieceType.ROOK;
        for (int column = 0; column < 8; column++) {
            view.putPiece(PieceType.PAWN, PlayerColor.WHITE, column, 1);
            board[column][1] = PieceType.PAWN;
        }

        /* Black pieces */
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 7, 7);
        board[7][7] = PieceType.ROOK;
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 1, 7);
        board[1][7] = PieceType.KNIGHT;
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 2, 7);
        board[2][7] = PieceType.BISHOP;
        view.putPiece(PieceType.QUEEN, PlayerColor.BLACK, 3, 7);
        board[3][7] = PieceType.QUEEN;
        view.putPiece(PieceType.KING, PlayerColor.BLACK, 4, 7);
        board[4][7] = PieceType.KING;
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 5, 7);
        board[5][7] = PieceType.BISHOP;
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 6, 7);
        board[6][7] = PieceType.KNIGHT;
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 0, 7);
        board[7][7] = PieceType.ROOK;
        for (int column = 0; column < 8; column++) {
            view.putPiece(PieceType.PAWN, PlayerColor.BLACK, column, 6);
            board[column][6] = PieceType.PAWN;
        }
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
        player1 = new User(PlayerColor.WHITE);
        player2 = new User(PlayerColor.BLACK);
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        PieceType pt = board[fromX][fromY];

        board[toX][toY] = pt;
        board[fromX][fromY] = null;
        view.removePiece(fromX, fromY);
        view.putPiece(pt, currentPlayer, toX, toY);

        if(currentPlayer == PlayerColor.WHITE)
            currentPlayer = PlayerColor.BLACK;
        else
            currentPlayer = PlayerColor.WHITE;
        return true;
    }

    @Override
    public void newGame() {
        /* Clear the game before init */
        clearBoard();
        init();
        this.currentPlayer = PlayerColor.WHITE;
    }
}
