<<<<<<< Updated upstream
package com.tp;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class BoardPanel extends Region {
    private static final int HEX_SIZE = 20;
    private Hex selectedHex = null;
    private ArrayList<Hex> hexes = new ArrayList<>();
    private Board board;
    
    private static final double X_OFFSET = Math.sqrt(3) * HEX_SIZE; 
    private static final double Y_OFFSET = HEX_SIZE * 1.5;     
    private static final double X_PADDING = 50; 
    private static final double Y_PADDING = 50; 

    private static final int ROWS = 8;    
    private static final int COLS = 8; 

    private Consumer<Move> moveCallback;

    BoardPanel(Consumer<Move> moveCallback) {
        super();
        this.moveCallback = moveCallback;
        this.board = new Board(Client.getInstance().getVariant());
    
        for (int row = -ROWS; row <= ROWS; row++) { // Adjusted for negative coordinates
            for (int col = -COLS; col <= COLS; col++) { // Adjusted for negative coordinates
                double x = (row + ROWS) * X_OFFSET + (col + COLS) * X_OFFSET / 2 + X_PADDING;
                double y = (col + COLS) * Y_OFFSET + Y_PADDING;
    
                Hex hex = new Hex(col, row, x, y, HEX_SIZE);
                hexes.add(hex);
                hex.setFill(Color.LIGHTGRAY);
                hex.setStroke(Color.GRAY);
    
                Label coordinates = new Label(row + "\n" + col);
                coordinates.setLayoutX(x - HEX_SIZE / 2);
                coordinates.setLayoutY(y - HEX_SIZE / 1.5);
                coordinates.setVisible(false);
                
                this.getChildren().add(hex);
                this.getChildren().add(coordinates);
    
                hex.setOnMouseClicked(e -> handleHexClick(hex));
            }
        }
    }

    private void handleHexClick(Hex hex) {
        if (!hex.isVisible()) return;
        if (!(hex.tile.toString().equals(Client.getInstance().getColor().toString())) && selectedHex == null) return;
        if (!Client.getInstance().isYourTurn()) return;
        if (Client.getInstance().getWinningColor() != null) return;

        if (hex.isSelected()) {
            hex.toggleSelect();
            selectedHex = null;
        } else {
            if (selectedHex == null) {
                selectedHex = hex;
                hex.toggleSelect();
            } else {
                parseMove(selectedHex, hex);
                selectedHex.toggleSelect();
                selectedHex = null;
            }
        }
    }

    public void update() {
        for (Hex h : hexes) {
            h.setTile(board.getTile(h.row, h.col));
        }
    }

    public void setBoard(Board b) { this.board = b; }
    public Board getBoard() { return board; }

    public boolean move(Move move) {
        Position from = move.from;
        Position to = move.to;
        boolean success = board.move(new Move(from, to));
        if (success) {
            UIApp.boardPanel.update();
            Client.getInstance().checkWin(board);
        } else {
            System.out.println("Invalid move received - possible desync");
        }
        return success;
    }

    private void parseMove(Hex h1, Hex h2) {
        Position from = new Position(h1.row, h1.col);
        Position to = new Position(h2.row, h2.col);
        boolean success = board.move(new Move(from, to));
        if (success) {
            moveCallback.accept(new Move(from, to));
            Client.getInstance().setYourTurn(false);
            Client.getInstance().checkWin(board);
        }
        UIApp.boardPanel.update();
    }
}
=======
package com.tp;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

// Class representing the board panel which displays the hexagonal game board
public class BoardPanel extends Region {
    // Constants for hexagon size and offsets
    private static final int HEX_SIZE = 20; // Size of each hexagon
    private Hex selectedHex = null; // Currently selected hexagon
    private ArrayList<Hex> hexes = new ArrayList<>(); // List to store all hexagons on the board
    private Board board; // The game board

    // Constants for calculating positions of hexagons in the grid
    private static final double X_OFFSET = Math.sqrt(3) * HEX_SIZE;
    private static final double Y_OFFSET = HEX_SIZE * 1.5;
    private static final double X_PADDING = -150; // Padding along the X-axis to center the grid
    private static final double Y_PADDING = 50; // Padding along the Y-axis to adjust vertical position

