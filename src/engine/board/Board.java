package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.*;

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

        if (from == null            ||
            sTo == sFrom            ||
            !validMove(sFrom, sTo)  ||
            (sTo.getPiece() != null  &&
            from.getColor() == sTo.getPiece().getColor()))
            return false;

        movePiece(sFrom, sTo);
        return true;
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
            movePiece(rook, rookEndPos);
            movePiece(king, squares[toX][toY]);
        }

        return rookPos;
    }

    /**
     * Checks if the a king of a the given color is in a check situation if in the given Square (position)
     * @param king (Square) position in which to test the check situation for the king
     * @param color (PlayerColor) color of the king to check
     * @return true if the king would be in a check situation, false otherwise
     */
    public boolean isInCheck(Square king, PlayerColor color) {
        boolean changePiece = false;

        if(king.getPiece() == null) {
            changePiece = true;
            king.setPiece(new King(color));
        }

        for (Square[] line : squares) {
            for (Square square : line) {
                if(canKill(square, king)) {
                    if(changePiece)
                        king.setPiece(null);
                    return true;
                }
            }
        }
        if(changePiece)
            king.setPiece(null);
        return false;
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

        if (pieceType != PieceType.KING)
            return from.getPiece().isAValidMove(from, to) && !hasObstacle(from, to);
        else {
            if(isInCheck(to, from.getPiece().getColor()))
                return false;

            to.setPiece(null);
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
        int minX = min(from.getX(), to.getX()),
            minY = min(from.getY(), to.getY()),
            maxX = max(from.getX(), to.getX()),
            maxY = max(from.getY(), to.getY());
        int cpt = 0;
        for (int x = minX; x <= maxX; ++x)
            for (int y = minY; y <= maxY; ++y)
                if(!(squares[x][y].isEmpty()))
                    ++cpt;

        Square path[] = new Square[cpt];
        cpt = 0;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                if(!(squares[x][y].isEmpty()))
                    path[cpt++] = squares[x][y];
            }
        }
        return from.getPiece().hasObstacle(path, cpt);
    }

    /**
     * Checks if the given Piece position can move to the given king putting it in a check situation
     * @param from (Square) origin of the move
     * @param king (Square) possible destination of the given piece and position of the King
     * @return true if the king is in chack situation, false otherwise
     */
    private boolean canKill(Square from, Square king) {
        //it there is nothing on the square...obvious
        if(from.getPiece() == null)
            return false;
        // You can't eat your own king
        else if (from.getPiece().getColor() == king.getPiece().getColor())
            return false;
        // if a piece from the other color can reach the place the king wants to move to
        // the move is not valid
        else return from.getPiece().hasCheckmateTheKing(from, king) && !hasObstacle(from, king);
    }

    /**
     * Checks if the Board is in a check mate situation, meaning, one of the king is stuck
     * @return
     */
    private boolean isCheckMate() {
        return false;
    }
}
