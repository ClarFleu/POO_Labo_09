package engine.board;

public class Square {
    int x;
    int y;
    Piece piece;

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
    public void setPiecePosition(Piece piece){
        if (isEmpty()) {
            this.piece = piece;
        }
        else throw new RuntimeException("you can't place two pieces here");
    }

}
