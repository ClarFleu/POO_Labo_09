package engine.board;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class King extends Piece {
    public King(PlayerColor color){
        super(color);
    }

    @Override
    public boolean isAValidMove(Square from, Square to) {
        double x = pow((double)(from.getX() - to.getX()) , 2);
        double y =  pow((double)(from.getY() - to.getY()) , 2);
        if((sqrt(x + y) == 1) && check_bounds(to)){
            return true;
        }else return false;
    }
}
