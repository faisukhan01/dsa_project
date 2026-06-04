import java.util.ArrayList;

/**
 * Represents a player with their color and active pieces (using ArrayList)
 */
public class Player {
    private final Piece.PieceColor color;
    private final ArrayList<Piece> pieces;
    
    public Player(Piece.PieceColor color) {
        this.color = color;
        this.pieces = new ArrayList<>();
    }
    
    public Piece.PieceColor getColor() {
        return color;
    }
    
    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }
    
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }
    
    public King getKing() {
        for (Piece piece : pieces) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return color == Piece.PieceColor.WHITE ? "White" : "Black";
    }
}
