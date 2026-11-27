package models;

import enums.Color;
import java.util.Objects;

public abstract class Piece {
    private final Color color;

    protected Piece(Color color) {
        this.color = Objects.requireNonNull(color, "Piece color is required");
    }

    public Color getColor() {
        return color;
    }

    // Movement geometry; Board also enforces ownership and king safety.
    public abstract boolean canMove(Board board, Position from, Position to);

    public abstract char getSymbol();

    @Override
    public String toString() {
        return Character.toString(getSymbol());
    }
}
