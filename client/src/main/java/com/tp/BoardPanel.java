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

    private final Consumer<Move> moveCallback;

    private static final Client client = Client.getInstance();

    BoardPanel(Consumer<Move> moveCallback) {
        super();
        this.moveCallback = moveCallback;
        this.board = new Board(client.getVariant());
    
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
        if (!(hex.tile.toString().equals(client.getColor().toString())) && selectedHex == null) return;
        if (!client.isYourTurn()) return;
        if (client.getWinningColor() != null) return;
        if (client.isReplayMode()) return;

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
            client.checkWin(board);
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
            client.setYourTurn(false);
            client.checkWin(board);
        }
        UIApp.boardPanel.update();
    }

    public void playReplay(Replay replay) {
        ArrayList<Move> moves = replay.getMoveHistory();
        Variant var = replay.getVariant();
        board = new Board(var);
        try {
            for(Move m : moves){
                Thread.sleep(1000);
                move(m);
            }
        } catch (InterruptedException e) {
            System.err.println("Replay playback interrupted");
        }   
    }
}
