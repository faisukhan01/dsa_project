import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.Timer;

/**
 * Professional Chess Board GUI using Java Swing
 * Beautiful visual chess board with drag-and-drop functionality
 */
public class ChessBoardGUI extends JFrame {
    private final Game game;
    private final JPanel boardPanel;
    private final SquarePanel[][] squares;
    private JLabel statusLabel;
    private JLabel turnLabel;
    private JLabel moveCountLabel;
    private JLabel notificationLabel;
    
    private Position selectedPosition;
    private List<Position> highlightedMoves;
    
    // Professional color scheme
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color SELECTED_COLOR = new Color(186, 202, 68);
    private static final Color HIGHLIGHT_COLOR = new Color(246, 246, 130, 180);
    private static final Color CHECK_COLOR = new Color(255, 100, 100);
    private static final Color BACKGROUND = new Color(49, 46, 43);
    
    public ChessBoardGUI() {
        this.game = new Game();
        this.squares = new SquarePanel[8][8];
        this.selectedPosition = null;
        this.highlightedMoves = null;
        
        // Frame setup
        setTitle("Chess Game - University of Central Punjab");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND);
        
        // Center panel with board and title overlay
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND);
        
        // Top panel with title (will be on the board)
        JPanel topPanel = createTopPanel();
        centerPanel.add(topPanel, BorderLayout.NORTH);
        
        // Board panel
        boardPanel = createBoardPanel();
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Right panel with info
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.EAST);
        
        // Bottom panel with controls
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // Pack and center
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        
        updateBoard();
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        // Main title - Times New Roman for classic elegance
        JLabel titleLabel = new JLabel("Chess with Faisal!");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titleLabel.setForeground(LIGHT_SQUARE);
        
        // Subtitle with project info
        JLabel subtitleLabel = new JLabel("A Data Structures & Algorithms Project");
        subtitleLabel.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        subtitleLabel.setForeground(new Color(180, 180, 180));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitleLabel);
        
        return panel;
    }
    
    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 8, 0, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createLineBorder(new Color(101, 67, 33), 3)
        ));
        panel.setPreferredSize(new Dimension(640, 640));
        
        // Create squares
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                Position pos = new Position((char)('a' + file), rank + 1);
                boolean isLight = (rank + file) % 2 == 0;
                
                SquarePanel square = new SquarePanel(pos, isLight);
                squares[file][rank] = square;
                panel.add(square);
            }
        }
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
        panel.setPreferredSize(new Dimension(300, 640));
        
        // Add vertical spacing to position cards lower
        panel.add(Box.createVerticalStrut(140));
        
        // Turn info panel
        JPanel turnPanel = new JPanel();
        turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.Y_AXIS));
        turnPanel.setBackground(new Color(45, 42, 40));
        turnPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        turnPanel.setPreferredSize(new Dimension(280, 130));
        turnPanel.setMaximumSize(new Dimension(280, 130));
        
        turnLabel = new JLabel("Current Turn: White");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        moveCountLabel = new JLabel("Moves: 0");
        moveCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        moveCountLabel.setForeground(new Color(200, 200, 200));
        moveCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        statusLabel = new JLabel("Game in Progress");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(new Color(186, 202, 68));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        turnPanel.add(turnLabel);
        turnPanel.add(Box.createVerticalStrut(12));
        turnPanel.add(moveCountLabel);
        turnPanel.add(Box.createVerticalStrut(12));
        turnPanel.add(statusLabel);
        
        // Notification label - centered, small, no background!
        notificationLabel = new JLabel("", SwingConstants.CENTER);
        notificationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        notificationLabel.setForeground(Color.WHITE);
        notificationLabel.setPreferredSize(new Dimension(280, 140));
        notificationLabel.setMaximumSize(new Dimension(280, 140));
        notificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        notificationLabel.setVerticalAlignment(SwingConstants.TOP);
        
        // Add to main panel
        panel.add(turnPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(notificationLabel);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        JButton undoButton = createStyledButton("⟲ Undo");
        JButton redoButton = createStyledButton("⟳ Redo");
        JButton newGameButton = createStyledButton("⟳ New Game");
        JButton replayButton = createStyledButton("▶ Replay");
        
        undoButton.addActionListener(e -> undoMove());
        redoButton.addActionListener(e -> redoMove());
        newGameButton.addActionListener(e -> newGame());
        replayButton.addActionListener(e -> replayGame());
        
        panel.add(undoButton);
        panel.add(redoButton);
        panel.add(newGameButton);
        panel.add(replayButton);
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(181, 136, 99));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(101, 67, 33), 2),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(201, 156, 119));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(181, 136, 99));
            }
        });
        
        return button;
    }
    
    private void updateBoard() {
        Board board = game.getBoard();
        
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position pos = new Position((char)('a' + file), rank + 1);
                SquarePanel square = squares[file][rank];
                Piece piece = board.getPiece(pos);
                
                square.setPiece(piece);
                square.setSelected(pos.equals(selectedPosition));
                square.setHighlighted(highlightedMoves != null && highlightedMoves.contains(pos));
                square.setInCheck(false);
                
                // Highlight king if in check
                if (piece instanceof King && 
                    game.isKingInCheck(piece.getColor(), board)) {
                    square.setInCheck(true);
                }
            }
        }
        
        // Update status
        updateStatus();
    }
    
    private void updateStatus() {
        String playerName = game.getCurrentPlayer().getColor() == Piece.PieceColor.WHITE ? "White" : "Black";
        turnLabel.setText("Current Turn: " + playerName);
        moveCountLabel.setText("Moves: " + game.getMoveHistory().size());
        
        switch (game.getGameState()) {
            case CHECK:
                statusLabel.setText("CHECK!");
                statusLabel.setForeground(CHECK_COLOR);
                break;
            case CHECKMATE:
                String winner = game.getCurrentPlayer().getColor() == Piece.PieceColor.WHITE ? "Black" : "White";
                statusLabel.setText("CHECKMATE! " + winner + " wins!");
                statusLabel.setForeground(new Color(255, 215, 0));
                showGameOverDialog(winner + " wins by Checkmate!");
                break;
            case STALEMATE:
                statusLabel.setText("STALEMATE - Draw!");
                statusLabel.setForeground(Color.YELLOW);
                showGameOverDialog("Game ended in Stalemate - It's a draw!");
                break;
            default:
                statusLabel.setText("Game in Progress");
                statusLabel.setForeground(new Color(186, 202, 68));
        }
    }
    
    private void handleSquareClick(Position position) {
        if (game.getGameState() == Game.GameState.CHECKMATE || 
            game.getGameState() == Game.GameState.STALEMATE) {
            return;
        }
        
        if (selectedPosition == null) {
            // Select piece
            Piece piece = game.getBoard().getPiece(position);
            if (piece != null && piece.getColor() == game.getCurrentPlayer().getColor()) {
                selectedPosition = position;
                highlightedMoves = piece.getPossibleMoves(game.getBoard());
                updateBoard();
            }
        } else {
            // Check if this will be a capture
            Piece targetPiece = game.getBoard().getPiece(position);
            boolean isCapture = targetPiece != null && targetPiece.getColor() != game.getCurrentPlayer().getColor();
            
            // Try to move
            if (game.makeMove(selectedPosition, position)) {
                selectedPosition = null;
                highlightedMoves = null;
                updateBoard();
                
                // Show exciting capture message!
                if (isCapture) {
                    showCaptureAnimation(targetPiece);
                }
            } else {
                // Check if clicking another own piece
                Piece piece = game.getBoard().getPiece(position);
                if (piece != null && piece.getColor() == game.getCurrentPlayer().getColor()) {
                    selectedPosition = position;
                    highlightedMoves = piece.getPossibleMoves(game.getBoard());
                    updateBoard();
                } else {
                    // Invalid move
                    JOptionPane.showMessageDialog(this, 
                        "Illegal move! Please try again.", 
                        "Invalid Move", 
                        JOptionPane.WARNING_MESSAGE);
                    selectedPosition = null;
                    highlightedMoves = null;
                    updateBoard();
                }
            }
        }
    }
    
    /**
     * Show exciting capture animation - small, centered, no background!
     */
    private void showCaptureAnimation(Piece capturedPiece) {
        String color = capturedPiece.getColor() == Piece.PieceColor.WHITE ? "White" : "Black";
        String capturingColor = capturedPiece.getColor() == Piece.PieceColor.WHITE ? "Black" : "White";
        
        // Create small exciting message
        String message = "";
        Color textColor;
        
        if (capturedPiece instanceof Queen) {
            message = "<html><div style='text-align: center; padding-top: 10px;'>" +
                     "<div style='font-size: 22px; margin: 2px 0;'>🎉 💥 🎉</div>" +
                     "<div style='font-size: 17px; font-weight: bold; margin: 4px 0;'>" + capturingColor.toUpperCase() + "</div>" +
                     "<div style='font-size: 14px; margin: 2px 0;'>CAPTURES</div>" +
                     "<div style='font-size: 19px; font-weight: bold; margin: 4px 0;'>QUEEN 👑</div>" +
                     "</div></html>";
            textColor = new Color(255, 215, 0);
        } else if (capturedPiece instanceof Rook) {
            message = "<html><div style='text-align: center; padding-top: 10px;'>" +
                     "<div style='font-size: 22px; margin: 2px 0;'>⚡ 💫 ⚡</div>" +
                     "<div style='font-size: 17px; font-weight: bold; margin: 4px 0;'>" + capturingColor.toUpperCase() + "</div>" +
                     "<div style='font-size: 14px; margin: 2px 0;'>CAPTURES</div>" +
                     "<div style='font-size: 19px; font-weight: bold; margin: 4px 0;'>ROOK 🏰</div>" +
                     "</div></html>";
            textColor = new Color(100, 200, 255);
        } else if (capturedPiece instanceof Bishop) {
            message = "<html><div style='text-align: center; padding-top: 10px;'>" +
                     "<div style='font-size: 22px; margin: 2px 0;'>🔥 💥 🔥</div>" +
                     "<div style='font-size: 17px; font-weight: bold; margin: 4px 0;'>" + capturingColor.toUpperCase() + "</div>" +
                     "<div style='font-size: 14px; margin: 2px 0;'>CAPTURES</div>" +
                     "<div style='font-size: 19px; font-weight: bold; margin: 4px 0;'>BISHOP ⛪</div>" +
                     "</div></html>";
            textColor = new Color(255, 100, 100);
        } else if (capturedPiece instanceof Knight) {
            message = "<html><div style='text-align: center; padding-top: 10px;'>" +
                     "<div style='font-size: 22px; margin: 2px 0;'>💫 ⭐ 💫</div>" +
                     "<div style='font-size: 17px; font-weight: bold; margin: 4px 0;'>" + capturingColor.toUpperCase() + "</div>" +
                     "<div style='font-size: 14px; margin: 2px 0;'>CAPTURES</div>" +
                     "<div style='font-size: 19px; font-weight: bold; margin: 4px 0;'>KNIGHT 🐴</div>" +
                     "</div></html>";
            textColor = new Color(200, 100, 255);
        } else {
            message = "<html><div style='text-align: center; padding-top: 10px;'>" +
                     "<div style='font-size: 20px; margin: 2px 0;'>✨ 🎊 ✨</div>" +
                     "<div style='font-size: 17px; font-weight: bold; margin: 4px 0;'>" + capturingColor.toUpperCase() + "</div>" +
                     "<div style='font-size: 14px; margin: 2px 0;'>CAPTURES</div>" +
                     "<div style='font-size: 17px; font-weight: bold; margin: 4px 0;'>PAWN ♟</div>" +
                     "</div></html>";
            textColor = new Color(150, 255, 150);
        }
        
        // Show the text - no background!
        notificationLabel.setText(message);
        notificationLabel.setForeground(textColor);
        
        // Pulsing color animation
        Timer pulseTimer = new Timer(200, null);
        final int[] pulseCount = {0};
        final Color originalColor = textColor;
        
        pulseTimer.addActionListener(e -> {
            if (pulseCount[0] < 12) {
                if (pulseCount[0] % 2 == 0) {
                    notificationLabel.setForeground(originalColor.brighter());
                } else {
                    notificationLabel.setForeground(originalColor);
                }
                pulseCount[0]++;
            } else {
                notificationLabel.setForeground(originalColor);
                ((Timer)e.getSource()).stop();
            }
        });
        pulseTimer.start();
        
        // Clear after 4 seconds
        Timer clearTimer = new Timer(4000, e -> {
            notificationLabel.setText("");
        });
        clearTimer.setRepeats(false);
        clearTimer.start();
    }
    
    private void undoMove() {
        if (game.undo()) {
            selectedPosition = null;
            highlightedMoves = null;
            updateBoard();
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to undo!", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void redoMove() {
        if (game.redo()) {
            selectedPosition = null;
            highlightedMoves = null;
            updateBoard();
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to redo!", "Redo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void newGame() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Start a new game? Current game will be lost.", 
            "New Game", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            dispose();
            new ChessBoardGUI().setVisible(true);
        }
    }
    
    private void replayGame() {
        // TODO: Implement replay in separate window
        JOptionPane.showMessageDialog(this, 
            "Replay feature coming soon!", 
            "Replay", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showGameOverDialog(String message) {
        SwingUtilities.invokeLater(() -> {
            int result = JOptionPane.showConfirmDialog(this,
                message + "\n\nWould you like to start a new game?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                newGame();
            }
        });
    }
    
    /**
     * Inner class representing a square on the chess board
     */
    private class SquarePanel extends JPanel {
        private final Position position;
        private final Color baseColor;
        private Piece piece;
        private boolean selected;
        private boolean highlighted;
        private boolean inCheck;
        
        public SquarePanel(Position position, boolean isLight) {
            this.position = position;
            this.baseColor = isLight ? LIGHT_SQUARE : DARK_SQUARE;
            this.selected = false;
            this.highlighted = false;
            this.inCheck = false;
            
            setPreferredSize(new Dimension(80, 80));
            setBackground(baseColor);
            
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    handleSquareClick(position);
                }
                
                public void mouseEntered(MouseEvent e) {
                    if (!selected && !highlighted) {
                        setBackground(baseColor.brighter());
                    }
                }
                
                public void mouseExited(MouseEvent e) {
                    updateColor();
                }
            });
        }
        
        public void setPiece(Piece piece) {
            this.piece = piece;
            repaint();
        }
        
        public void setSelected(boolean selected) {
            this.selected = selected;
            updateColor();
        }
        
        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
            updateColor();
        }
        
        public void setInCheck(boolean inCheck) {
            this.inCheck = inCheck;
            updateColor();
        }
        
        private void updateColor() {
            if (inCheck) {
                setBackground(CHECK_COLOR);
            } else if (selected) {
                setBackground(SELECTED_COLOR);
            } else if (highlighted) {
                setBackground(HIGHLIGHT_COLOR);
            } else {
                setBackground(baseColor);
            }
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Draw coordinates
            g2d.setFont(new Font("SansSerif", Font.BOLD, 10));
            if (position.getRank() == 1) {
                g2d.setColor(baseColor == LIGHT_SQUARE ? DARK_SQUARE : LIGHT_SQUARE);
                g2d.drawString(String.valueOf(position.getFile()), 3, getHeight() - 3);
            }
            if (position.getFile() == 'a') {
                g2d.setColor(baseColor == LIGHT_SQUARE ? DARK_SQUARE : LIGHT_SQUARE);
                g2d.drawString(String.valueOf(position.getRank()), getWidth() - 10, 12);
            }
            
            // Draw piece
            if (piece != null) {
                g2d.setFont(new Font("Serif", Font.PLAIN, 60));
                FontMetrics fm = g2d.getFontMetrics();
                String symbol = piece.getSymbol();
                int x = (getWidth() - fm.stringWidth(symbol)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                
                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.drawString(symbol, x + 2, y + 2);
                
                // Piece
                g2d.setColor(Color.BLACK);
                g2d.drawString(symbol, x, y);
            }
            
            // Draw highlight circle for possible moves
            if (highlighted && piece == null) {
                g2d.setColor(new Color(100, 100, 100, 100));
                int size = 20;
                g2d.fillOval((getWidth() - size) / 2, (getHeight() - size) / 2, size, size);
            } else if (highlighted && piece != null) {
                g2d.setColor(new Color(255, 0, 0, 80));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(5, 5, getWidth() - 10, getHeight() - 10);
            }
        }
    }
    
    /**
     * Main method to launch the Chess GUI
     */
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ChessBoardGUI gui = new ChessBoardGUI();
            gui.setVisible(true);
        });
    }
}
