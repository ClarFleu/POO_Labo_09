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

    public Piece getPiece(){
        return piece;
    }

    /**
     * Place the piece in the current square
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public boolean didLongStep() {
        if(piece == null || piece.getType() != PieceType.PAWN)
            return false;
        return (((piece.getColor() == PlayerColor.WHITE) && y == 3 ) ||
                ((piece.getColor() == PlayerColor.BLACK) && y == 4));
    }
}
