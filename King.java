import java.util.ArrayList;
import java.util.List;

/**
 * King piece - can move one square in any direction
 */
public class King extends Piece {
    
    public King(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int file = position.getFileIndex();
        int rank = position.getRankIndex();
        
        // King can move one square in any direction (8 possible moves)
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int[] dir : directions) {
            int newFile = file + dir[0];
            int newRank = rank + dir[1];
            
            if (isWithinBoard(newFile, newRank)) {
                Position newPos = createPosition(newFile, newRank);
                Piece targetPiece = board.getPiece(newPos);
                
                // Can move to empty square or capture opponent's piece
                if (targetPiece == null || targetPiece.getColor() != this.color) {
                    moves.add(newPos);
                }
            }
        }
        
        // Castling moves (handled in Game.java with additional validation)
        if (!hasMoved) {
            // Kingside castling (O-O)
            if (canCastle(board, true)) {
                moves.add(createPosition(file + 2, rank));
            }
            // Queenside castling (O-O-O)
            if (canCastle(board, false)) {
                moves.add(createPosition(file - 2, rank));
            }
        }
        
        return moves;
    }
    
    /**
     * Check if castling is possible (basic check, full validation in Game.java)
     */
    private boolean canCastle(Board board, boolean kingside) {
        int file = position.getFileIndex();
        int rank = position.getRankIndex();
        
        if (kingside) {
            // Check if squares between king and rook are empty
            for (int f = file + 1; f < 7; f++) {
                if (board.getPiece(createPosition(f, rank)) != null) {
                    return false;
                }
            }
            // Check if rook is there and hasn't moved
            Piece rook = board.getPiece(createPosition(7, rank));
            return rook instanceof Rook && !rook.hasMoved();
        } else {
            // Queenside
            for (int f = file - 1; f > 0; f--) {
                if (board.getPiece(createPosition(f, rank)) != null) {
                    return false;
                }
            }
            Piece rook = board.getPiece(createPosition(0, rank));
            return rook instanceof Rook && !rook.hasMoved();
        }
    }
    
    @Override
    public String getSymbol() {
        return color == PieceColor.WHITE ? "♔" : "♚";
    }
    
    @Override
    public String getName() {
        return "King";
    }
}
