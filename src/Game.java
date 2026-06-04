import java.util.*;

/**
 * Main game controller with undo/redo stacks, replay queue, and game logic
 * Implements check/checkmate detection and move validation
 */
public class Game {
    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    
    // Stack for undo/redo (LIFO)
    private final Stack<Move> undoStack;
    private final Stack<Move> redoStack;
    
    // Queue for replay (FIFO)
    private final LinkedList<Move> moveHistory;
    
    private GameState gameState;
    
    public enum GameState {
        IN_PROGRESS, CHECK, CHECKMATE, STALEMATE
    }
    
    public Game() {
        this.board = new Board();
        this.whitePlayer = new Player(Piece.PieceColor.WHITE);
        this.blackPlayer = new Player(Piece.PieceColor.BLACK);
        this.currentPlayer = whitePlayer;
        
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.moveHistory = new LinkedList<>();
        
        this.gameState = GameState.IN_PROGRESS;
        
        // Setup standard chess position
        board.setupStandardPosition(whitePlayer, blackPlayer);
    }
    
    /**
     * Attempt to make a move - validates and executes if legal
     */
    public boolean makeMove(Position from, Position to) {
        if (!isLegalMove(from, to)) {
            return false;
        }
        
        Piece piece = board.getPiece(from);
        
        // Check for castling
        if (piece instanceof King && Math.abs(from.getFileIndex() - to.getFileIndex()) == 2) {
            return performCastling(from, to);
        }
        
        // Check for en passant
        Piece capturedPiece = board.movePiece(from, to);
        if (piece instanceof Pawn && capturedPiece == null && from.getFileIndex() != to.getFileIndex()) {
            // En passant capture - remove the pawn that was passed
            int capturedRank = from.getRankIndex();
            Position enPassantPos = new Position(to.getFile(), from.getRank());
            capturedPiece = board.removePiece(enPassantPos);
        }
        
        // Remove captured piece from opponent's list
        if (capturedPiece != null) {
            getOpponent().removePiece(capturedPiece);
        }
        
        // Check for pawn promotion
        if (piece instanceof Pawn && ((Pawn)piece).canPromote()) {
            promotePawn(to);
        }
        
        // Record move for undo and history
        Move move = new Move(from, to, piece, capturedPiece);
        undoStack.push(move);
        moveHistory.offer(move); // Add to queue
        redoStack.clear(); // Clear redo stack on new move
        
        // Switch player
        switchPlayer();
        
        // Update game state
        updateGameState();
        
        return true;
    }
    
    /**
     * Perform castling move
     */
    private boolean performCastling(Position kingFrom, Position kingTo) {
        int rookFromFile = kingTo.getFileIndex() > kingFrom.getFileIndex() ? 7 : 0;
        int rookToFile = kingTo.getFileIndex() > kingFrom.getFileIndex() ? 5 : 3;
        int rank = kingFrom.getRankIndex();
        
        Position rookFrom = new Position((char)('a' + rookFromFile), kingFrom.getRank());
        Position rookTo = new Position((char)('a' + rookToFile), kingFrom.getRank());
        
        // Move king
        board.movePiece(kingFrom, kingTo);
        
        // Move rook
        board.movePiece(rookFrom, rookTo);
        
        // Record move
        Piece king = board.getPiece(kingTo);
        Move move = new Move(kingFrom, kingTo, king, null);
        undoStack.push(move);
        moveHistory.offer(move);
        redoStack.clear();
        
        switchPlayer();
        updateGameState();
        
        return true;
    }
    
    /**
     * Promote pawn to queen (can be extended for user choice)
     */
    private void promotePawn(Position position) {
        Piece pawn = board.removePiece(position);
        Piece queen = new Queen(pawn.getColor(), position);
        board.placePiece(queen, position);
        
        // Update player's piece list
        currentPlayer.removePiece(pawn);
        currentPlayer.addPiece(queen);
        
        System.out.println("Pawn promoted to Queen!");
    }
    
