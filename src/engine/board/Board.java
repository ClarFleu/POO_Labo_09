package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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
        init();
    }

    void init(){
        pieces.add(new Rook(PlayerColor.WHITE));
        pieces.add(new Knight(PlayerColor.WHITE));
        pieces.add(new Bishop(PlayerColor.WHITE));
        pieces.add(new Queen(PlayerColor.WHITE));
        pieces.add(new King(PlayerColor.WHITE));
        pieces.add(new Bishop(PlayerColor.WHITE));
        pieces.add(new Knight(PlayerColor.WHITE));
        pieces.add(new Rook(PlayerColor.WHITE));
        squares[0][0].setPiece(pieces.get(0));
        squares[1][0].setPiece(pieces.get(1));
        squares[2][0].setPiece(pieces.get(2));
        squares[3][0].setPiece(pieces.get(3));
        squares[4][0].setPiece(pieces.get(4));
        squares[5][0].setPiece(pieces.get(5));
        squares[6][0].setPiece(pieces.get(6));
        squares[7][0].setPiece(pieces.get(7));

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(PlayerColor.WHITE));
            squares[i][1].setPiece(pieces.get(8+i));
        }

        pieces.add(new Rook(PlayerColor.BLACK));
        pieces.add(new Knight(PlayerColor.BLACK));
        pieces.add(new Bishop(PlayerColor.BLACK));
        pieces.add(new Queen(PlayerColor.BLACK));
        pieces.add(new King(PlayerColor.BLACK));
        pieces.add(new Bishop(PlayerColor.BLACK));
        pieces.add(new Knight(PlayerColor.BLACK));
        pieces.add(new Rook(PlayerColor.BLACK));
        squares[0][7].setPiece(pieces.get(16));
        squares[1][7].setPiece(pieces.get(17));
        squares[2][7].setPiece(pieces.get(18));
        squares[3][7].setPiece(pieces.get(19));
        squares[4][7].setPiece(pieces.get(20));
        squares[5][7].setPiece(pieces.get(21));
        squares[6][7].setPiece(pieces.get(22));
        squares[7][7].setPiece(pieces.get(23));

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(PlayerColor.BLACK));
            squares[i][6].setPiece(pieces.get(24+i));
        }
    }

    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        Square sFrom = squares[fromX][fromY],
               sTo = squares[toX][toY];
        Piece from = sFrom.getPiece();
        if (from == null)
            return false;

        if(validMove(sFrom, sTo)) {
            movePiece(from, sFrom, sTo);
            return true;
        }
        return false;
    }

    private boolean validMove(Square from, Square to) {
        if (isSpecialMove(from, to))
            return true;
        // TODO : use from.getPiece().isAValidMove(sFrom, sTo)
        switch (from.getPiece().getType()) {
            case PAWN:
                return (from.getPiece().isAValidMove(from, to) && to.getPiece() == null);
            case KING:
                // TODO : tester si le mouvemnt le met en echec, si c'est le cas --> false
                break;
            case ROOK:

                break;
            case QUEEN:
                break;
            case BISHOP:
                break;
            case KNIGHT:
                return (from.getPiece().isAValidMove(from, to));
            default:
                return false;
        }
        return false;
    }

    /*****************
     * Special moves *
     *****************/
    public boolean isSpecialMove(Square from, Square to) {
        if(from == null || to == null)
            return false;
        Piece piece = from.getPiece();
        return (isSmallR(from, to)      ||
                isBigR(from, to)        ||
                isPwanEating(from, to));
    }

    public boolean isSmallR(Square from, Square to) {
        if(from == null || to == null)
            return false;
        Piece piece = from.getPiece();
        return false;
    }

    public boolean isBigR(Square from, Square to) {
        if(from == null || to == null)
            return false;
        Piece piece = from.getPiece();
        return false;
    }

    public int[] isEnPassant(int fromX, int fromY, int toX, int toY) {
        Square from = squares[fromX][fromY],
               to = squares[toX][toY];
        int[] prayPos = new int[2];

        if (squares[to.getX()][from.getY()].getPiece() != null                      &&
            squares[to.getX()][from.getY()].getPiece().getType() == PieceType.PAWN  &&
            from.getPiece().getType() == PieceType.PAWN                             &&
            squares[to.getX()][from.getY()].getPiece().getNbrMoves() == 1           &&
            longStep(squares[to.getX()][from.getY()])                               &&
            isDiagonalOneMove(from, to)                                             ) {
            pieces.remove(squares[to.getX()][from.getY()].getPiece());
            prayPos[0] = to.getX();
            prayPos[1] = from.getY();
        } else {
            prayPos[0] = -1;
            prayPos[1] = -1;
        }

        return prayPos;
    }

    private boolean longStep(Square square) {
        if (square.getPiece().getColor() == PlayerColor.WHITE)
            return (square.getY() == 3);
        else
            return (square.getY() == 4);
    }

    private boolean isDiagonalOneMove(Square from, Square to) {
        if(to.getY() < 0 || to.getX() < 0 || to.getY() >= squares.length || to.getX() >= squares.length)
            return false;
        return (abs(from.getY() - to.getY()) == 1 && abs(from.getX() - to.getX()) == 1);
    }

    public boolean isPwanEating(Square from, Square to) {
        Piece piece = from.getPiece();
        return false;
    }

    /***********
     * Getters *
     ***********/
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

    public int[] getPosition(Piece piece) {
        int[] pos = new int[2];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                pos[0] = squares[i][j].getX();
                pos[1] = squares[i][j].getY();
                if (squares[i][j].getPiece() == piece)
                    return pos;
            }
        }
        pos[0] = -1;
        pos[1] = -1;
        return pos;
    }

    /*******************
     * Private methods *
     *******************/
    private void movePiece(Piece piece, Square from, Square to) {
        if(to.getPiece() != null)
            pieces.remove(piece);
        to.setPiece(piece);
        from.setPiece(null);
    }

}
