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
