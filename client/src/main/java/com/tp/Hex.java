package com.tp;

import javafx.scene.shape.Polygon;

public class Hex extends Polygon {
    public int col, row;
    public Tile tile;
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
    
}
