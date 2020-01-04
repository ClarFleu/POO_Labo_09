package engine.board;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {

    private boolean promoted;
    private Piece promotion;

    public Pawn(PlayerColor color){
        super(color);
        promoted = false;
        promotion = null;
    }

    public void promote(Piece piece){
        if(!promoted){
            promoted = true;
            promotion = piece;
        }
        else throw new RuntimeException("You can't promote this pawn twice");
    }
    public Piece getPromotion(){
        return promotion;
    }


    @Override
    public boolean isAValidMove(Square from, Square to) {
        if(promoted){
            return promotion.isAValidMove(from, to);
        }
        else{
            int x = from.getX();
            int y = from.getY();
            int x1 = to.getX();
            int y1 = to.getY();
            if (check_bounds(to) && ((x1 == x + 1) || (x1 == x -1) || (x1 == x))){/// pour l'instant puis implementer la prise en passant apres! et enlever x - 1 et x + 1 dans ce cas pt etre
                if((this.color == PlayerColor.WHITE)  && (y1 == y + 1)){
                    return true;
                }
                else if((this.color == PlayerColor.BLACK) && ((y1 == y - 1))){
                    return true;
                }
                else return false;
            }
            else return false;
        }
    }

}
