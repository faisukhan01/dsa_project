import java.util.Scanner;

/**
 * Main class - presentation layer for the Chess Game
 * Provides console interface for playing chess
 */
public class ChessGame {
    private final Game game;
    private final Scanner scanner;
    private boolean running;
    
    public ChessGame() {
        this.game = new Game();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }
    
    /**
     * Start the game loop
     */
    public void start() {
        printWelcome();
        game.getBoard().display();
        
        while (running && game.getGameState() != Game.GameState.CHECKMATE 
               && game.getGameState() != Game.GameState.STALEMATE) {
            game.displayStatus();
            processCommand();
        }
        
        if (game.getGameState() == Game.GameState.CHECKMATE) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║          CHECKMATE!                    ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("Winner: " + getWinner() + " wins!");
        } else if (game.getGameState() == Game.GameState.STALEMATE) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║          STALEMATE!                    ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("It's a draw!");
        }
        
        scanner.close();
    }
    
    /**
     * Process user commands
     */
    private void processCommand() {
        System.out.print("\nEnter command (move/undo/redo/replay/quit/help): ");
        String input = scanner.nextLine().trim().toLowerCase();
        
        switch (input) {
            case "move":
                handleMove();
                break;
            case "undo":
                handleUndo();
                break;
            case "redo":
                handleRedo();
                break;
            case "replay":
                game.replayGame();
                break;
            case "quit":
            case "exit":
                running = false;
                System.out.println("Thanks for playing!");
                break;
            case "help":
                printHelp();
                break;
            default:
                System.out.println("Unknown command. Type 'help' for available commands.");
        }
    }
    
    /**
     * Handle move command
     */
    private void handleMove() {
        System.out.print("Enter move (e.g., e2 e4): ");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");
        
        if (parts.length != 2) {
            System.out.println("Invalid format. Use: <from> <to> (e.g., e2 e4)");
            return;
        }
        
        try {
            Position from = parsePosition(parts[0]);
            Position to = parsePosition(parts[1]);
            
            if (game.makeMove(from, to)) {
                System.out.println("Move successful!");
                game.getBoard().display();
                
                if (game.getGameState() == Game.GameState.CHECK) {
                    System.out.println("CHECK!");
                }
            } else {
                System.out.println("Illegal move! Try again.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid position: " + e.getMessage());
        }
    }
    
    /**
     * Handle undo command
     */
    private void handleUndo() {
        if (game.undo()) {
            System.out.println("Move undone!");
            game.getBoard().display();
        } else {
            System.out.println("Nothing to undo.");
        }
    }
    
    /**
     * Handle redo command
     */
    private void handleRedo() {
        if (game.redo()) {
            System.out.println("Move redone!");
            game.getBoard().display();
        } else {
            System.out.println("Nothing to redo.");
        }
    }
    
    /**
     * Parse position from string (e.g., "e2")
     */
    private Position parsePosition(String pos) {
        if (pos.length() != 2) {
            throw new IllegalArgumentException("Position must be 2 characters (e.g., e2)");
        }
        
        char file = pos.charAt(0);
        int rank = Character.getNumericValue(pos.charAt(1));
        
        return new Position(file, rank);
    }
    
    /**
     * Get the winner
     */
    private String getWinner() {
        return (game.getCurrentPlayer().getColor() == Piece.PieceColor.WHITE) 
            ? "Black" : "White";
    }
    
    /**
     * Print welcome message
     */
    private void printWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  CHESS GAME USING DATA STRUCTURES     ║");
        System.out.println("║  University of Central Punjab          ║");
        System.out.println("║  By: Faisal Arslan Khan                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nWelcome to Chess!");
        System.out.println("Type 'help' for available commands.\n");
    }
    
    /**
     * Print help information
     */
    private void printHelp() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║           CHESS GAME - HELP & RULES                    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.println("\n=== COMMANDS ===");
        System.out.println("  move   - Make a move (format: e2 e4)");
        System.out.println("  undo   - Undo last move");
        System.out.println("  redo   - Redo undone move");
        System.out.println("  replay - Replay entire game");
        System.out.println("  quit   - Exit the game");
        System.out.println("  help   - Show this help");
        
        System.out.println("\n=== PIECE SYMBOLS ===");
        System.out.println("  White: ♔ King  ♕ Queen  ♖ Rook  ♗ Bishop  ♘ Knight  ♙ Pawn");
        System.out.println("  Black: ♚ King  ♛ Queen  ♜ Rook  ♝ Bishop  ♞ Knight  ♟ Pawn");
        
        System.out.println("\n=== HOW PIECES MOVE (Real Chess Rules) ===");
        System.out.println("  ♔ King:   One square in any direction");
        System.out.println("  ♕ Queen:  Any number of squares in any direction");
        System.out.println("  ♖ Rook:   Any number of squares horizontally or vertically");
        System.out.println("  ♗ Bishop: Any number of squares diagonally");
        System.out.println("  ♘ Knight: L-shape (2+1 squares), can jump over pieces");
        System.out.println("  ♙ Pawn:   Forward 1 square (2 on first move), captures diagonally");
        
        System.out.println("\n=== SPECIAL MOVES ===");
        System.out.println("  Castling:       King moves 2 squares toward rook (if neither moved)");
        System.out.println("                  Kingside: e1 g1 (white) or e8 g8 (black)");
        System.out.println("                  Queenside: e1 c1 (white) or e8 c8 (black)");
        System.out.println("  Pawn Promotion: Pawn reaching opposite end becomes Queen");
        System.out.println("  En Passant:     Special pawn capture (implemented)");
        
        System.out.println("\n=== WIN/DRAW CONDITIONS ===");
        System.out.println("  Checkmate:  King is in check and cannot escape (YOU WIN!)");
        System.out.println("  Stalemate:  No legal moves but not in check (DRAW)");
        System.out.println("  Check:      King is under attack (must move out)");
        
        System.out.println("\n=== MOVE EXAMPLES ===");
        System.out.println("  e2 e4   - Move pawn from e2 to e4 (King's pawn opening)");
        System.out.println("  g1 f3   - Move knight from g1 to f3");
        System.out.println("  e1 g1   - Castle kingside (if allowed)");
        
        System.out.println("\n=== DATA STRUCTURES (Computer Science) ===");
        System.out.println("  • HashMap  - Board position lookup O(1)");
        System.out.println("  • ArrayList - Active pieces tracking");
        System.out.println("  • Stack    - Undo/Redo (LIFO)");
        System.out.println("  • Queue    - Move history & replay (FIFO)");
        System.out.println("════════════════════════════════════════════════════════\n");
    }
    
    /**
     * Main method to start the game
     */
    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame();
        chessGame.start();
    }
}
