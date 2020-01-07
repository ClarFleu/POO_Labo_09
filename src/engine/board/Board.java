package engine.board;

import chess.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Square[][] squares;
    private List<Piece> pieces;

    public Board(){
        squares = new Square[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                squares[i][j] = new Square(i, j);
            }
        }
        pieces = new ArrayList<Piece>();
        initialize();
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Piece[] getPieces() {
        Piece[] pieceTab = new Piece[pieces.size()];
        int idx = 0;
        for (Piece piece : pieces)
            pieceTab[idx++] = piece;
        return pieceTab;
    }

    public void deletePiece(Piece piece){
        pieces.remove(piece);
        return;
    }

    public void movePiece(Piece piece, Square from, Square to){

    }

    void initialize(){
        pieces.add(new Rook(PlayerColor.WHITE));
        pieces.add(new Knight(PlayerColor.WHITE));
        pieces.add(new Bishop(PlayerColor.WHITE));
        pieces.add(new King(PlayerColor.WHITE));
        pieces.add(new Queen(PlayerColor.WHITE));
        pieces.add(new Bishop(PlayerColor.WHITE));
        pieces.add(new Knight(PlayerColor.WHITE));
        pieces.add(new Rook(PlayerColor.WHITE));
        squares[0][0].setPiecePosition(pieces.get(0));
        squares[1][0].setPiecePosition(pieces.get(1));
        squares[2][0].setPiecePosition(pieces.get(2));
        squares[3][0].setPiecePosition(pieces.get(3));
        squares[4][0].setPiecePosition(pieces.get(4));
        squares[5][0].setPiecePosition(pieces.get(5));
        squares[6][0].setPiecePosition(pieces.get(6));
        squares[7][0].setPiecePosition(pieces.get(7));

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(PlayerColor.WHITE));
            squares[i][1].setPiecePosition(pieces.get(8+i));
        }

        pieces.add(new Rook(PlayerColor.BLACK));
        pieces.add(new Knight(PlayerColor.BLACK));
        pieces.add(new Bishop(PlayerColor.BLACK));
        pieces.add(new King(PlayerColor.BLACK));
        pieces.add(new Queen(PlayerColor.BLACK));
        pieces.add(new Bishop(PlayerColor.BLACK));
        pieces.add(new Knight(PlayerColor.BLACK));
        pieces.add(new Rook(PlayerColor.BLACK));
        squares[0][7].setPiecePosition(pieces.get(16));
        squares[1][7].setPiecePosition(pieces.get(17));
        squares[2][7].setPiecePosition(pieces.get(18));
        squares[3][7].setPiecePosition(pieces.get(19));
        squares[4][7].setPiecePosition(pieces.get(20));
        squares[5][7].setPiecePosition(pieces.get(21));
        squares[6][7].setPiecePosition(pieces.get(22));
        squares[7][7].setPiecePosition(pieces.get(23));

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(PlayerColor.BLACK));
            squares[i][6].setPiecePosition(pieces.get(24+i));
        }
    }

    public int[] getPosition(Piece piece) {
        int[] pos = new int[2];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                pos[0] = squares[i][j].x;
                pos[1] = squares[i][j].y;
                if (squares[i][j].piece == piece)
                    return pos;
            }
        }
        pos[0] = -1;
        pos[1] = -1;
        return pos;
    }
}
