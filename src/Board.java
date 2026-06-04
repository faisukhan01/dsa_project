import java.util.HashMap;

/**
 * Represents the chess board using a HashMap for O(1) position lookups
 */
public class Board {
    private final HashMap<Position, Piece> board;
    
    public Board() {
        this.board = new HashMap<>();
    }
    
    /**
     * Get the piece at a given position (O(1) average time)
     */
    public Piece getPiece(Position position) {
        return board.get(position);
    }
    
    /**
     * Place a piece at a position
     */
    public void placePiece(Piece piece, Position position) {
        board.put(position, piece);
        piece.setPosition(position);
    }
    
    /**
     * Remove a piece from a position
     */
    public Piece removePiece(Position position) {
        return board.remove(position);
    }
    
    /**
     * Move a piece from one position to another
     */
    public Piece movePiece(Position from, Position to) {
        Piece piece = removePiece(from);
        Piece capturedPiece = removePiece(to);
        
        if (piece != null) {
            placePiece(piece, to);
        }
        
        return capturedPiece;
    }
    
    /**
     * Check if a position is empty
     */
    public boolean isEmpty(Position position) {
        return !board.containsKey(position);
    }
    
    /**
     * Create a deep copy of the board for simulation
     */
    public Board copy() {
        Board newBoard = new Board();
        for (Position pos : board.keySet()) {
            Piece piece = board.get(pos);
            newBoard.board.put(pos, piece);
        }
        return newBoard;
    }
    
    /**
     * Initialize the board with standard chess starting position
     */
    public void setupStandardPosition(Player whitePlayer, Player blackPlayer) {
        // White pieces (rank 1 and 2)
        setupPlayerPieces(whitePlayer, Piece.PieceColor.WHITE, 1, 2);
        
        // Black pieces (rank 8 and 7)
        setupPlayerPieces(blackPlayer, Piece.PieceColor.BLACK, 8, 7);
    }
    
    private void setupPlayerPieces(Player player, Piece.PieceColor color, int backRank, int pawnRank) {
        // Back rank pieces
        char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        
        // Rooks
        Piece rook1 = new Rook(color, new Position('a', backRank));
        Piece rook2 = new Rook(color, new Position('h', backRank));
        placePiece(rook1, rook1.getPosition());
        placePiece(rook2, rook2.getPosition());
        player.addPiece(rook1);
        player.addPiece(rook2);
        
        // Knights
        Piece knight1 = new Knight(color, new Position('b', backRank));
        Piece knight2 = new Knight(color, new Position('g', backRank));
        placePiece(knight1, knight1.getPosition());
        placePiece(knight2, knight2.getPosition());
        player.addPiece(knight1);
        player.addPiece(knight2);
        
        // Bishops
        Piece bishop1 = new Bishop(color, new Position('c', backRank));
        Piece bishop2 = new Bishop(color, new Position('f', backRank));
        placePiece(bishop1, bishop1.getPosition());
        placePiece(bishop2, bishop2.getPosition());
        player.addPiece(bishop1);
        player.addPiece(bishop2);
        
        // Queen
        Piece queen = new Queen(color, new Position('d', backRank));
        placePiece(queen, queen.getPosition());
        player.addPiece(queen);
        
        // King
        Piece king = new King(color, new Position('e', backRank));
        placePiece(king, king.getPosition());
        player.addPiece(king);
        
        // Pawns
        for (char file : files) {
            Piece pawn = new Pawn(color, new Position(file, pawnRank));
            placePiece(pawn, pawn.getPosition());
            player.addPiece(pawn);
        }
    }
    
    /**
     * Display the board in console
     */
    public void display() {
        System.out.println("\n  a b c d e f g h");
        System.out.println("  ----------------");
        
        for (int rank = 8; rank >= 1; rank--) {
            System.out.print(rank + "|");
            for (char file = 'a'; file <= 'h'; file++) {
                Position pos = new Position(file, rank);
                Piece piece = getPiece(pos);
                if (piece != null) {
                    System.out.print(piece.getSymbol() + " ");
                } else {
                    System.out.print("· ");
                }
            }
            System.out.println("|" + rank);
        }
        
        System.out.println("  ----------------");
        System.out.println("  a b c d e f g h\n");
    }
}
