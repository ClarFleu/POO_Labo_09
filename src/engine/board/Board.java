package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class Board {
    private Square[][] squares;

    public Board(){
        squares = new Square[8][8];

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                squares[i][j] = new Square(i, j);
        init();
    }

    /**
     * Checks if the move from the given position to the given final position is a valid move
     * @param fromX (int) value x of the origin position
     * @param fromY (int) value y of the origin position
     * @param toX (int) value x of the final position
     * @param toY (int) value y of the final position
     * @return true if the move is leagal and can be made, false otherwise
     */
    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        Square sFrom = squares[fromX][fromY],
               sTo   = squares[toX][toY];
        Piece from   = sFrom.getPiece();

        if (from != null && validMove(sFrom, sTo)) {
            if(sTo.getPiece() != null && sFrom.getPiece().getColor() == sTo.getPiece().getColor())
                return false;
            else
                movePiece(sFrom, sTo);
            return true;
        }
        return false;
    }

    /**
     * Checks if the given movement is a promotion (when a Pawn reaches the end of the board)
     * @param fromX (int) value x of the origin position
     * @param fromY (int) value y of the origin position
     * @param toX (int) value x of the final position
     * @param toY (int) value y of the final position
     * @return true if the move is a promotion move for a pawn, false otherwise
     */
    public boolean isPromotion(int fromX, int fromY, int toX, int toY) {
        Piece p = squares[fromX][fromY].getPiece();
        return (p.getType() == PieceType.PAWN && ((Pawn)p).isPromotion(toY) && isValid(fromX, fromY, toX, toY));
    }

    /**
     * Does the promotion of a Pawn to a new Piece of the given type, and places it on the given position
     * @param x (int) value x of the position in which to place the new Piece
     * @param y (int) value y of the position in which to place the new Piece
     * @param type (PieceType) type of Piece to which the Pawn is to be promoted
     * @param color (PlayerColor) color of the Piece
     */
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

    /**
     * Checks if the given move is a "Prise en passant"
     * @param fromX (int) value x of the origin position
     * @param fromY (int) value y of the origin position
     * @param toX (int) value x of the final position
     * @param toY (int) value y of the final position
     * @return prayPos (int[]) the position of the Pawn that falls victim from the move if it is a "Prise en passant"
     *                 {-1, -1} otherwise
     */
    public int[] isEnPassant(int fromX, int fromY, int toX, int toY) {
        Square from = squares[fromX][fromY],
               to = squares[toX][toY];
        int[] prayPos = new int[2];
        Square pray = squares[to.getX()][from.getY()];

        if (from.getPiece() != null &&
            from.getPiece().getType() == PieceType.PAWN &&
            ((Pawn)from.getPiece()).isEnPassant(from, to, pray)) {
            /* Eat the pray */
            pray.setPiece(null);
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

    /**
     * Checks if the move from the given origin to the given final position is a "Roque" move
     * and finds the Rook with which the move is to be donn
     * @param fromX (int) value x of the origin position
     * @param fromY (int) value y of the origin position
     * @param toX (int) value x of the final position
     * @param toY (int) value y of the final position
     * @return prayPos (int[]) the position of the Rook with which the King will change position
     *                 if it is a "Roque" move
     *                 {-1, -1} otherwise
     */
    public int[] isRoque(int fromX, int fromY, int toX, int toY) {
        Square king = squares[fromX][fromY];
        int[] rookPos = {-1, -1};
        if (abs(toX - fromX) == 2 &&
            (toY - fromY == 0) &&
            king.getPiece().getNbrMoves() == 0) {
            Square rook;
            Square rookEndPos;
            if(toX < fromX) {
                rookPos[0] = 0;
                if(king.getPiece().getColor() == PlayerColor.WHITE) {
                    rook = squares[0][0];
                    rookPos[1] = 0;
                    rookEndPos = squares[3][0];
                } else {
                    rook = squares[0][7];
                    rookPos[1] = 7;
                    rookEndPos = squares[3][7];
                }
            } else {
                rookPos[0] = 7;
                if (king.getPiece().getColor() == PlayerColor.WHITE) {
                    rook = squares[7][0];
                    rookPos[1] = 0;
                    rookEndPos = squares[5][0];
                } else {
                    rook = squares[7][7];
                    rookPos[1] = 7;
                    rookEndPos = squares[5][7];
                }
            }
            if(rook.getPiece() == null || rook.getPiece().getNbrMoves() != 0 || hasObstacle(rook, king)) {
                rookPos[0] = -1;
                rookPos[1] = -1;
                return rookPos;
            }
            movePiece(king, rookEndPos);
            movePiece(king, squares[toX][toY]);
        }

        return rookPos;
    }

    /**********
     * Getter *
     **********/
    /**
     * Getter for the variable squares of the board, which is a list of positions and Pieces
     * @return squares (Square[][]) the list giving the actual status of the board game
     */
    public Square[][] getSquares() {
        return squares;
    }

    /*******************
     * Private methods *
     *******************/
    /**
     * Method initialising the board in the beginning of a game
     */
    private void init() {
        squares[0][0].setPiece(new Rook(PlayerColor.WHITE));
        squares[1][0].setPiece(new Knight(PlayerColor.WHITE));
        squares[2][0].setPiece(new Bishop(PlayerColor.WHITE));
        squares[3][0].setPiece(new Queen(PlayerColor.WHITE));
        squares[4][0].setPiece(new King(PlayerColor.WHITE));
        squares[5][0].setPiece(new Bishop(PlayerColor.WHITE));
        squares[6][0].setPiece(new Knight(PlayerColor.WHITE));
        squares[7][0].setPiece(new Rook(PlayerColor.WHITE));

        squares[0][7].setPiece(new Rook(PlayerColor.BLACK));
        squares[1][7].setPiece(new Knight(PlayerColor.BLACK));
        squares[2][7].setPiece(new Bishop(PlayerColor.BLACK));
        squares[3][7].setPiece(new Queen(PlayerColor.BLACK));
        squares[4][7].setPiece(new King(PlayerColor.BLACK));
        squares[5][7].setPiece(new Bishop(PlayerColor.BLACK));
        squares[6][7].setPiece(new Knight(PlayerColor.BLACK));
        squares[7][7].setPiece(new Rook(PlayerColor.BLACK));

        for (int i = 0; i < 8; i++) {
            squares[i][1].setPiece(new Pawn(PlayerColor.WHITE));
            squares[i][6].setPiece(new Pawn(PlayerColor.BLACK));
        }
    }

    /**
     * Does the given move
     * @param from (Square) origin of the move to be done
     * @param to (Square) final position of the Piece affter the move
     */
    private void movePiece(Square from, Square to) {
        to.setPiece(from.getPiece());
        from.setPiece(null);
    }

    /**
     * Checks if the move from the given origin to the given destination is valid
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if the move is valid, false otherwise
     */
    private boolean validMove(Square from, Square to) {
        PieceType pieceType = from.getPiece().getType();

        if (from.getPiece().canBeBlocked()) {
            return from.getPiece().isAValidMove(from, to) && !hasObstacle(from, to);
        } else if (pieceType != PieceType.KING)
            return from.getPiece().isAValidMove(from, to);
        else {
            //to.setPiece(from.getPiece());
            for (Square[] line : squares) {
                for (Square square : line) {
                    //if(isChec(square, to)) {
                        // TODO faire le test de la mise en echec
                        //return false;
                    //}
                }
            }
            return (from.getPiece().isAValidMove(from, to));
        }
    }

    /**
     * Checks if the given move is impeded by an obstacle of any sort
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if there is an obstacle, false otherwise
     */
    private boolean hasObstacle(Square from, Square to) {
        PieceType pieceType = from.getPiece().getType();

        if(pieceType == PieceType.ROOK || (pieceType == PieceType.QUEEN && !isDiagonalMove(from, to)))
            return hasLinearObstacle(from, to);

        if(pieceType == PieceType.BISHOP || (pieceType == PieceType.QUEEN && isDiagonalMove(from, to)))
            return hasDiagonalObstacle(from, to);
        return true;
    }

    /**
     * Checks if the given diagonal move is impeded by an obstacle of any sort
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if there is an obstacle, false otherwise
     */
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

    /**
     * Checks if the given linear move is impeded by an obstacle of any sort
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if there is an obstacle, false otherwise
     */
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

    /**
     * Checks if the given move is a diagonal move
     * @param from (Square) origin of the move
     * @param to (Square) destination of the move
     * @return true if the move is a diagonal move, false otherwise
     */
    private boolean isDiagonalMove(Square from, Square to) {
        if(to.getY() < 0 || to.getX() < 0 || to.getY() >= squares.length || to.getX() >= squares.length)
            return false;
        return (abs(from.getY() - to.getY()) == abs(from.getX() - to.getX()));
    }

    /**
     * Checks if the given Piece position can move to the given king putting it in a check situation
     * @param from (Square) origin of the move
     * @param king (Square) possible destination of the given piece and position of the King
     * @return true if the king is in chack situation, false otherwise
     */
    private boolean isChec(Square from, Square king) {
        // TODO tout re-faire /!\
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
        return false;
    }

    /**
     * Checks if the Board is in a check mate situation, meaning, one of the king is stuck
     * @return
     */
    private boolean isChecMate() {
        return false;
    }
}
