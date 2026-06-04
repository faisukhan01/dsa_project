import java.util.Objects;

/**
 * Represents a square on the chess board using algebraic notation (file a-h, rank 1-8)
 */
public class Position {
    private final char file; // a-h
    private final int rank;  // 1-8
    
    public Position(char file, int rank) {
        if (file < 'a' || file > 'h') {
            throw new IllegalArgumentException("File must be between a and h");
        }
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank must be between 1 and 8");
        }
        this.file = file;
        this.rank = rank;
    }
    
    public char getFile() {
        return file;
    }
    
    public int getRank() {
        return rank;
    }
    
    public int getFileIndex() {
        return file - 'a';
    }
    
    public int getRankIndex() {
        return rank - 1;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
    
    @Override
    public String toString() {
        return "" + file + rank;
    }
}
