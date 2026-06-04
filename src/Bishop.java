import java.util.ArrayList;
import java.util.List;

/**
 * Bishop piece - can move any number of squares diagonally
 */
public class Bishop extends Piece {
    
    public Bishop(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int file = position.getFileIndex();
        int rank = position.getRankIndex();
        
        // Bishop moves diagonally (4 directions)
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        
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
                    moves.add(newPos);
                }
                break;
            }
            
            file += dFile;
            rank += dRank;
        }
    }
    
    @Override
    public String getSymbol() {
        return color == PieceColor.WHITE ? "♗" : "♝";
    }
    
    @Override
    public String getName() {
        return "Bishop";
    }
}
