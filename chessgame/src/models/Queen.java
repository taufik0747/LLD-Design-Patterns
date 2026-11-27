package models;

import enums.Color;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        boolean straight = from.row() == to.row() || from.col() == to.col();
        boolean diagonal = Math.abs(from.row() - to.row()) == Math.abs(from.col() - to.col());
        return (straight || diagonal) && board.isPathClear(from, to);
    }

    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? 'Q' : 'q';
    }
}
