package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private Map<Integer,Integer> snakes;
    private Map<Integer,Integer> ladders;

    public Board(){
        snakes=new HashMap<>();
        ladders=new HashMap<>();
        setupSnakesAndLadders();
    }

    private void setupSnakesAndLadders() {
        // Define some snakes (higher to lower)
        snakes.put(99, 7);
        snakes.put(92, 35);
        snakes.put(65, 21);
        snakes.put(54, 19);
        snakes.put(17, 4);

        // Define some ladders (lower to higher)
        ladders.put(3, 22);
        ladders.put(6, 25);
        ladders.put(20, 59);
        ladders.put(27, 84);
        ladders.put(63, 97);
    }

    public int getNewPosition(int position) {
        // TODO 1: Create a visited set.
        Set<Integer> visited = new HashSet<>();

        // TODO 2: While a snake or ladder exists
        // at the current position:
        while (snakes.containsKey(position) || ladders.containsKey(position)) {
            // Check whether this position was visited already.
            if (!visited.add(position)) {
                throw new IllegalStateException(
                        "Cyclic snake/ladder movement at position " + position
                );
            }
            visited.add(position);

            // If snake exists, update position.
            if (snakes.containsKey(position)) {
                position = snakes.get(position);
            }
            // Otherwise if ladder exists, update position.
            else if (ladders.containsKey(position)) {
                position = ladders.get(position);
            }
        }

        // TODO 3: Return the final position.
        return position;
    }
}
