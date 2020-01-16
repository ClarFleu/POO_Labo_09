package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Square {
    private int x;
    private int y;
    private Piece piece;

    public Square(int x, int y){
        this.x = x;
        this.y = y;
        piece = null;
    }

    /**
     * Check if the square is empty or not
     */
    public boolean isEmpty(){
        return piece == null ;
    }

    /**
     * Get the x-coordinate of the square
     */
    public int getX(){
        return x;
    }
    /**
     * Get the y-coordinate of the square
     */
    public int getY(){
        return y;
    }

    /**
     * Get the piece in the square
     * @return piece (Piece)
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * Place the piece in the current square
     * @param piece (Piece) piece to place on the square
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Checks if there is a Pawn that did a long step on the square
     * @return true if ther is, false otherwise
     */
    public boolean didLongStep() {
        if(piece == null || piece.getType() != PieceType.PAWN)
            return false;
        return (((piece.getColor() == PlayerColor.WHITE) && y == 3 ) ||
                ((piece.getColor() == PlayerColor.BLACK) && y == 4));
    }
}