    /**
     * Validate if a move is legal (as per project spec section 6.1)
     */
    public boolean isLegalMove(Position from, Position to) {
        Piece piece = board.getPiece(from);
        
        // Check source has player's piece
        if (piece == null || piece.getColor() != currentPlayer.getColor()) {
            return false;
        }
        
        // Check target is in piece's possible moves
        List<Position> possibleMoves = piece.getPossibleMoves(board);
        if (!possibleMoves.contains(to)) {
            return false;
        }
        
        // Simulate move and check if own king is in check
        Board copyBoard = simulateMove(from, to);
        if (isKingInCheck(currentPlayer.getColor(), copyBoard)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Check if a king is in check (as per project spec section 6.2)
     */
    public boolean isKingInCheck(Piece.PieceColor color, Board board) {
        Player player = (color == Piece.PieceColor.WHITE) ? whitePlayer : blackPlayer;
        Player opponent = (color == Piece.PieceColor.WHITE) ? blackPlayer : whitePlayer;
        
        King king = player.getKing();
        if (king == null) return false;
        
        Position kingPos = king.getPosition();
        
        // Check if any enemy piece can move to king's position
        for (Piece enemyPiece : opponent.getPieces()) {
            List<Position> enemyMoves = enemyPiece.getPossibleMoves(board);
            if (enemyMoves.contains(kingPos)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if current player is in checkmate (as per project spec section 6.3)
     */
    public boolean isCheckmate(Piece.PieceColor color) {
        if (!isKingInCheck(color, board)) {
            return false;
        }
        
        Player player = (color == Piece.PieceColor.WHITE) ? whitePlayer : blackPlayer;
        
        // Try all possible moves for all pieces
        for (Piece piece : player.getPieces()) {
            List<Position> possibleMoves = piece.getPossibleMoves(board);
            for (Position move : possibleMoves) {
                if (isLegalMove(piece.getPosition(), move)) {
                    return false; // Found a legal move to escape check
                }
            }
        }
        
        return true; // No legal moves - checkmate
    }
    
    /**
     * Undo the last move using Stack (as per project spec section 6.4)
     */
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        
        Move move = undoStack.pop();
        
        // Move piece back
        board.movePiece(move.getTo(), move.getFrom());
        
        // Restore captured piece if any
        if (move.getCapturedPiece() != null) {
            board.placePiece(move.getCapturedPiece(), move.getTo());
            getOpponent().addPiece(move.getCapturedPiece());
        }
        
        // Push to redo stack
        redoStack.push(move);
        
        // Switch player back
        switchPlayer();
        updateGameState();
        
        return true;
    }
    
    /**
     * Redo a previously undone move using Stack (as per project spec section 6.4)
     */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        
        Move move = redoStack.pop();
        
        // Re-apply the move
        Piece capturedPiece = board.movePiece(move.getFrom(), move.getTo());
        
        if (capturedPiece != null) {
            getOpponent().removePiece(capturedPiece);
        }
        
        undoStack.push(move);
        switchPlayer();
        updateGameState();
        
        return true;
    }
    
    /**
     * Replay the game from start using Queue
     */
    public void replayGame() {
        System.out.println("\n=== GAME REPLAY ===\n");
        
        // Create a new game for replay
        Game replayGame = new Game();
        replayGame.board.display();
        
        int moveNumber = 1;
        for (Move move : moveHistory) {
            System.out.println("Move " + moveNumber + ": " + move);
            replayGame.makeMove(move.getFrom(), move.getTo());
            replayGame.board.display();
            
            // Pause for visualization
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            moveNumber++;
        }
        
        System.out.println("=== REPLAY COMPLETE ===\n");
    }
    
    /**
     * Simulate a move on a copy of the board
     */
    private Board simulateMove(Position from, Position to) {
        Board copyBoard = board.copy();
        copyBoard.movePiece(from, to);
        return copyBoard;
    }
    
    /**
     * Switch to the other player
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }
    
    /**
     * Get the opponent of current player
     */
    private Player getOpponent() {
        return (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }
    
    /**
     * Update game state after each move
     */
    private void updateGameState() {
        if (isCheckmate(currentPlayer.getColor())) {
            gameState = GameState.CHECKMATE;
        } else if (isStalemate(currentPlayer.getColor())) {
            gameState = GameState.STALEMATE;
        } else if (isKingInCheck(currentPlayer.getColor(), board)) {
            gameState = GameState.CHECK;
        } else {
            gameState = GameState.IN_PROGRESS;
        }
    }
    
    /**
     * Check if current player is in stalemate (not in check but no legal moves)
     */
    public boolean isStalemate(Piece.PieceColor color) {
        // Not in check
        if (isKingInCheck(color, board)) {
            return false;
        }
        
        Player player = (color == Piece.PieceColor.WHITE) ? whitePlayer : blackPlayer;
        
        // No legal moves available
        for (Piece piece : player.getPieces()) {
            List<Position> possibleMoves = piece.getPossibleMoves(board);
            for (Position move : possibleMoves) {
                if (isLegalMove(piece.getPosition(), move)) {
                    return false; // Found a legal move
                }
            }
        }
        
        return true; // No legal moves - stalemate
    }
    
    // Getters
    public Board getBoard() { return board; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public GameState getGameState() { return gameState; }
    public LinkedList<Move> getMoveHistory() { return moveHistory; }
    
    /**
     * Display game status
     */
    public void displayStatus() {
        System.out.println("Current Player: " + currentPlayer);
        System.out.println("Game State: " + gameState);
        System.out.println("Moves made: " + moveHistory.size());
    }
}
