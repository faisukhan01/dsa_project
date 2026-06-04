import java.util.List;

/**
 * Abstract base class for all chess pieces
 * Each piece type (King, Queen, Rook, Bishop, Knight, Pawn) extends this
 */
public abstract class Piece {
    protected final PieceColor color;
    protected Position position;
    protected boolean hasMoved;
    
    public enum PieceColor {
        WHITE, BLACK
    }
    
    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
        this.hasMoved = false;
    }
    
    public PieceColor getColor() {
        return color;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
        this.hasMoved = true;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    /**
     * Returns all possible moves for this piece based on its movement rules
     * Does not consider check/checkmate
     */
    public abstract List<Position> getPossibleMoves(Board board);
    
    /**
     * Returns the piece symbol for display
     */
    public abstract String getSymbol();
    
    /**
     * Returns the piece type name
     */
    public abstract String getName();
    
    protected boolean isWithinBoard(int file, int rank) {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }
    
    protected Position createPosition(int fileIndex, int rankIndex) {
        return new Position((char)('a' + fileIndex), rankIndex + 1);
    }
}
