package com.tp;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;


public class BoardPanel extends Region {
    final private int HEX_SIZE = 20;
    private Board board;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    BoardPanel() {
        super();
        int rows = 20; 
        int cols = 20; 
        

        double xOffset = Math.sqrt(3) * HEX_SIZE; 
        double yOffset = HEX_SIZE * 1.5;             

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                
                double x = col * xOffset + (row % 2 == 0 ? 0 : HEX_SIZE * Math.sqrt(3) / 2); 
                double y = row * yOffset;

                Tile hex = new Tile(col, row, x, y, HEX_SIZE);
                tiles.add(hex);
                hex.setFill(Color.LIGHTGRAY);
                hex.setStroke(Color.BLACK);
                

                // Add interaction (optional)
                hex.setOnMouseClicked(e -> hex.setFill(Color.GRAY));
                Label coordinates = new Label(row + "\n" + col);
                coordinates.setLayoutX(x - HEX_SIZE / 2);
                coordinates.setLayoutY(y - HEX_SIZE / 1.5);
                this.getChildren().add(hex);
                this.getChildren().add(coordinates);
                
            }
        }
    }
    public void update(){
        for(Tile t : tiles){
            t.spot = board.getSpot(t.row, t.col);
        }
    }
    public void setBoard(Board b){
        this.board = b;
    }
}