    // Constants defining the dimensions of the board
    private static final int ROWS = 10;
    private static final int COLS = 10;

    // Callback function for handling moves in the game
    private Consumer<Move> moveCallback;

    // Constructor that initializes the board panel
    BoardPanel(Consumer<Move> moveCallback) {
        super(); // Call the constructor of the superclass Region
        this.moveCallback = moveCallback; // Assign the moveCallback to the instance variable
        this.board = new Board(Client.getInstance().getVariant()); // Initialize the board based on client variant

        // Loop through rows and columns to create a grid of hexagons
        for (int row = -ROWS; row <= ROWS; row++) { // Adjusted for negative coordinates
            for (int col = -COLS; col <= COLS; col++) { // Adjusted for negative coordinates
                // Calculate the x and y position of the hexagon based on row and column
                double x = (row + ROWS) * X_OFFSET + (col + COLS) * X_OFFSET / 2 + X_PADDING;
                double y = (col + COLS) * Y_OFFSET + Y_PADDING;

                // Create a new hexagon and add it to the hexes list
                Hex hex = new Hex(col, row, x, y, HEX_SIZE);
                hexes.add(hex);
                hex.setFill(Color.LIGHTGRAY); // Set fill color of hexagon
                hex.setStroke(Color.GRAY); // Set border color of hexagon

                // Create a label to show coordinates and distance to another position
                Label coordinates = new Label(
                        row + " " + col + "\n" + board.getDistance(new Position(row, col), new Position(4, 2)));
                coordinates.setLayoutX(x - HEX_SIZE / 2); // Adjust label position
                coordinates.setLayoutY(y - HEX_SIZE / 1.5); // Adjust label position
                coordinates.setVisible(false); // Initially hide the coordinates label

                // Add hexagon and label to the panel
                this.getChildren().add(hex);
                this.getChildren().add(coordinates);

                // Set mouse click event handler for hexagon
                hex.setOnMouseClicked(e -> handleHexClick(hex));
            }
        }
    }

    // Method to handle click events on hexagons
    private void handleHexClick(Hex hex) {
        // Check if hexagon is visible and valid for selection
        if (!hex.isVisible())
            return;
        if (!(hex.tile.toString().equals(Client.getInstance().getColor().toString())) && selectedHex == null)
            return;
        if (!Client.getInstance().isYourTurn())
            return;
        if (Client.getInstance().getWinningColor() != null)
            return;

        // Toggle selection based on the current state of the hexagon
        if (hex.isSelected()) {
            hex.toggleSelect(); // Deselect hexagon
            selectedHex = null; // Clear selection
        } else {
            // If no hexagon is currently selected, select the clicked hexagon
            if (selectedHex == null) {
                selectedHex = hex;
                hex.toggleSelect(); // Select hexagon
            } else {
                // Move from the selected hexagon to the clicked hexagon
                parseMove(selectedHex, hex);
                selectedHex.toggleSelect(); // Deselect the selected hexagon
                selectedHex = null; // Clear selection
            }
        }
    }

    // Method to update the display of the board and hexagons
    public void update() {
        for (Hex h : hexes) {
            // Update each hexagon's tile based on the current board state
            h.setTile(board.getTile(h.row, h.col));
        }
    }

    // Method to set a new board state
    public void setBoard(Board b) {
        System.out.println("Setting board : " + b.toString());
        this.board = b; // Assign the new board
        UIApp.boardPanel.update(); // Update the board display
    }

    // Getter method for the current board
    public Board getBoard() {
        return board;
    }

    // Method to parse and execute a move from one hexagon to another
    private void parseMove(Hex h1, Hex h2) {
        Position from = new Position(h1.row, h1.col); // Position to move from
        Position to = new Position(h2.row, h2.col); // Position to move to
        boolean success = board.move(new Move(from, to)); // Attempt to move on the board
        if (success) {
            moveCallback.accept(new Move(from, to)); // Notify via callback if the move was successful
            Client.getInstance().setYourTurn(false); // Set turn to false for current player
        }
        UIApp.boardPanel.update(); // Update the board display after the move
    }
}
>>>>>>> Stashed changes
