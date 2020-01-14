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

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                squares[i][j] = new Square(i, j);
        pieces = new ArrayList<>();
        init();
    }

    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        Square sFrom = squares[fromX][fromY],
               sTo   = squares[toX][toY];
        Piece from   = sFrom.getPiece();

        if (from != null && validMove(sFrom, sTo)) {
            if(sTo.getPiece() != null && sFrom.getPiece().getColor() == sTo.getPiece().getColor())
                return false;
            else
                movePiece(from, sFrom, sTo);
            return true;
        }
        return false;
    }

    public boolean isPromotion(int fromX, int fromY, int toX, int toY) {
        Piece p = squares[fromX][fromY].getPiece();
        return (p.getType() == PieceType.PAWN && ((Pawn)p).isPromotion(toY) && isValid(fromX, fromY, toX, toY));
    }

    public void doPromotion(int x, int y, PieceType type, PlayerColor color) {
        switch (type) {
            case ROOK:
                squares[x][y].setPiece(new Rook(color));
                break;
            case QUEEN:
                squares[x][y].setPiece(new Queen(color));
                break;
            case BISHOP:
                squares[x][y].setPiece(new Bishop(color));
                break;
            case KNIGHT:
                squares[x][y].setPiece(new Knight(color));
                break;
            default: break;
        }
    }

    public int[] isEnPassant(int fromX, int fromY, int toX, int toY) {
        Square from = squares[fromX][fromY],
               to = squares[toX][toY];
        int[] prayPos = new int[2];

        if (squares[to.getX()][from.getY()].getPiece() != null                      &&
            squares[to.getX()][from.getY()].getPiece().getType() == PieceType.PAWN  &&
            squares[to.getX()][from.getY()].getPiece().getNbrMoves() == 1           &&
            longStep(squares[to.getX()][from.getY()])                               &&
            isDiagonalOnePawnMove(from, to)                                             ) {
            /* Eat the pray */
            pieces.remove(squares[to.getX()][from.getY()].getPiece());
            squares[to.getX()][from.getY()].setPiece(null);
            /* Move the 'eater' */
            to.setPiece(from.getPiece());
            from.setPiece(null);
            prayPos[0] = to.getX();
            prayPos[1] = from.getY();
        } else {
            prayPos[0] = -1;
            prayPos[1] = -1;
        }
        return prayPos;
    }

    public int[] isRoque(int fromX, int fromY, int toX, int toY) {
        Square king = squares[fromX][fromY];
        int[] rookPos = {-1, -1};
        if(abs(toX - fromX) == 2 && (toY - fromY == 0) && king.getPiece().getNbrMoves() == 0) {
            Square rook;
            Square rookEndPos;
            if(toX < fromX) {
                if(king.getPiece().getColor() == PlayerColor.WHITE) {
                    rook = squares[0][0];
                    rookPos[0] = 0;
                    rookPos[1] = 0;
                    rookEndPos = squares[3][0];
                } else {
                    rook = squares[0][7];
                    rookPos[0] = 0;
                    rookPos[1] = 7;
                    rookEndPos = squares[2][7];
                }
            } else {
                if (king.getPiece().getColor() == PlayerColor.WHITE) {
                    rook = squares[7][0];
                    rookPos[0] = 7;
                    rookPos[1] = 0;
                    rookEndPos = squares[5][0];
                } else {
                    rook = squares[7][7];
                    rookPos[0] = 7;
                    rookPos[1] = 7;
                    rookEndPos = squares[4][7];
                }
            }
            if(rook.getPiece() == null || rook.getPiece().getNbrMoves() != 0 || hasObstacle(rook, king)) {
                rookPos[0] = -1;
                rookPos[1] = -1;
                return rookPos;
            }
            movePiece(rook.getPiece(), king, rookEndPos);
            movePiece(rook.getPiece(), king, squares[toX][toY]);
        }

        return rookPos;
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

    private void movePiece(Piece piece, Square from, Square to) {
        if(to.getPiece() != null)
            pieces.remove(to.getPiece());
        to.setPiece(piece);
        from.setPiece(null);
    }

    private boolean validMove(Square from, Square to) {
        PieceType pieceType = from.getPiece().getType();

        if(pieceType == PieceType.PAWN)
            return ((from.getPiece().isAValidMove(from, to) && to.getPiece() == null) || isPwanEating(from, to));

        if(pieceType == PieceType.KNIGHT)
            return (from.getPiece().isAValidMove(from, to));

        if(pieceType == PieceType.KING) {
            to.setPiece(from.getPiece());
            for (Square[] line : squares) {
                for (Square square : line) {
                    if(isChec(square, to)){
                        return false;
                    }
                }
            }
            return (from.getPiece().isAValidMove(from, to));
        }

        if(from.getPiece().isAValidMove(from, to))
            return !hasObstacle(from, to);
        return false;
    }

    private boolean hasObstacle(Square from, Square to) {
        PieceType pieceType = from.getPiece().getType();

        if(pieceType == PieceType.ROOK || (pieceType == PieceType.QUEEN && !isDiagonalMove(from, to)))
            return hasLinearObstacle(from, to);

        if(pieceType == PieceType.BISHOP || (pieceType == PieceType.QUEEN && isDiagonalMove(from, to)))
            return hasDiagonalObstacle(from, to);
        return true;
    }

    private boolean hasDiagonalObstacle(Square from, Square to) {
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

    private  boolean hasLinearObstacle(Square from, Square to) {
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

    private boolean longStep(Square square) {
        if (square.getPiece().getColor() == PlayerColor.WHITE)
            return (square.getY() == 3);
        else
            return (square.getY() == 4);
    }

    private boolean isDiagonalMove(Square from, Square to) {
        if(to.getY() < 0 || to.getX() < 0 || to.getY() >= squares.length || to.getX() >= squares.length)
            return false;
        return (abs(from.getY() - to.getY()) == abs(from.getX() - to.getX()));
    }

    private boolean isDiagonalOnePawnMove(Square from, Square to) {
        return (abs(from.getY() - to.getY()) == 1   &&
                isDiagonalMove(from, to))           &&
                (from.getPiece().getColor() == PlayerColor.WHITE &&
                 from.getY() < to.getY() ||
                 from.getPiece().getColor() == PlayerColor.BLACK &&
                 from.getY() > to.getY());
    }

    private boolean isPwanEating(Square from, Square to) {
        return (isDiagonalOnePawnMove(from, to) && to.getPiece() != null);
    }

    private boolean isChec(Square from, Square king) {
        // You can't eat your own king
        if(from.getPiece() == null)
            return false;
        if (from.getPiece().getColor() == king.getPiece().getColor())
            return false;

        // if a piece from the other color can reach the place the king wants to move to
        // the move is not valid
        if ((from.getPiece().getType() == PieceType.QUEEN ||
                from.getPiece().getType() == PieceType.BISHOP ||
                from.getPiece().getType() == PieceType.ROOK) &&
                !hasObstacle(from, king)) {
            return false;
        }
        if (from.getPiece().getType() != PieceType.PAWN && from.getPiece().isAValidMove(from, king)) {
            return false;
        }
        if(isPwanEating(from, king)) {
            return false;
        }

        return false;
    }

    private boolean isChecMate() {
        return false;
    }
}
