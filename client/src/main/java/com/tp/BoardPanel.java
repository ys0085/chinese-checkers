package com.tp;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class BoardPanel extends Region {
    private int HEX_SIZE = 20;

    BoardPanel() {
        int rows = 13; // Number of rows
        int cols = 13; // Number of columns

        double xOffset = Math.sqrt(3) * HEX_SIZE; // Horizontal offset between columns
        double yOffset = HEX_SIZE * 1.5;             // Vertical offset between rows

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                // Check bounds for Chinese Checkers triangular pattern
                
                double x = col * xOffset + (row % 2 == 0 ? 0 : HEX_SIZE * Math.sqrt(3) / 2); 
                double y = row * yOffset;

                Polygon hex = createHexagon(x, y, HEX_SIZE);
                hex.setFill(Color.LIGHTGRAY);
                hex.setStroke(Color.BLACK);

                // Add interaction (optional)
                hex.setOnMouseClicked(e -> hex.setFill(Color.BLUE));

                this.getChildren().add(hex);
                
            }
        }
    }
    private Polygon createHexagon(double x, double y, double size) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + 30);
            double xOffset = size * Math.cos(angle);
            double yOffset = size * Math.sin(angle);
            hexagon.getPoints().addAll(x + xOffset, y + yOffset);
        }
        return hexagon;
    }
}
