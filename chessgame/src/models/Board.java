package models;

import enums.Color;

public class Board {
    private final Piece[][] pieces = new Piece[8][8];

    public Board() {
        setBackRank(0, Color.BLACK);
        setBackRank(7, Color.WHITE);
        for (int col = 0; col < 8; col++) {
            pieces[1][col] = new Pawn(Color.BLACK);
            pieces[6][col] = new Pawn(Color.WHITE);
        }
    }

    private void setBackRank(int row, Color color) {
        pieces[row] = new Piece[] {
                new Rook(color), new Knight(color), new Bishop(color), new Queen(color),
                new King(color), new Bishop(color), new Knight(color), new Rook(color)
        };
    }

    public Piece getPiece(Position position) {
        requireValidPosition(position);
        return pieces[position.row()][position.col()];
    }

    void setPiece(Position position, Piece piece) {
        requireValidPosition(position);
        pieces[position.row()][position.col()] = piece;
    }

    private void requireValidPosition(Position position) {
        if (position == null || !position.isValid()) {
            throw new IllegalArgumentException("Position must be inside the 8x8 board");
        }
    }

    public boolean isLegalMove(Position from, Position to, Color color) {
        if (color == null || from == null || to == null
                || !from.isValid() || !to.isValid()
                || from.equals(to)) {
            return false;
        }
        Piece moving = getPiece(from);
        Piece target = getPiece(to);

        if (moving == null || moving.getColor() != color) {
            return false;
        }

        if (target != null && (target.getColor() == color || target instanceof King)) {
            return false;
        }

        if (!moving.canMove(this, from, to)) {
            return false;
        }

        setPiece(from, null);
        setPiece(to, moving);

        try {
            return !isInCheck(color);
        } finally {
            setPiece(from, moving);
            setPiece(to, target);
        }
    }

    public void movePiece(Position from, Position to) {
        requireValidPosition(from);
        requireValidPosition(to);
        Piece moving = getPiece(from);
        if (moving == null || !isLegalMove(from, to, moving.getColor())) {
            throw new IllegalArgumentException("Illegal move: " + from + " -> " + to);
        }
        setPiece(from, null);
        // This demo automatically promotes pawns to queens.
        setPiece(to, moving instanceof Pawn && (to.row() == 0 || to.row() == 7)
                ? new Queen(moving.getColor()) : moving);
    }

    public boolean isPathClear(Position from, Position to) {
        if (from == null || to == null || !from.isValid() || !to.isValid() || from.equals(to)) {
            return false;
        }
        int rowDelta = to.row() - from.row();
        int colDelta = to.col() - from.col();
        if (rowDelta != 0 && colDelta != 0 && Math.abs(rowDelta) != Math.abs(colDelta)) {
            return false;
        }
        int rowStep = Integer.signum(rowDelta);
        int colStep = Integer.signum(colDelta);
        int row = from.row() + rowStep;
        int col = from.col() + colStep;
        while (row != to.row() || col != to.col()) {
            if (pieces[row][col] != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }

    public boolean isInCheck(Color color) {
        Position king = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = pieces[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    king = new Position(row, col);
                }
            }
        }
        if (king == null) {
            throw new IllegalStateException("Missing king for " + color);
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece.getColor() != color
                        && piece.canMove(this, new Position(row, col), king)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasLegalMove(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = pieces[row][col];
                if (piece == null || piece.getColor() != color) {
                    continue;
                }
                Position from = new Position(row, col);
                for (int endRow = 0; endRow < 8; endRow++) {
                    for (int endCol = 0; endCol < 8; endCol++) {
                        if (isLegalMove(from, new Position(endRow, endCol), color)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("  a b c d e f g h\n");
        for (int row = 0; row < 8; row++) {
            result.append(8 - row).append(' ');
            for (int col = 0; col < 8; col++) {
                result.append(pieces[row][col] == null ? '.' : pieces[row][col].getSymbol()).append(' ');
            }
            result.append(8 - row).append('\n');
        }
        return result.append("  a b c d e f g h").toString();
    }
}
