package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Piece;

import static javafx.scene.input.KeyCode.T;

public class Game implements ChessController {
    private Board board;
    private Player player1, player2;
    private ChessView view;

    public Game() {
        player1 = new Player(PlayerColor.WHITE, true);
        player2 = new Player(PlayerColor.BLACK, false);
        board = new Board();
    }

    private boolean turnChange(Piece piece) {
        if (player1.isTurn()) {
            if (piece.getColor() != player1.getPlayerColor())
                return false;
        } else {
            if (piece.getColor() == player1.getPlayerColor())
                return false;
        }
        player1.changeTurn();
        player2.changeTurn();
        return true;
    }

    private void movePiece(Piece p, int fromX, int fromY, int toX, int toY ) {
        view.removePiece(fromX, fromY);
        view.putPiece(p.getType(), p.getColor(), toX, toY);
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        initView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece from = board.getSquares()[fromX][fromY].getPiece();
        if (from == null)
            return false;

        if (turnChange(from)) {
            if (from.getType() == PieceType.PAWN) {
                int[] passPos = board.isEnPassant(fromX, fromY, toX, toY);

                if(passPos[0] >= 0 && passPos[1] >= 0) {
                    view.removePiece(passPos[0], passPos[1]);
                    movePiece(from, fromX, fromY, toX, toY);
                    from.moved();
                    return true;
                }
                if(board.isPromotion(fromX, fromY, toX, toY)) {
                    PieceChoice choices[] = {
                            new PieceChoice(PieceType.BISHOP),
                            new PieceChoice(PieceType.KNIGHT),
                            new PieceChoice(PieceType.QUEEN),
                            new PieceChoice(PieceType.ROOK)};
                    PieceChoice choice = view.askUser(
                            "Pawn promotion",
                            "Your pawn can be promoted, what PieceType do you want it to evolve to?",
                            choices);
                    PlayerColor color = (player1.isTurn() ? player2.getPlayerColor() : player1.getPlayerColor());
                    view.putPiece(choice.getType(), color, toX, toY);
                    board.doPromotion(toX, toY, choice.getType(), color);
                    view.removePiece(fromX, fromY);
                    return true;
                }
            }

            if(from.getType() == PieceType.KING) {
                int[] rookPos = board.isRoque(fromX, fromY, toX, toY);
                if (rookPos[0] != -1) {
                    if(rookPos[1] == 0) {
                        movePiece(board.getSquares()[rookPos[0]][rookPos[1]].getPiece(),
                                rookPos[0], rookPos[1],
                                (rookPos[0] == 0 ? 3 : 5), 0);
                    } else {
                        movePiece(board.getSquares()[rookPos[0]][rookPos[1]].getPiece(),
                                rookPos[0], rookPos[1],
                                (rookPos[0] == 0 ? 3 : 5), 7);
                    }
                    movePiece(from, fromX, fromY, toX, toY);
                    return true;
                }
                return false;
            }
            if (board.isValid(fromX, fromY, toX, toY)) {
                movePiece(from, fromX, fromY, toX, toY);
                from.moved();
                return true;
            }
            player1.changeTurn();
            player2.changeTurn();
        }
        return false;
    }

    @Override
    public void newGame() {
        board = new Board();
        if (player2.isTurn()) {
            player2.changeTurn();
            player1.changeTurn();
        }

        this.start(view);
    }

    private void initView() {
        for (Piece piece : board.getPieces()) {
            int x = board.getPosition(piece)[0];
            int y = board.getPosition(piece)[1];
            if(x >= 0)
                view.putPiece(piece.getType(), piece.getColor(), x, y);
        }
    }
}
