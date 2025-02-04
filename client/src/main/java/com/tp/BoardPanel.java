package com.tp;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Class representing the board panel which displays the hexagonal game board.
 * This panel is responsible for rendering the game board and handling user interactions.
 */
public class BoardPanel extends Region {
    /** Size of each hexagon in the board. */
    private static final int HEX_SIZE = 20;

    /** Currently selected hexagon. */
    private Hex selectedHex = null;

    /** List to store all hexagons on the board. */
    private ArrayList<Hex> hexes = new ArrayList<>();

    /** The game board. */
    private Board board;

    /** X-axis offset for calculating hexagon positions. */
    private static final double X_OFFSET = Math.sqrt(3) * HEX_SIZE;

    /** Y-axis offset for calculating hexagon positions. */
    private static final double Y_OFFSET = HEX_SIZE * 1.5;

    /** Padding along the X-axis to center the grid. */
    private static final double X_PADDING = -150;

    /** Padding along the Y-axis to adjust vertical position. */
    private static final double Y_PADDING = 50;

    /** Number of rows in the board. */
    private static final int ROWS = 10;

    /** Number of columns in the board. */
    private static final int COLS = 10;

    /** Callback function for handling moves in the game. */
    private Consumer<Move> moveCallback;

    /**
     * Constructor that initializes the board panel.
     *
     * @param moveCallback A callback function to handle moves in the game.
     */
    BoardPanel(Consumer<Move> moveCallback) {
        super();
        this.moveCallback = moveCallback;
        this.board = new Board(Client.getInstance().getVariant());

        // Loop through rows and columns to create a grid of hexagons
        for (int row = -ROWS; row <= ROWS; row++) {
            for (int col = -COLS; col <= COLS; col++) {
                double x = (row + ROWS) * X_OFFSET + (col + COLS) * X_OFFSET / 2 + X_PADDING;
                double y = (col + COLS) * Y_OFFSET + Y_PADDING;

                Hex hex = new Hex(col, row, x, y, HEX_SIZE);
                hexes.add(hex);
                hex.setFill(Color.LIGHTGRAY);
                hex.setStroke(Color.GRAY);

                Label coordinates = new Label(
                        row + " " + col + "\n" + board.getDistance(new Position(row, col), new Position(4, 2)));
                coordinates.setLayoutX(x - HEX_SIZE / 2);
                coordinates.setLayoutY(y - HEX_SIZE / 1.5);
                coordinates.setVisible(false);

                this.getChildren().add(hex);
                this.getChildren().add(coordinates);

                hex.setOnMouseClicked(e -> handleHexClick(hex));
            }
        }
    }

    /**
     * Handles click events on hexagons.
     *
     * @param hex The hexagon that was clicked.
     */
    private void handleHexClick(Hex hex) {
        if (!hex.isVisible())
            return;
        if (!(hex.tile.toString().equals(Client.getInstance().getColor().toString())) && selectedHex == null)
            return;
        if (!Client.getInstance().isYourTurn())
            return;
        if (Client.getInstance().getWinningColor() != null)
            return;

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

    /**
     * Updates the display of the board and hexagons.
     * This method refreshes the visual representation of the board based on the current state.
     */
    public void update() {
        for (Hex h : hexes) {
            h.setTile(board.getTile(h.row, h.col));
        }
    }

    /**
     * Sets a new board state.
     *
     * @param b The new board to set.
     */
    public void setBoard(Board b) {
        System.out.println("Setting board : " + b.toString());
        this.board = b;
        UIApp.boardPanel.update();
    }

    /**
     * Gets the current board.
     *
     * @return The current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Parses and executes a move from one hexagon to another.
     *
     * @param h1 The source hexagon.
     * @param h2 The destination hexagon.
     */
    private void parseMove(Hex h1, Hex h2) {
        Position from = new Position(h1.row, h1.col);
        Position to = new Position(h2.row, h2.col);
        boolean success = board.move(new Move(from, to));
        if (success) {
            moveCallback.accept(new Move(from, to));
            Client.getInstance().setYourTurn(false);
        }
        UIApp.boardPanel.update();
    }
}