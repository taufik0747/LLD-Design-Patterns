import model.Board;
import model.Dice;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class SnakeAndLadderGame {
    private Board board;
    private Dice dice;
    private List<Player> players;
    private int currentPlayerIndex;

    public SnakeAndLadderGame(List<String> playerNames) {
        board = new Board();
        dice = new Dice();
        players = new ArrayList<>();

        for (String name : playerNames) {
            players.add(new Player(name));
        }
        currentPlayerIndex = 0;
    }

    public void playTurn() {
        Player player = players.get(currentPlayerIndex);
        System.out.println(player.getName() + "'s turn:");

        int diceValue = dice.roll();
        System.out.println("Rolled: " + diceValue);

        int newPosition = player.getPosition() + diceValue;

        if (newPosition > 100) {
            System.out.println("Move exceeds 100, wait for next turn.");
        } else {
            newPosition = board.getNewPosition(newPosition);
            player.setPosition(newPosition);
            System.out.println(player.getName() + " moved to: " + newPosition);

            if (player.hasWon()) {
                System.out.println(player.getName() + " has won the game!");
                System.exit(0);
            }
        }

        // Move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
