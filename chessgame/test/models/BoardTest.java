package models;

import enums.Color;

public class BoardTest {
    private static int checks;

    public static void main(String[] args) {
        initialPositionAndBounds();
        pieceMovement();
        kingSafety();
        terminalPositions();
        promotion();
        System.out.println("BoardTest: " + checks + " checks passed");
    }

    private static void initialPositionAndBounds() {
        Board board = new Board();
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getPiece(new Position(row, col)) != null) count++;
            }
        }
        check(count == 32, "Initial board must contain 32 pieces");
        check(board.getPiece(at("e1")) instanceof King, "White king must start on e1");
        check(board.getPiece(at("d8")) instanceof Queen, "Black queen must start on d8");
        check(!board.isInCheck(Color.WHITE) && !board.isInCheck(Color.BLACK), "Neither king starts in check");
        String initial = board.toString();
        for (Color color : Color.values()) {
            int legalMoves = 0;
            for (int from = 0; from < 64; from++) {
                for (int to = 0; to < 64; to++) {
                    if (board.isLegalMove(new Position(from / 8, from % 8),
                            new Position(to / 8, to % 8), color)) legalMoves++;
                }
            }
            check(legalMoves == 20, color + " must have 20 opening moves");
        }
        check(board.toString().equals(initial), "Move search must not alter the board");
        for (Position invalid : new Position[] {
                new Position(-1, 0), new Position(8, 0), new Position(0, -1), new Position(0, 8)}) {
            check(!invalid.isValid(), "Coordinates outside 0..7 must be invalid");
            check(!board.isLegalMove(invalid, at("e4"), Color.WHITE), "Reject invalid source");
            check(!board.isLegalMove(at("e2"), invalid, Color.WHITE), "Reject invalid target");
        }
        check(new Position(0, 0).isValid() && new Position(7, 7).isValid(), "Both corners must be valid");
        check(!board.isLegalMove(null, at("e4"), Color.WHITE), "Reject null source");
        check(!board.isLegalMove(at("e2"), null, Color.WHITE), "Reject null target");
        check(!board.isLegalMove(at("e2"), at("e4"), null), "Reject null color");
        check(!legal(board, "e2", "e2", Color.WHITE), "Reject a move to the same square");
        check(!legal(board, "e3", "e4", Color.WHITE), "Reject an empty source");
        check(!legal(board, "e7", "e5", Color.WHITE), "Reject moving the opponent's piece");
        check(!legal(board, "e1", "d1", Color.WHITE), "Reject capturing a friendly piece");
        check(!board.isPathClear(at("a1"), at("b3")), "Nonlinear paths must be rejected");
        check(!board.isPathClear(at("a1"), at("a1")), "Zero-length paths must be rejected");
        expectIllegal(() -> board.getPiece(new Position(8, 0)), "Invalid read should have a clear error");
        expectIllegal(() -> board.movePiece(at("a1"), at("a4")), "Direct moves must also be validated");
        check(board.toString().equals(initial), "Rejected moves must preserve the board");
    }

    private static void pieceMovement() {
        Board board = new Board();
        check(legal(board, "e2", "e4", Color.WHITE), "White pawn initial double step");
        check(legal(board, "e7", "e5", Color.BLACK), "Black pawn initial double step");
        check(legal(board, "b1", "c3", Color.WHITE), "Knight can jump over the back rank");
        check(!legal(board, "b1", "b3", Color.WHITE), "Knight cannot move straight");
        check(!legal(board, "a1", "a4", Color.WHITE), "Rook cannot jump over a pawn");
        check(!legal(board, "c1", "f4", Color.WHITE), "Bishop cannot jump over a pawn");
        check(!legal(board, "d1", "h5", Color.WHITE), "Queen cannot jump over a pawn");
        board.setPiece(at("e3"), new Knight(Color.BLACK));
        check(!legal(board, "e2", "e4", Color.WHITE), "Pawn cannot jump over a blocker");
        check(!legal(board, "e2", "e3", Color.WHITE), "Pawn cannot capture straight ahead");

        board = emptyBoard();
        board.setPiece(at("e4"), new Pawn(Color.WHITE));
        board.setPiece(at("d5"), new Pawn(Color.BLACK));
        check(legal(board, "e4", "d5", Color.WHITE), "White pawn diagonal capture");
        check(legal(board, "d5", "e4", Color.BLACK), "Black pawn diagonal capture");
        check(!legal(board, "e4", "f5", Color.WHITE), "Pawn cannot move diagonally onto an empty square");
        check(!legal(board, "e4", "e3", Color.WHITE), "Pawn cannot move backward");
        check(!legal(board, "e4", "e6", Color.WHITE), "Pawn double step only from starting rank");

        board = emptyBoard();
        board.setPiece(at("a1"), new Rook(Color.WHITE));
        check(legal(board, "a1", "a8", Color.WHITE), "Rook vertical move");
        check(legal(board, "a1", "d1", Color.WHITE), "Rook horizontal move");
        check(!legal(board, "a1", "b2", Color.WHITE), "Rook cannot move diagonally");
        board.setPiece(at("a4"), new Pawn(Color.BLACK));
        check(!legal(board, "a1", "a8", Color.WHITE), "Rook cannot jump over an enemy");
        check(legal(board, "a1", "a4", Color.WHITE), "Rook can capture the first enemy on its path");

        board = emptyBoard();
        board.setPiece(at("c1"), new Bishop(Color.WHITE));
        check(legal(board, "c1", "h6", Color.WHITE), "Bishop diagonal move");
        check(!legal(board, "c1", "c4", Color.WHITE), "Bishop cannot move straight");
        board.setPiece(at("d4"), new Queen(Color.WHITE));
        check(legal(board, "d4", "d7", Color.WHITE), "Queen straight move");
        check(legal(board, "d4", "g7", Color.WHITE), "Queen diagonal move");
        check(!legal(board, "d4", "e6", Color.WHITE), "Queen cannot move like a knight");
    }

    private static void kingSafety() {
        Board board = emptyBoard();
        board.setPiece(at("e8"), new Rook(Color.BLACK));
        board.setPiece(at("a8"), new King(Color.BLACK));
        Piece shield = new Rook(Color.WHITE);
        Piece attacker = board.getPiece(at("e8"));
        board.setPiece(at("e2"), shield);
        check(!legal(board, "e2", "f2", Color.WHITE), "Pinned rook must not expose its king");
        check(board.getPiece(at("e2")) == shield && board.getPiece(at("f2")) == null,
                "Rejected validation must restore source and target");
        check(legal(board, "e2", "e8", Color.WHITE), "Pinned rook may capture its attacker");
        check(board.getPiece(at("e8")) == attacker, "Validation must restore captured piece identity");
        board.setPiece(at("e2"), null);
        check(board.isInCheck(Color.WHITE), "Open rook file checks the king");
        check(!legal(board, "e1", "e2", Color.WHITE), "King cannot move into an attacked square");
        check(legal(board, "e1", "f1", Color.WHITE), "King can escape a rook check");
        board.setPiece(at("g2"), new Pawn(Color.BLACK));
        check(!legal(board, "e1", "f1", Color.WHITE), "King cannot move into a pawn attack");
        check(!legal(board, "e8", "e1", Color.BLACK), "A king must never be captured");

        board = emptyBoard();
        board.setPiece(at("e8"), null);
        board.setPiece(at("e3"), new King(Color.BLACK));
        check(!legal(board, "e1", "e2", Color.WHITE), "Kings cannot become adjacent");
        check(!legal(board, "e1", "g1", Color.WHITE), "Castling is outside this demo's supported rules");
    }

    private static void terminalPositions() {
        Board board = emptyBoard();
        board.setPiece(at("e1"), null);
        board.setPiece(at("e8"), null);
        board.setPiece(at("c6"), new King(Color.WHITE));
        board.setPiece(at("a8"), new King(Color.BLACK));
        board.setPiece(at("b6"), new Queen(Color.WHITE));
        String before = board.toString();
        check(!board.isInCheck(Color.BLACK), "Stalemated king is not in check");
        check(!board.hasLegalMove(Color.BLACK), "Stalemated side has no legal moves");
        check(board.toString().equals(before), "Stalemate search must preserve the board");
        board.setPiece(at("b6"), null);
        board.setPiece(at("b7"), new Queen(Color.WHITE));
        check(board.isInCheck(Color.BLACK), "Checkmated king is in check");
        check(!board.hasLegalMove(Color.BLACK), "Checkmated side has no legal moves");
        board.setPiece(at("b7"), null);
        board.setPiece(at("h8"), new Rook(Color.WHITE));
        check(board.isInCheck(Color.BLACK) && board.hasLegalMove(Color.BLACK),
                "Check alone must not be treated as checkmate");
    }

    private static void promotion() {
        for (Color color : Color.values()) {
            for (boolean capture : new boolean[] {false, true}) {
                Board board = emptyBoard();
                Position from = at(color == Color.WHITE ? "a7" : "a2");
                Position to = at(color == Color.WHITE ? (capture ? "b8" : "a8") : (capture ? "b1" : "a1"));
                Pawn pawn = new Pawn(color);
                Piece victim = capture ? new Rook(color == Color.WHITE ? Color.BLACK : Color.WHITE) : null;
                board.setPiece(from, pawn);
                board.setPiece(to, victim);
                check(board.isLegalMove(from, to, color), "Promotion must be legal for " + color);
                check(board.getPiece(from) == pawn && board.getPiece(to) == victim,
                        "Promotion validation must not mutate pieces");
                board.movePiece(from, to);
                Piece promoted = board.getPiece(to);
                check(promoted instanceof Queen && promoted.getColor() == color, "Pawn promotes to its own queen");
                check(board.getPiece(from) == null, "Promotion clears the source square");
                check(new Move(from, to, pawn, victim).toString().contains("promoted to queen"),
                        "Promotion is readable in move history");
            }
        }
    }

    private static Board emptyBoard() {
        Board board = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) board.setPiece(new Position(row, col), null);
        }
        board.setPiece(at("e1"), new King(Color.WHITE));
        board.setPiece(at("e8"), new King(Color.BLACK));
        return board;
    }

    private static Position at(String square) {
        return new Position('8' - square.charAt(1), square.charAt(0) - 'a');
    }

    private static boolean legal(Board board, String from, String to, Color color) {
        return board.isLegalMove(at(from), at(to), color);
    }

    private static void expectIllegal(Runnable action, String message) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            checks++;
            return;
        }
        throw new AssertionError(message);
    }

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }
}
