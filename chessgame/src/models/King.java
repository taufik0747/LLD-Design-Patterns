package models;

import enums.Color;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        return Math.max(Math.abs(from.row() - to.row()), Math.abs(from.col() - to.col())) == 1;
    }

    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? 'K' : 'k';
    }
}
