package models;

import enums.Color;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        int direction = getColor() == Color.WHITE ? -1 : 1;
        int startingRow = getColor() == Color.WHITE ? 6 : 1;
        int rowDelta = to.row() - from.row();
        int colDelta = to.col() - from.col();
        Piece target = board.getPiece(to);

        if (colDelta == 0 && target == null) {
            return rowDelta == direction || (from.row() == startingRow
                    && rowDelta == 2 * direction
                    && board.getPiece(new Position(from.row() + direction, from.col())) == null);
        }
        return rowDelta == direction && Math.abs(colDelta) == 1
                && target != null && target.getColor() != getColor();
    }

    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? 'P' : 'p';
    }
}
