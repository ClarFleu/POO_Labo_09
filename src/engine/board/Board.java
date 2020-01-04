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
    public void deletePiece(Piece piece){
        pieces.remove(piece);
        return;
    }

    public void movePiece(Piece piece, Square from, Square to){

    }

    void initialize(){
        pieces.add(new Rook(PlayerColor.WHITE));
        squares[0][0].setPiecePosition(pieces.get(0));
        pieces.add(new Knight(PlayerColor.WHITE));
        squares[1][0].setPiecePosition(pieces.get(1));
        pieces.add(new Bishop(PlayerColor.WHITE));
        squares[2][0].setPiecePosition(pieces.get(2));
        pieces.add(new King(PlayerColor.WHITE));
        squares[3][0].setPiecePosition(pieces.get(3));
        pieces.add(new Queen(PlayerColor.WHITE));
        squares[4][0].setPiecePosition(pieces.get(4));
        pieces.add(new Bishop(PlayerColor.WHITE));
        squares[5][0].setPiecePosition(pieces.get(5));
        pieces.add(new Knight(PlayerColor.WHITE));
        squares[6][0].setPiecePosition(pieces.get(6));
        pieces.add(new Rook(PlayerColor.WHITE));
        squares[7][0].setPiecePosition(pieces.get(7));

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(PlayerColor.WHITE));
            squares[i][1].setPiecePosition(pieces.get(8+i));
            pieces.add(new Pawn(PlayerColor.BLACK));
            squares[i][1].setPiecePosition(pieces.get(16+i));
        }

        pieces.add(new Rook(PlayerColor.BLACK));
        squares[0][7].setPiecePosition(pieces.get(24));
        pieces.add(new Knight(PlayerColor.BLACK));
        squares[1][7].setPiecePosition(pieces.get(25));
        pieces.add(new Bishop(PlayerColor.BLACK));
        squares[2][7].setPiecePosition(pieces.get(26));
        pieces.add(new King(PlayerColor.BLACK));
        squares[3][7].setPiecePosition(pieces.get(27));
        pieces.add(new Queen(PlayerColor.BLACK));
        squares[4][7].setPiecePosition(pieces.get(28));
        pieces.add(new Bishop(PlayerColor.BLACK));
        squares[5][7].setPiecePosition(pieces.get(29));
        pieces.add(new Knight(PlayerColor.BLACK));
        squares[6][7].setPiecePosition(pieces.get(30));
        pieces.add(new Rook(PlayerColor.BLACK));
        squares[7][7].setPiecePosition(pieces.get(31));
    }
}
