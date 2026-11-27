package models;

public record Move(Position from, Position to, Piece movedPiece, Piece capturedPiece) {
    @Override
    public String toString() {
        return movedPiece.getColor() + " " + movedPiece + " " + from + " -> " + to
                + (capturedPiece != null ? " capturing " + capturedPiece : "")
                + (movedPiece instanceof Pawn && (to.row() == 0 || to.row() == 7)
                        ? " (promoted to queen)" : "");
    }
}
