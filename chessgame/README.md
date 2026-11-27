# Chess game

A Java 17+ low-level design exercise using `ChessGame`, `Board`, and polymorphic
`Piece` classes. No external libraries or build tool are required.

## Run and test

Run these commands in PowerShell from the `chessgame` directory with a JDK on PATH:

```powershell
$sources = Get-ChildItem -Path src,test -Recurse -Filter *.java | ForEach-Object FullName
javac --release 17 -Xlint:all -d out $sources
java -cp out ChessGameDemo
java -cp out models.BoardTest
java -cp out ChessGameTest
```

The demo plays `1. f3 e5 2. g4 Qh4#` (Fool's Mate), displays each board, and ends
with `Game status: BLACK_WINS` and a four-move history. The test programs throw an
`AssertionError` on failure and print the number of checks passed on success.

## Design

- `ChessGame` owns the players, current turn, status, and move history. It rejects
  moves after the game ends and returns an immutable history snapshot.
- `Board` starts with all 32 pieces. It checks bounds, ownership, blocked paths,
  and king safety, and searches for legal moves to identify checkmate or stalemate.
  Validation temporarily applies a move and always restores the original position.
- `Piece` subclasses implement their movement rules. Pawns move toward decreasing
  row numbers for white and increasing row numbers for black.
- `Position` uses zero-based coordinates: `(0, 0)` is `a8`, `(7, 7)` is `h1`.
  Board symbols are uppercase for white and lowercase for black.
- `Move` records the original moved piece and captured piece, including when a
  pawn promotes. The board automatically replaces a promoted pawn with a queen.

## Supported rules

The exercise supports ordinary movement for all six piece types, captures, turns,
check, king safety, checkmate, stalemate (reported as `DRAW`), and automatic queen
promotion. Tests cover both colors winning, stalemate positions, pinned pieces,
invalid coordinates, blocked moves, captures, and promotion with and without capture.

Castling, en passant, choosing an underpromotion, repetition, the fifty-move rule,
and insufficient-material draws are not implemented. Legal-move searches and game
results use only the supported rules; this is not a complete tournament chess engine.
