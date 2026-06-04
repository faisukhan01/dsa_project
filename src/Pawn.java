import java.util.ArrayList;
import java.util.List;

/**
 * Pawn piece - moves forward, captures diagonally, with special first-move and en passant rules
 */
public class Pawn extends Piece {
    
    public Pawn(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int file = position.getFileIndex();
        int rank = position.getRankIndex();
        
        int direction = (color == PieceColor.WHITE) ? 1 : -1;
        int startRank = (color == PieceColor.WHITE) ? 1 : 6; // Starting rank for pawns
        
        // Move forward one square
        int newRank = rank + direction;
        if (isWithinBoard(file, newRank)) {
            Position forwardPos = createPosition(file, newRank);
            if (board.getPiece(forwardPos) == null) {
                moves.add(forwardPos);
                
                // Move forward two squares on first move (only from starting position)
                if (rank == startRank && !hasMoved) {
                    int doubleRank = rank + 2 * direction;
                    Position doubleForwardPos = createPosition(file, doubleRank);
                    if (board.getPiece(doubleForwardPos) == null) {
                        moves.add(doubleForwardPos);
                    }
                }
            }
        }
        
        // Capture diagonally
        int[][] captureMoves = {{-1, direction}, {1, direction}};
        for (int[] capture : captureMoves) {
            int captureFile = file + capture[0];
            int captureRank = rank + capture[1];
            
            if (isWithinBoard(captureFile, captureRank)) {
                Position capturePos = createPosition(captureFile, captureRank);
                Piece targetPiece = board.getPiece(capturePos);
                
                if (targetPiece != null && targetPiece.getColor() != this.color) {
                    moves.add(capturePos);
                }
            }
        }
        
        // TODO: En passant will be handled in Game.java with move history
        
        return moves;
    }
    
    /**
     * Check if pawn has reached the end and needs promotion
     */
    public boolean canPromote() {
        int promotionRank = (color == PieceColor.WHITE) ? 7 : 0;
        return position.getRankIndex() == promotionRank;
    }
    
    @Override
    public String getSymbol() {
        return color == PieceColor.WHITE ? "♙" : "♟";
    }
    
    @Override
    public String getName() {
        return "Pawn";
    }
}
