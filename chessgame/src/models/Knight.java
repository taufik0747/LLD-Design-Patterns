package models;

import enums.Color;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        int delRow = Math.abs(from.row() - to.row());
        int delCol = Math.abs(from.col() - to.col());
        return (delRow == 2 && delCol == 1) || (delRow == 1 && delCol == 2);
    }

    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? 'N' : 'n';
    }
}
