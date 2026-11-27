import enums.Color;
import enums.GameStatus;
import models.Move;
import models.Piece;
import models.Player;
import models.Position;
import models.Queen;

import java.util.List;

public class ChessGameTest {
    private static int checks;

    public static void main(String[] args) {
        turnAndHistoryRules();
        foolsMate();
        scholarsMate();
        invalidPlayers();
        System.out.println("ChessGameTest: " + checks + " checks passed");
    }

    private static void turnAndHistoryRules() {
        ChessGame game = newGame();
        Player white = game.getCurrentPlayer();
        String initial = game.getBoard().toString();
        check(game.getStatus() == GameStatus.IN_PROGRESS, "New game should be active");
        check(white.color() == Color.WHITE, "White moves first");
        check(!move(game, "e7", "e5"), "Black cannot move first");
        check(!game.makeMove(new Position(8, 0), at("e4")), "Row 8 must not crash the game");
        check(!game.makeMove(at("e2"), new Position(0, 8)), "Column 8 must not crash the game");
        check(!game.makeMove(null, at("e4")), "Null move must be rejected");
        check(!move(game, "e2", "e5"), "Pawn cannot move three squares");
        check(game.getCurrentPlayer() == white && game.getHistory().isEmpty(),
                "Invalid moves must preserve the turn and history");
        check(game.getBoard().toString().equals(initial), "Invalid moves must preserve the board");
        Piece pawn = game.getBoard().getPiece(at("e2"));
        check(move(game, "e2", "e4"), "Legal white move should succeed");
        check(game.getCurrentPlayer().color() == Color.BLACK, "Successful move switches the turn");
        check(game.getBoard().getPiece(at("e4")) == pawn && game.getBoard().getPiece(at("e2")) == null,
                "Successful move transfers the original piece");
        check(!move(game, "d2", "d4"), "White cannot move twice");
        List<Move> snapshot = game.getHistory();
        try {
            snapshot.clear();
            throw new AssertionError("History must be read-only");
        } catch (UnsupportedOperationException expected) {
            checks++;
        }
        check(move(game, "d7", "d5"), "Legal black reply should succeed");
        Piece captured = game.getBoard().getPiece(at("d5"));
        check(move(game, "e4", "d5"), "Pawn capture should succeed");
        Move capture = game.getHistory().get(2);
        check(capture.movedPiece() == pawn && capture.capturedPiece() == captured,
                "History should preserve moved and captured pieces");
        check(capture.toString().contains("e4 -> d5 capturing p"), "Capture should have a readable log");
        check(snapshot.size() == 1, "Previously returned history should remain a stable snapshot");
    }

    private static void foolsMate() {
        ChessGame game = newGame();
        play(game, new String[][] {{"f2", "f3"}, {"e7", "e5"}, {"g2", "g4"}, {"d8", "h4"}});
        check(game.getStatus() == GameStatus.BLACK_WINS, "Fool's Mate must end in a black win");
        check(game.getBoard().isInCheck(Color.WHITE), "White king should be in check");
        check(!game.getBoard().hasLegalMove(Color.WHITE), "White has no escape from Fool's Mate");
        check(!move(game, "a2", "a3"), "No moves may be played after checkmate");
        check(game.getHistory().size() == 4, "Rejected post-game move must not enter history");
    }

    private static void scholarsMate() {
        ChessGame game = newGame();
        play(game, new String[][] {{"e2", "e4"}, {"e7", "e5"}, {"d1", "h5"}, {"b8", "c6"},
                {"f1", "c4"}, {"g8", "f6"}, {"h5", "f7"}});
        check(game.getStatus() == GameStatus.WHITE_WINS, "Scholar's Mate must end in a white win");
        check(game.getBoard().getPiece(at("f7")) instanceof Queen, "White queen ends on f7");
        check(game.getHistory().get(6).capturedPiece().getColor() == Color.BLACK,
                "Final move captures the black f7 pawn");
    }

    private static void invalidPlayers() {
        Player white = new Player("Alice", Color.WHITE);
        Player black = new Player("Bob", Color.BLACK);
        for (Player[] players : new Player[][] {{black, white}, {white, white}, {null, black}, {white, null}}) {
            try {
                new ChessGame(players[0], players[1]);
                throw new AssertionError("Invalid player setup must be rejected");
            } catch (IllegalArgumentException expected) {
                checks++;
            }
        }
    }

    private static ChessGame newGame() {
        return new ChessGame(new Player("Alice", Color.WHITE), new Player("Bob", Color.BLACK));
    }

    private static void play(ChessGame game, String[][] moves) {
        for (int i = 0; i < moves.length; i++) {
            check(game.getStatus() == GameStatus.IN_PROGRESS, "Game must remain active before move " + (i + 1));
            check(move(game, moves[i][0], moves[i][1]), "Move must succeed: " + moves[i][0] + " -> " + moves[i][1]);
        }
    }

    private static boolean move(ChessGame game, String from, String to) {
        return game.makeMove(at(from), at(to));
    }

    private static Position at(String square) {
        return new Position('8' - square.charAt(1), square.charAt(0) - 'a');
    }

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }
}
