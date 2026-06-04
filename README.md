# Chess Game Using Data Structures in Java

**University of Central Punjab - Data Structures & Algorithms Project**  
**Student:** Faisal Arslan Khan  
**Roll Number:** L1F24BSSE0297  
**Section:** D2

## 🎨 **PROFESSIONAL GUI VERSION!**

This chess game features a **beautiful Java Swing GUI** with professional graphics!

## ♟️ THIS IS A REAL CHESS GAME!

This implementation follows **official FIDE chess rules** including all special moves and win conditions!

## 🚀 Quick Start

### **GUI Version (Recommended):**
```bash
javac *.java
java ChessBoardGUI
```
**Or double-click:** `run-gui.bat`

### **Console Version:**
```bash
java ChessGame
```
**Or double-click:** `run-console.bat`

## Project Overview

This is a fully functional chess game with **professional GUI** implemented in Java that demonstrates the practical application of various data structures and algorithms learned in the DSA course. **The game follows real-world chess rules exactly as played in tournaments.**

## ✅ Real Chess Features

### Standard Rules ✅
✅ **All pieces move according to official chess rules**  
✅ **Castling** - King and Rook special move (O-O and O-O-O)  
✅ **Pawn Promotion** - Pawns become Queens at the end  
✅ **En Passant** - Special pawn capture move  
✅ **Check Detection** - King under attack  
✅ **Checkmate** - Game ending condition  
✅ **Stalemate** - Draw condition  
✅ **Move Validation** - Cannot move into check  

### Complete Piece Movement
- ♔ **King:** One square any direction + Castling
- ♕ **Queen:** Any distance in any direction  
- ♖ **Rook:** Any distance horizontal/vertical
- ♗ **Bishop:** Any distance diagonally
- ♘ **Knight:** L-shape (2+1), jumps over pieces
- ♙ **Pawn:** Forward 1 (or 2 on first move), captures diagonally, promotes at end

## Features

🎨 **PROFESSIONAL JAVA SWING GUI** - Beautiful graphical interface  
✅ **REAL CHESS RULES** - Follows official FIDE regulations  
✅ **Interactive Click-to-Move** - Intuitive mouse controls  
✅ **Visual Highlights** - Shows selected pieces and possible moves  
✅ **Move History Display** - Real-time move tracking  
✅ **Undo/Redo Buttons** - Easy move reversal  
✅ **Game Status Panel** - Shows turn, moves, and game state  
✅ **Beautiful Color Scheme** - Professional chess board colors  
✅ **Standard Chess Board** - Full 8×8 board with coordinates  
✅ **All Piece Movements** - King, Queen, Rook, Bishop, Knight, Pawn  
✅ **Special Moves:**
   - Castling (Kingside O-O and Queenside O-O-O)
   - Pawn Promotion (Pawn → Queen)
   - En Passant (special pawn capture)  
✅ **Check & Checkmate Detection** - Visual warnings and dialogs  
✅ **Stalemate Detection** - Draw condition with dialog  
✅ **Move Validation** - Cannot move into check, must follow piece rules  

## Data Structures Used

| Data Structure | Purpose | Time Complexity |
|----------------|---------|-----------------|
| **HashMap** | Board position lookup | O(1) average |
| **ArrayList** | Store active pieces | O(1) index access, O(n) iteration |
| **Stack** | Undo/Redo functionality | O(1) push/pop |
| **Queue (LinkedList)** | Move history & replay | O(1) offer/poll |
| **Tree** | Future AI move analysis | O(b^d) with pruning |

## Project Structure

```
├── ChessGame.java      # Main class with presentation layer
├── Game.java           # Game engine with logic layer
├── Board.java          # Board representation using HashMap
├── Player.java         # Player class with ArrayList of pieces
├── Move.java           # Move record for history/undo/redo
├── Position.java       # Position class (algebraic notation)
├── Piece.java          # Abstract base class for pieces
├── King.java           # King piece implementation
├── Queen.java          # Queen piece implementation
├── Rook.java           # Rook piece implementation
├── Bishop.java         # Bishop piece implementation
├── Knight.java         # Knight piece implementation
└── Pawn.java           # Pawn piece implementation
```

## How to Compile and Run

### Compile all Java files:
```bash
javac *.java
```

### Run the game:
```bash
java ChessGame
```

## How to Play

### Commands:
- `move` - Make a move (format: e2 e4)
- `undo` - Undo the last move
- `redo` - Redo an undone move
- `replay` - Replay the entire game
- `help` - Show help information
- `quit` - Exit the game

### Move Format:
Enter moves using algebraic notation:
- Example: `e2 e4` (move piece from e2 to e4)
- Files: a-h (columns)
- Ranks: 1-8 (rows)

## Algorithms Implemented

### Move Validation (Section 6.1)
Confirms source contains player's piece, validates against piece rules, and ensures move doesn't leave king in check.

### Check Detection (Section 6.2)
Iterates opponent's pieces to check if any can capture the king.

### Checkmate Verification (Section 6.3)
Verifies king is in check and no legal move can escape it.

### Undo/Redo (Section 6.4)
Uses two stacks to manage move reversal and reapplication.

## System Architecture

```
┌─────────────────────────┐
│  Presentation Layer     │  <- ChessGame.java
│  (Console Interface)    │
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│  Logic Layer            │  <- Game.java
│  (Game Engine)          │
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│  Data Layer             │  <- Board, Player, Pieces
│  (Data Structures)      │
└─────────────────────────┘
```

## Learning Outcomes

- Practical application of HashMap, ArrayList, Stack, and Queue
- Object-oriented design with inheritance and polymorphism
- Algorithm implementation for game logic
- Problem decomposition and incremental development

## Future Enhancements

- Tree-based AI opponent using minimax algorithm
- Special moves (castling, en passant, pawn promotion)
- Graphical user interface (GUI)
- Save/load game functionality
- Move notation in standard PGN format

## Author

Faisal Arslan Khan  
Department of Software Engineering  
University of Central Punjab
