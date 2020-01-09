package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Board {
    private Square[][] squares;
    private List<Piece> pieces;

    public Board(){
        squares = new Square[8][8];

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                squares[i][j] = new Square(i, j);
        pieces = new ArrayList<>();
        init();
    }

    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        Square sFrom = squares[fromX][fromY],
               sTo = squares[toX][toY];
        Piece from = sFrom.getPiece();

        if (from != null && validMove(sFrom, sTo)) {
            if(sTo.getPiece() != null && sFrom.getPiece().getColor() == sTo.getPiece().getColor())
                return false;
            movePiece(from, sFrom, sTo);
            return true;
        }
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
        for (Square[] square : squares)
            for (int i = 0; i < squares[0].length; i++) {
                pos[0] = square[i].getX();
                pos[1] = square[i].getY();
                if (square[i].getPiece() == piece)
                    return pos;
            }
        pos[0] = -1;
        pos[1] = -1;
        return pos;
    }

    /*******************
     * Private methods *
     *******************/
    private void init(){
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

    private void movePiece(Piece piece, Square from, @NotNull Square to) {
        if(to.getPiece() != null)
            pieces.remove(piece);
        to.setPiece(piece);
        from.setPiece(null);
    }

    private boolean validMove(@NotNull Square from, Square to) {
        // TODO : use from.getPiece().isAValidMove(sFrom, sTo)
        PieceType pieceType = from.getPiece().getType();

        if(pieceType == PieceType.PAWN)
            return ((from.getPiece().isAValidMove(from, to) && to.getPiece() == null) || isPwanEating(from, to));

        if(pieceType == PieceType.KNIGHT)
            return (from.getPiece().isAValidMove(from, to));

        if(pieceType == PieceType.ROOK)
            if(isSmallR(from, to) || isBigR(from, to))
                return true;

        if(pieceType == PieceType.KING) {
            if(isSmallR(from, to) || isBigR(from, to))
                return true;
            // TODO : tester si le mouvemnt le met en echec, si c'est le cas --> false
        }

        if(from.getPiece().isAValidMove(from, to))
            return !hasObstacle(from, to);
        return false;
    }

    private boolean hasObstacle(@NotNull Square from, Square to) {
        PieceType pieceType = from.getPiece().getType();

        if(pieceType == PieceType.ROOK || (pieceType == PieceType.QUEEN && !isDiagonalMove(from, to)))
            return hasLinearObstacle(from, to);

        if(pieceType == PieceType.BISHOP || (pieceType == PieceType.QUEEN && isDiagonalMove(from, to)))
            return hasDiagonalObstacle(from, to);
        return true;
    }

    private boolean hasDiagonalObstacle(@NotNull Square from, Square to) {
        boolean up = to.getY() > from.getY(),
                right = to.getX() > from.getX();
        if(up) {
            if(right)
                for (int i = 1; i < to.getY()-from.getY() ; ++i) {
                    if (squares[from.getX() + i][from.getY() + i].getPiece() != null)
                        return true;
                }
            else
                for (int i = 1; i < to.getY()-from.getY() ; ++i) {
                    if (squares[from.getX() - i][from.getY() + i].getPiece() != null)
                        return true;
                }
        } else {
            if(right)
                for (int i = 1; i < from.getY()-to.getY() ; ++i) {
                    if (squares[from.getX() + i][from.getY() - i].getPiece() != null)
                        return true;
                }
            else
                for (int i = 1; i < from.getY()-to.getY() ; ++i) {
                    if (squares[from.getX() - i][from.getY() - i].getPiece() != null)
                        return true;
                }
        }
        return false;
    }

    private  boolean hasLinearObstacle(@NotNull Square from, Square to) {
        boolean up = to.getY() > from.getY(),
                right = to.getX() > from.getX();
        if(up)
            for (int y = to.getY()-1; y > from.getY() ; --y) {
                if (squares[from.getX()][y].getPiece() != null)
                    return true;
            }
        else
            for (int y = to.getY()+1; y < from.getY() ; ++y) {
                if (squares[from.getX()][y].getPiece() != null)
                    return true;
            }
        if(right)
            for (int x = to.getX()+1; x < from.getX() ; ++x) {
                if (squares[x][from.getY()].getPiece() != null)
                    return true;
            }
        else
            for (int x = to.getX()-1; x > from.getX() ; --x) {
                if (squares[x][from.getY()].getPiece() != null)
                    return true;
            }
        return false;
    }

    private boolean isSmallR(Square from, Square to) {
        //TODO : petit roque
        return false;
    }

    private boolean isBigR(Square from, Square to) {
        //TODO : grand roque
        return false;
    }

    private boolean longStep(@NotNull Square square) {
        if (square.getPiece().getColor() == PlayerColor.WHITE)
            return (square.getY() == 3);
        else
            return (square.getY() == 4);
    }

    private boolean isDiagonalMove(Square from, @NotNull Square to) {
        if(to.getY() < 0 || to.getX() < 0 || to.getY() >= squares.length || to.getX() >= squares.length)
            return false;
        return (abs(from.getY() - to.getY()) == abs(from.getX() - to.getX()));
    }

    private boolean isDiagonalOneMove(@NotNull Square from, @NotNull Square to) {
        return abs(from.getY() - to.getY()) == 1 && isDiagonalMove(from, to);
    }

    private boolean isPwanEating(Square from, Square to) {
        return (isDiagonalOneMove(from, to) && to.getPiece() != null);
    }
}
