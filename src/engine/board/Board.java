package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Board {
    final int MAXSIZE = 8;
    private Piece[][] board;

    public Board() {
        board = new Piece[MAXSIZE][MAXSIZE];
        /* White pieces */
        board[0][0] = new Rook(PlayerColor.WHITE);
        board[1][0] = new Knight(PlayerColor.WHITE);
        board[2][0] = new Bishop(PlayerColor.WHITE);
        board[3][0] = new Queen(PlayerColor.WHITE);
        board[4][0] = new King(PlayerColor.WHITE);
        board[5][0] = new Bishop(PlayerColor.WHITE);
        board[6][0] = new Knight(PlayerColor.WHITE);
        board[7][0] = new Rook(PlayerColor.WHITE);
        for (int column = 0; column < MAXSIZE; column++)
            board[column][1] = new Pawn(PlayerColor.WHITE);

        /* Black pieces */
        board[0][7] = new Rook(PlayerColor.BLACK);
        board[1][7] = new Knight(PlayerColor.BLACK);
        board[2][7] = new Bishop(PlayerColor.BLACK);
        board[3][7] = new Queen(PlayerColor.BLACK);
        board[4][7] = new King(PlayerColor.BLACK);
        board[5][7] = new Bishop(PlayerColor.BLACK);
        board[6][7] = new Knight(PlayerColor.BLACK);
        board[7][7] = new Rook(PlayerColor.BLACK);
        for (int column = 0; column < MAXSIZE; column++)
            board[column][6] = new Pawn(PlayerColor.BLACK);
    }

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public boolean legalMove(int fromX, int fromY, int toX, int toY) {
        return true;
    }

    public void move(int fromX, int fromY, int toX, int toY) {
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
    }
}
