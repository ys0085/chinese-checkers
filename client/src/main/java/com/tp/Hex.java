package com.tp;

// Import necessary classes for color and shape handling
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * The {@code Hex} class represents a hexagonal tile in a game board.
 * It extends {@code Polygon} to create a hexagonal shape with a specified
 * column, row, position, and size.
 */
public class Hex extends Polygon {
    
    /**
     * Column index of the hex tile.
     */
    public int col;
    
    /**
     * Row index of the hex tile.
     */
    public int row;
    
    /**
     * Tile object associated with this hexagon.
     */
    public Tile tile;
    
    /**
     * Indicates whether the hexagon is selected.
     */
    private boolean selected;

    /**
     * Constructs a hexagonal tile at a given grid position with a specified size.
     *
     * @param col Column index of the hex tile.
     * @param row Row index of the hex tile.
     * @param x X-coordinate of the hexagon's center.
     * @param y Y-coordinate of the hexagon's center.
     * @param size Radius size of the hexagon.
     */
    Hex(int col, int row, double x, double y, double size) {
        super();
        this.col = col;
        this.row = row;

        // Generate the hexagonal shape by computing its six vertices
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + 30);
            double xOffset = size * Math.cos(angle);
            double yOffset = size * Math.sin(angle);
            this.getPoints().addAll(x + xOffset, y + yOffset);
        }
    }

    /**
     * Checks whether the hex tile is currently selected.
     *
     * @return {@code true} if the hex is selected, {@code false} otherwise.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Toggles the selection state of the hex tile and updates its appearance.
     */
    public void toggleSelect() {
        selected = !selected;
        updateSelectVisual();
    }

    /**
     * Updates the visual representation of the hex based on its selection state.
     */
    private void updateSelectVisual() {
        if (selected) {
            setStroke(Color.BLACK);
            setStrokeWidth(2);
        } else {
            setStroke(Color.GRAY);
            setStrokeWidth(1);
        }
    }

    /**
     * Sets the tile type and updates the hexagon's appearance accordingly.
     *
     * @param t The tile object to assign to this hex.
     */
    public void setTile(Tile t) {
        tile = t;
        if (t == Tile.INVALID || t == null) {
            setVisible(false); // Hide the hexagon if the tile is invalid
            return;
        }
        setVisible(true);

        // Set fill color based on tile type
        if (t == Tile.EMPTY) {
            setFill(Color.LIGHTGRAY);
        } else {
            setFill(t.toColor().toPaintColor());
        }
    }
}
