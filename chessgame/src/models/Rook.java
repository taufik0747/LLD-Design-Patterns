package models;

import enums.Color;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board,
                    Position from,
                    Position to) {

        boolean validDirection =
                from.row() == to.row()
                        || from.col() == to.col();

        return validDirection
                && board.isPathClear(from, to);
    }

    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? 'R' : 'r';
    }
}
