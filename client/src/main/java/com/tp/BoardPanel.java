package com.tp;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;


public class BoardPanel extends Region {
    final private int HEX_SIZE = 20;
    private Hex selectedHex = null;

    private ArrayList<Hex> hexes = new ArrayList<>();

    final private double xOffset = Math.sqrt(3) * HEX_SIZE; 
    final private double yOffset = HEX_SIZE * 1.5;     
    final private double xPadding = 50; 
    final private double yPadding = 50; 

    final private int rows = 17;    
    final private int cols = 13; 

    private Consumer<Move> moveCallback;

    BoardPanel(Consumer<Move> moveCallback) {
        super();
        this.moveCallback = moveCallback;
        board = new Board();

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                
                double x = col * xOffset + (row % 2 == 0 ? 0 : HEX_SIZE * Math.sqrt(3) / 2) + xPadding; 
                double y = row * yOffset + yPadding;

                Hex hex = new Hex(col, row, x, y, HEX_SIZE);
                hexes.add(hex);
                hex.setFill(Color.LIGHTGRAY);
                hex.setStroke(Color.GRAY);
                
                Label coordinates = new Label(row + "\n" + col);
                coordinates.setLayoutX(x - HEX_SIZE / 2);
                coordinates.setLayoutY(y - HEX_SIZE / 1.5);
                this.getChildren().add(hex);
                this.getChildren().add(coordinates);


                //Sending moves through UI
                hex.setOnMouseClicked(e -> {
                    if(!hex.isVisible()) return;
                    if((!(hex.tile.toString().equals(Client.getInstance().getColor().toString()))) && (selectedHex == null )) return;
                    if(!Client.getInstance().isYourTurn()) return;
                    if(Client.getInstance().getWinningColor() != null) return;
                    if(hex.isSelected()){
                        hex.toggleSelect();
                        selectedHex = null;
                    }
                    else {
                        if(selectedHex == null) {
                            selectedHex = hex;
                            hex.toggleSelect();
                        }
                        else {
                            parseMove(selectedHex, hex);
                            selectedHex.toggleSelect();
                            selectedHex = null;
                        }
                    }
                });
                
            }
        }
    }



    /**
     * BoardPanel update
     */
    public void update(){
        for(Hex h : hexes){
            h.setTile(board.getTile(h.row, h.col));
        }
    }

    private Board board;
    public void setBoard(Board b){ this.board = b; }
    public Board getBoard(){ return board; }

    
    /** Update board with a new move
     * @param move
     * @return ?success
     */
    public boolean move(Move move){
        Position from = move.from;
        Position to = move.to;
        boolean success = board.move(new Move(from, to));
        if (success) {
            UIApp.boardPanel.update();
            Client.getInstance().checkWin(board);
        }
        else {
            System.out.println("Invalid move received - possible desync");
        }
        return success;
    }

    
    private void parseMove(Hex h1, Hex h2){
        Position from = new Position(h1.row, h1.col);
        Position to = new Position(h2.row, h2.col);
        boolean success = board.move(new Move(from , to));
        // System.out.println("move: " + from.toString()+ " " + to.toString() + star);
        if (success) {
            moveCallback.accept(new Move(from, to));
            Client.getInstance().setYourTurn(false);
            Client.getInstance().checkWin(board);
        }
        
        
        UIApp.boardPanel.update();
    
    }



}
