import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<String> playerNames = Arrays.asList("Alice", "Bob", "Charlie");
        SnakeAndLadderGame game = new SnakeAndLadderGame(playerNames);
        while(true){
            game.playTurn();
        }
    }
}