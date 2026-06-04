/**
 * Records a chess move including source, destination, and captured piece
 * Used for move history, undo/redo, and replay
 */
public class Move {
    private final Position from;
    private final Position to;
    private final Piece capturedPiece;
    private final Piece movedPiece;
    
    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
    }
    
    public Position getFrom() {
        return from;
    }
    
    public Position getTo() {
        return to;
    }
    
    public Piece getCapturedPiece() {
        return capturedPiece;
    }
    
    public Piece getMovedPiece() {
        return movedPiece;
    }
    
    @Override
    public String toString() {
        String result = movedPiece.getSymbol() + " " + from + " → " + to;
        if (capturedPiece != null) {
            result += " (captures " + capturedPiece.getSymbol() + ")";
        }
        return result;
    }
}
