package engine;

import chess.ChessView;
import chess.PieceType;

public class PieceChoice implements ChessView.UserChoice {
    private PieceType type;

    public PieceChoice(PieceType type) {
        this.type = type;
    }

    /**
     * Get the type of the piece choice
     * @return type (PieceType) type of the piece choice
     */
    public PieceType getType() {
        return type;
    }

    @Override
    public String textValue() {
        switch (type) {
            case KNIGHT: return "Knight";
            case ROOK: return "Rook";
            case QUEEN: return "Queen";
            case BISHOP: return "Bishop";
            default: return "Oups this is a bad choice.";
        }
    }
}
