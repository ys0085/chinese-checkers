<<<<<<< Updated upstream
package com.tp;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hex extends Polygon {
    public int col, row;
    public Tile tile;
    private boolean selected;
    Hex(int col, int row, double x, double y, double size){
        super();
        this.col = col;
        this.row = row;
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + 30);
            double xOffset = size * Math.cos(angle);
            double yOffset = size * Math.sin(angle);
            this.getPoints().addAll(x + xOffset, y + yOffset);
        }
    }

    public boolean isSelected() { return selected; }
    public void toggleSelect() { selected = !selected; updateSelectVisual(); }
    
    private void updateSelectVisual() {
        if(selected){
            setStroke(Color.BLACK);
            setStrokeWidth(2);
        }
        else{
            setStroke(Color.GRAY);
            setStrokeWidth(1);
        }
        
    }

    
    /** Sets tile color of given hex
     * @param t
     */
    public void setTile(Tile t) {
        tile = t;
        if(t == Tile.INVALID || t == null) { setVisible(false); return; }
        else setVisible(true);

        if(t == Tile.EMPTY) setFill(Color.LIGHTGRAY);
        else setFill(t.toColor().toPaintColor());
        
    }
}
=======
package com.tp;

// Import necessary classes for color and shape handling
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

// Hex class extends Polygon to create a hexagonal shape
public class Hex extends Polygon {
    // Coordinates for the hexagon's column and row
    public int col, row;
    // Tile object associated with this hexagon
    public Tile tile;
    // Indicates whether the hexagon is selected
    private boolean selected;

    // Constructor for the Hex class
    Hex(int col, int row, double x, double y, double size) {
        // Call the superclass (Polygon) constructor
        super();
        // Initialize the column and row
        this.col = col;
        this.row = row;

        // Loop to create the points of the hexagon
        for (int i = 0; i < 6; i++) {
            // Calculate the angle in radians for each vertex
            double angle = Math.toRadians(60 * i + 30);
            // Calculate x and y offsets based on size and angle
            double xOffset = size * Math.cos(angle);
            double yOffset = size * Math.sin(angle);
            // Add the calculated points to the Polygon
            this.getPoints().addAll(x + xOffset, y + yOffset);
        }
        // Uncomment the following line to print hexagon creation debug information
        // System.out.println("Hex at " + col + ", " + row + " created at " + x + ", " +
        // y);
    }

    // Method to check if the hexagon is selected
    public boolean isSelected() {
        return selected;
    }

    // Method to toggle the selection state of the hexagon
    public void toggleSelect() {
        selected = !selected;
        updateSelectVisual();
    }

    // Private method to update the visual representation based on selection state
    private void updateSelectVisual() {
        if (selected) {
            // Set stroke color and width for selected hexagon
            setStroke(Color.BLACK);
            setStrokeWidth(2);
        } else {
            // Set stroke color and width for unselected hexagon
            setStroke(Color.GRAY);
            setStrokeWidth(1);
        }
    }

    /**
     * Sets tile color of the given hex
     * 
     * @param t Tile object to set for this hex
     */
    public void setTile(Tile t) {
        tile = t; // Assign the tile to the hexagon
        // Check if the tile is invalid or null
        if (t == Tile.INVALID || t == null) {
            setVisible(false); // Hide the hexagon if the tile is invalid
            return;
        } else {
            setVisible(true); // Show the hexagon if the tile is valid
        }

        // Set the fill color of the hexagon based on the tile type
        if (t == Tile.EMPTY) {
            setFill(Color.LIGHTGRAY); // Set to light gray if the tile is empty
        } else {
            setFill(t.toColor().toPaintColor()); // Use the tile's color if it's valid
        }
    }
}
>>>>>>> Stashed changes
