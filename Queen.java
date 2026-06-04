import java.util.ArrayList;
import java.util.List;

/**
 * Queen piece - can move any number of squares horizontally, vertically, or diagonally
 */
public class Queen extends Piece {
    
    public Queen(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int file = position.getFileIndex();
        int rank = position.getRankIndex();
        
        // Queen moves like rook + bishop (8 directions)
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Vertical and horizontal
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonals
        };
        
        for (int[] dir : directions) {
            addMovesInDirection(board, moves, file, rank, dir[0], dir[1]);
        }
        
        return moves;
    }
    
    private void addMovesInDirection(Board board, List<Position> moves, int startFile, int startRank, int dFile, int dRank) {
        int file = startFile + dFile;
        int rank = startRank + dRank;
        
        while (isWithinBoard(file, rank)) {
            Position newPos = createPosition(file, rank);
            Piece targetPiece = board.getPiece(newPos);
            
            if (targetPiece == null) {
                moves.add(newPos);
            } else {
                if (targetPiece.getColor() != this.color) {
                    moves.add(newPos); // Can capture
                }
                break; // Cannot move through pieces
            }
            
            file += dFile;
            rank += dRank;
        }
    }
    
    @Override
    public String getSymbol() {
        return color == PieceColor.WHITE ? "♕" : "♛";
    }
    
    @Override
    public String getName() {
        return "Queen";
    }
}
