package models;

import enums.Color;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        return Math.abs(from.row() - to.row()) == Math.abs(from.col() - to.col())
                && board.isPathClear(from, to);
    }

    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? 'B' : 'b';
    }
}
