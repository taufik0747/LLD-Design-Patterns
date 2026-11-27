import enums.Color;
import models.Player;
import models.Position;
import models.Move;

public class ChessGameDemo {

    public static void main(String[] args) {

        Player white = new Player("Alice", Color.WHITE);
        Player black = new Player("Bob", Color.BLACK);

        ChessGame game = new ChessGame(white, black);
        System.out.println("Fool's Mate: 1. f3 e5 2. g4 Qh4#");
        System.out.println(game.getBoard());

        playMove(game,
                new Position(6, 5),
                new Position(5, 5)
        );

        playMove(game,
                new Position(1, 4),
                new Position(3, 4)
        );

        playMove(game,
                new Position(6, 6),
                new Position(4, 6)
        );

        playMove(game,
                new Position(0, 3),
                new Position(4, 7)
        );

        System.out.println("Game status: " + game.getStatus());
        System.out.println("Move history:");
        for (Move move : game.getHistory()) {
            System.out.println("  " + move);
        }
    }

    private static void playMove(ChessGame game, Position from, Position to) {
        if (!game.makeMove(from, to)) {
            throw new IllegalStateException("Demo move rejected: " + from + " -> " + to);
        }
        System.out.println("\n" + from + " -> " + to);
        System.out.println(game.getBoard());
    }
}
