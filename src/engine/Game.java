package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Piece;
import engine.board.Rook;
import engine.board.Square;

public class Game implements ChessController {
    private Board board;
    private Player player1, player2;
    private ChessView view;

    public Game() {
        player1 = new Player(PlayerColor.WHITE, true);
        player2 = new Player(PlayerColor.BLACK, false);
        board = new Board();
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

        // Check that the right piece is being moved (the black player can't move a white pawn)
        // And change turns for the next move
        if (turnChange(from)) {
            if(from.getType() != PieceType.KING) {
                // do the move
                Piece tmp = board.getSquares()[toX][toY].getPiece();
                board.getSquares()[fromX][fromY].setPiece(null);
                board.getSquares()[toX][toY].setPiece(from);
                // Check if the move will let the player in check state
                if (board.isInCheck(player1.isTurn() ? player2.getKing() : player1.getKing(),
                        from.getColor())) {
                    player1.changeTurn();
                    player2.changeTurn();

                    // undo the move
                    board.getSquares()[fromX][fromY].setPiece(from);
                    board.getSquares()[toX][toY].setPiece(tmp);
                    return false;
                }
                // undo the move
                board.getSquares()[fromX][fromY].setPiece(from);
                board.getSquares()[toX][toY].setPiece(tmp);
            }

            int[] rookPos = board.isRoque(fromX, fromY, toX, toY);
            int[] passPos = board.isEnPassant(fromX, fromY, toX, toY);
            // If the piece to be moved is a Pawn
            // the move can be the promotion of said Pawn
            if (passPos[0] < 0 && from.getType() == PieceType.PAWN && board.isPromotion(fromX, fromY, toX, toY)) {
                PieceChoice choices[] = {
                        new PieceChoice(PieceType.BISHOP),
                        new PieceChoice(PieceType.KNIGHT),
                        new PieceChoice(PieceType.QUEEN),
                        new PieceChoice(PieceType.ROOK)};
                PieceChoice choice = view.askUser(
                        "Pawn promotion",
                        "Your pawn can be promoted, what PieceType do you want it to evolve to?",
                        choices);
                if (choice != null) {
                    PlayerColor color = (player1.isTurn() ? player2.
                            getPlayerColor() : player1.getPlayerColor());
                    view.putPiece(choice.getType(), color, toX, toY);
                    board.doPromotion(toX, toY, choice.getType(), color);
                    view.removePiece(fromX, fromY);

                    if (board.isInCheck((player1.isTurn() ? player1 : player2).getKing(),
                            (player1.isTurn() ? player1 : player2).getPlayerColor())) {
                        view.displayMessage("Echec !");
                    }
                    return true;
                }
            }

            // If the piece to be moved is a King
            // the move can be a Roque (small or big)
            if(from.getType() == PieceType.KING) {
                if (rookPos[0] != -1) {
                    if(rookPos[1] == 0) {
                        movePiece(new Rook(from.getColor()),
                                rookPos[0], rookPos[1],
                                (rookPos[0] == 0 ? 3 : 5), 0);
                    } else {
                        movePiece(new Rook(from.getColor()),
                                rookPos[0], rookPos[1],
                                (rookPos[0] == 0 ? 3 : 5), 7);
                    }
                    movePiece(from, fromX, fromY, toX, toY);

                    if (board.isInCheck((player1.isTurn() ? player1 : player2).getKing(),
                                        (player1.isTurn() ? player1 : player2).getPlayerColor())){
                        view.displayMessage("Echec !");
                    }
                    setKing(board.getSquares()[toX][toY]);
                    return true;
                }
            }

            // All other pieces or moves can be tested in the default method
            if (board.isValid(fromX, fromY, toX, toY) || passPos[0] >= 0) {
                // King movement
                if(from.getType() == PieceType.KING)
                    setKing(board.getSquares()[toX][toY]);
                // Prise en passant
                if(passPos[0] >= 0 && passPos[1] >= 0)
                    view.removePiece(passPos[0], passPos[1]);

                movePiece(from, fromX, fromY, toX, toY);
                from.moved();

                if (board.isInCheck((player1.isTurn() ? player1 : player2).getKing(),
                                    (player1.isTurn() ? player1 : player2).getPlayerColor())){
                    view.displayMessage("Echec !");
                }
                return true;
            }
            // If the move was not a leagal chess move, the players role revert back
            player1.changeTurn();
            player2.changeTurn();
        }

        if (board.isInCheck((player1.isTurn() ? player1 : player2).getKing(),
                            (player1.isTurn() ? player1 : player2).getPlayerColor())){
            view.displayMessage("Echec !");
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

    /*******************
     * Private methods *
     *******************/
    /**
     * Initialize the view in the state of the board
     */
    private void initView() {
        for (Square[] squares : board.getSquares()) {
            for (Square square: squares) {
                if(square.getPiece() != null) {
                    if(square.getPiece().getType() == PieceType.KING)
                        setKing(square);
                    int x = square.getX();
                    int y = square.getY();
                    if (x >= 0)
                        view.putPiece(square.getPiece().getType(), square.getPiece().getColor(), x, y);
                }
            }
        }
    }

    /**
     * Checks if the player moved one of his Pieces and changes the current player
     * @param piece (Piece) piece moved by the current player
     * @return true if it is the right piece false otherwise
     */
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

    /**
     * Sets the given king for the right player
     * @param king (King)
     */
    private void setKing(Square king) {
        if(king.getPiece() == null || king.getPiece().getType() != PieceType.KING)
            return;
        if (king.getPiece().getColor() == player1.getPlayerColor())
            player1.setKing(king);
        else
            player2.setKing(king);
    }

    /**
     * Makes the given move on the view
     * @param p (Piece) piece to move
     * @param fromX (int) x value of the origin of the move
     * @param fromY (int) y value of the origin of the move
     * @param toX (int) x value of the destination of the move
     * @param toY (int) y value of the destination of the move
     */
    private void movePiece(Piece p, int fromX, int fromY, int toX, int toY ) {
        view.removePiece(fromX, fromY);
        view.putPiece(p.getType(), p.getColor(), toX, toY);
    }
}
