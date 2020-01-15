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
        return (((sqrt(x + y) == 1)||sqrt(x + y) == sqrt(2)) && checkBounds(to));
    }

    @Override
    public boolean canBeBlocked() {
        return false;
    }

    @Override
    public boolean hasObstacle(Square[] path, int pathSize) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

}
