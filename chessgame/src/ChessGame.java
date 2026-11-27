import enums.Color;
import enums.GameStatus;
import models.Board;
import models.Move;
import models.Player;
import models.Position;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {

    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;

    private Player currentPlayer;

    private GameStatus status = GameStatus.IN_PROGRESS;
    private final List<Move> history = new ArrayList<>();

    public ChessGame(Player whitePlayer, Player blackPlayer) {
        if (whitePlayer == null || whitePlayer.color() != Color.WHITE
                || blackPlayer == null || blackPlayer.color() != Color.BLACK) {
            throw new IllegalArgumentException("Provide a white player followed by a black player");
        }
        this.board = new Board();
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
    }

    public Board getBoard() { return board; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public GameStatus getStatus() { return status; }
    public List<Move> getHistory() { return List.copyOf(history); }

    public boolean makeMove(Position from, Position to) {
        if (status != GameStatus.IN_PROGRESS) {
            return false;
        }
        if (!board.isLegalMove(from, to, currentPlayer.color())) {
            return false;
        }
        Move move = new Move(from, to, board.getPiece(from), board.getPiece(to));
        board.movePiece(from, to);
        history.add(move);
        switchTurn();
        updateGameStatus();
        return true;

    }

    private void switchTurn() {
        currentPlayer = currentPlayer == whitePlayer ? blackPlayer : whitePlayer;
    }

    private void updateGameStatus() {
        Color color = currentPlayer.color();
        if (!board.hasLegalMove(color)) {
            status = board.isInCheck(color)
                    ? (color == Color.WHITE ? GameStatus.BLACK_WINS : GameStatus.WHITE_WINS)
                    : GameStatus.DRAW;
        }
    }
}
