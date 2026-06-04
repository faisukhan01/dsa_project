import java.util.ArrayList;
import java.util.List;

/**
 * Knight piece - moves in an L-shape (2 squares in one direction, 1 square perpendicular)
 */
public class Knight extends Piece {
    
    public Knight(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int file = position.getFileIndex();
        int rank = position.getRankIndex();
        
        // Knight has 8 possible L-shaped moves
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        
        for (int[] move : knightMoves) {
            int newFile = file + move[0];
            int newRank = rank + move[1];
            
            if (isWithinBoard(newFile, newRank)) {
                Position newPos = createPosition(newFile, newRank);
                Piece targetPiece = board.getPiece(newPos);
                
                // Can move to empty square or capture opponent's piece
                if (targetPiece == null || targetPiece.getColor() != this.color) {
                    moves.add(newPos);
                }
            }
        }
        
        return moves;
    }
    
    @Override
    public String getSymbol() {
        return color == PieceColor.WHITE ? "♘" : "♞";
    }
    
    @Override
    public String getName() {
        return "Knight";
    }
}
