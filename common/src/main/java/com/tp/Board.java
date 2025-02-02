package com.tp;

import java.util.ArrayList;
import java.util.List;

/**
 * Main board class that manages the game state and movement logic.
 */
public class Board {
    private static TileMap tiles = new TileMap();

    /** Move-making logic
     * @param move Move object containing the source and destination positions
     * @return boolean indicating whether the move was successful
     */
    public boolean move(Move move) {
        Position from = move.from;
        Position to = move.to;

        Tile tileFrom = this.getTile(from.x, from.y);
        Tile tileTo = this.getTile(to.x, to.y);

        // Validate source and destination tiles
        if (tileFrom == Tile.EMPTY || tileFrom == Tile.INVALID) {
            System.out.println("Move failed: Cannot move from an empty or invalid tile at " + from);
            return false;
        }
        if (tileTo != Tile.EMPTY) {
            System.out.println("Move failed: Destination tile is not empty at " + to);
            return false;
        }

        List<Position> validMoves = getValidPositionsList(from);
        if (validMoves.contains(to)) {
            System.out.println("Move failed: " + to + " is not a valid move from " + from);
            return false;
        }

        // Perform the move
        tiles.setTile(from.x, from.y, Tile.EMPTY);
        tiles.setTile(to.x, to.y, tileFrom);
        System.out.println("Move successful: " + from + " -> " + to);
        return true;
    }

    /**
     * Gets a list of valid positions a tile can move to.
     * @param startPosition The starting position
     * @return List of valid positions
     */
    public List<Position> getValidPositionsList(Position startPosition) {
        List<Position> validPositions = new ArrayList<>();
        BoolMap visited = new BoolMap();

        // Define jump directions
        int[][] jumpDirections = {{0, -1}, {1, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0}};
        
        exploreMoves(startPosition.x, startPosition.y, validPositions, visited, jumpDirections, false);
        return validPositions;
    }

    private void exploreMoves(int x, int y, List<Position> validPositions, BoolMap visited, int[][] jumpDirections, boolean isHop) {
        if (visited.getValue(x, y)) return;

        visited.setValue(x, y, true);
        if (!isHop) exploreNeighbors(x, y, validPositions, jumpDirections);
        exploreJumps(x, y, validPositions, visited, jumpDirections);
        visited.setValue(x, y, false);
    }

    private void exploreNeighbors(int x, int y, List<Position> validPositions, int[][] directions) {
        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];
            if (tiles.getTile(nx, ny) == Tile.EMPTY) {
                
                Position pos = new Position(nx, ny);
                System.out.println(pos.toString());
                if (!validPositions.contains(pos)){
                    System.out.println(pos.toString());
                    validPositions.add(pos);
                }
            }
        }
    }

    private void exploreJumps(int x, int y, List<Position> validPositions, BoolMap visited, int[][] directions) {
        for (int[] dir : directions) {
            int overX = x + dir[0], overY = y + dir[1];
            int toX = x + 2 * dir[0], toY = y + 2 * dir[1];
            if (tiles.getTile(overX, overY) != Tile.EMPTY && tiles.getTile(toX, toY) == Tile.EMPTY) {
                Position pos = new Position(toX, toY);
                if (!validPositions.contains(pos)) {
                    validPositions.add(pos);
                    exploreMoves(toX, toY, validPositions, visited, directions, true);
                }
            }
        }
    }
    // RED Triangle (Top)
    private static final int[][] redCoordinates = {
        {0, 6},
        {1, 5}, {1, 6},
        {2, 5}, {2, 6}, {2, 7},
        {3, 4}, {3, 5}, {3, 6}, {3, 7}
    };
    // RED Triangle (Top)
    private static final int[][] redCoordinatesBig = {
        {0, 6},
        {1, 5}, {1, 6},
        {2, 5}, {2, 6}, {2, 7},
        {3, 4}, {3, 5}, {3, 6}, {3, 7},
        {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}
    };

    // YELLOW Triangle (Left-Top)
    private static final int[][] yellowCoordinates = {
        {4, 0}, {5, 0},
        {4, 1}, {5, 1}, {6, 1}, {7, 1},
        {4, 2}, {5, 2}, {6, 2},
        {4, 3}
    };

    // ORANGE Triangle (Bottom)
    private static final int[][] orangeCoordinates = {
        {9, 1},
        {10, 1}, {10, 2},
        {11, 0}, {11, 1}, {11, 2},
        {12, 0}, {12, 1}, {12, 2}, {12, 3}
    };

    // GREEN Triangle (Left-Bottom)
    private static final int[][] greenCoordinates = {
        {13, 4},
        {13, 5}, {14, 5}, {15, 5},
        {13, 6}, {14, 6}, {15, 6}, {16, 6},
        {13, 7}, {14, 7}
    };
    private static final int[][] greenCoordinatesBig = {
        {13, 4},
        {13, 5}, {14, 5}, {15, 5},
        {13, 6}, {14, 6}, {15, 6}, {16, 6},
        {13, 7}, {14, 7},{12,4},{12,5},{12,6},{12,7},{12,8}
    };

    // BLUE Triangle (Right-Top)
    private static final int[][] blueCoordinates = {
        {9, 10},
        {10, 10}, {10, 11},
        {11, 9}, {11, 10}, {11, 11},
        {12, 9}, {12, 10}, {12, 11}, {12, 12}
    };

    // PURPLE Triangle (Right-Bottom)
    private static final int[][] purpleCoordinates = {
        {4, 9}, {5, 9},
        {4, 10}, {5, 10}, {6, 10}, {7, 10},
        {4, 11}, {5, 11}, {6, 11},
        {4, 12}
    };

    // Center (Empty)
    private static final int[][] centerCoordinates = {
        {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
        {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8},
        {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 9},
        {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}, {7, 8}, {7, 9},
        {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6}, {8, 7}, {8, 8}, {8, 9}, {8, 10},
        {9, 2}, {9, 3}, {9, 4}, {9, 5}, {9, 6}, {9, 7}, {9, 8}, {9, 9},
        {10, 3}, {10, 4}, {10, 5}, {10, 6}, {10, 7}, {10, 8}, {10, 9},
        {11, 3}, {11, 4}, {11, 5}, {11, 6}, {11, 7}, {11, 8},
        {12, 4}, {12, 5}, {12, 6}, {12, 7}, {12, 8}
    };

    /**
     * Initializes the board based on the selected game variant.
     * @param variant The game variant
     */
    public Board(Variant variant) {
        initBoard(variant);
        System.out.println(variant != null ? variant.toString() : "Variant is null");
    }

    /**
     * Retrieves the tile at the specified position.
     * @param row Row index
     * @param col Column index
     * @return The tile at the given position
     */
    public Tile getTile(int row, int col) {
        return tiles.getTile(row, col);
    }

    private static void initBoard(Variant variant) {
        fillCenter(tiles, variant);
        fillTopLeft(tiles, variant, Tile.ORANGE);
        fillBotRight(tiles, variant, Tile.PURPLE);
        fillTopRight(tiles, variant, Tile.BLUE);
        fillBotLeft(tiles, variant, Tile.YELLOW);
        fillTop(tiles, variant, Tile.GREEN);
        fillBot(tiles, variant, Tile.RED);
        //fillBoard(tiles, variant);
    }
    private static void fillCenter(TileMap tiles, Variant variant){
        for (int i = -5; i < 5; i++){
            for (int j = -5; j < 5; j++){
                if (Math.max(Math.abs(i), Math.abs(j)) <= 4){
                    if (i + j <= 4){
                        if (-i -j <= 4){
                            tiles.setTile(i, j, Tile.EMPTY);
                        }
                    }
                }
            }
        }
    }
    private static void fillTopLeft(TileMap tiles, Variant variant, Tile tile){
        for (int i = -5; i < 5; i++){
            for (int j = -5; j < 5; j++){
                if (Math.max(Math.abs(i), Math.abs(j)) <= 4){
                    if (-i -j > 4){
                        tiles.setTile(i, j, tile);
                    }
                }
            }
        }
    }
    private static void fillBotRight(TileMap tiles, Variant variant, Tile tile){
        for (int i = -5; i < 5; i++){
            for (int j = -5; j < 5; j++){
                if (Math.max(Math.abs(i), Math.abs(j)) <= 4){
                    if (-i -j > 4){
                        tiles.setTile(-i, -j, tile);
                    }
                }
            }
        }
    }
    private static void fillTopRight(TileMap tiles, Variant variant, Tile tile){
        for (int i = 5; i <= 8; i++){
            for (int j = -4; j < 0; j++){
                if (Math.max(Math.abs(i), Math.abs(j)) > 4){
                    if (i + j < 5) {
                        tiles.setTile(i, j, tile);
                    } 
                }
            }
        }
    }
    private static void fillBotLeft(TileMap tiles, Variant variant, Tile tile){
        for (int i = 5; i <= 8; i++){
            for (int j = -4; j < 0; j++){
                if (Math.max(Math.abs(i), Math.abs(j)) > 4){
                    if (i + j < 5) {
                        tiles.setTile(-i, -j, tile);
                    } 
                }
            }
        }
    }
    private static void fillTop(TileMap tiles, Variant variant, Tile tile){
        for (int i = 1; i <= 4; i++){
            for (int j = -8; j <= -5; j++){
                if (i + j >= -4){
                    tiles.setTile(i, j, tile);
                }
            }
        }
    }
    private static void fillBot(TileMap tiles, Variant variant, Tile tile){
        for (int i = 1; i <= 4; i++){
            for (int j = -8; j <= -5; j++){
                if (i + j >= -4){
                    tiles.setTile(-i, -j, tile);
                }
            }
        }
    }
    private static void fillBoard(TileMap tiles, Variant variant) {
        if (variant == Variant.CLASSIC) {
            fillArea(tiles, centerCoordinates, Tile.EMPTY);
            fillArea(tiles, redCoordinates, Tile.RED);
            fillArea(tiles, yellowCoordinates, Tile.YELLOW);
            fillArea(tiles, orangeCoordinates, Tile.ORANGE);
            fillArea(tiles, greenCoordinates, Tile.GREEN);
            fillArea(tiles, blueCoordinates, Tile.BLUE);
            fillArea(tiles, purpleCoordinates, Tile.PURPLE);
        } else if (variant == Variant.ONEVONE) {
            fillArea(tiles, centerCoordinates, Tile.EMPTY);
            fillArea(tiles, redCoordinatesBig, Tile.RED);
            fillArea(tiles, greenCoordinatesBig, Tile.GREEN);
        } else {
            System.out.println("Invalid variant");
        }
    }

    /**
     * Checks for a winning color.
     * @return The winning color, or null if no winner.
     */
    public Color checkForWin() {
        for (Color color : Color.values()) {
            int[][] targetArea = getTargetCoordinates(color);
            if (targetArea != null && isColorWinner(targetArea, color)) {
                return color;
            }
        }
        return null;
    }
    private int[][] getTargetCoordinates(Color color) {
        switch (color) {
            case RED: return greenCoordinates; // Red's target is Green's starting area
            case GREEN: return redCoordinates; // Green's target is Red's starting area
            case YELLOW: return blueCoordinates; // Yellow's target is Blue's starting area
            case BLUE: return yellowCoordinates; // Blue's target is Yellow's starting area
            case ORANGE: return purpleCoordinates; // Orange's target is Purple's starting area
            case PURPLE: return orangeCoordinates; // Purple's target is Orange's starting area
            default: return null;
        }
    }
    

    private boolean isColorWinner(int[][] targetArea, Color color) {
        Tile targetTile = Tile.valueOf(color.name());
        for (int[] pos : targetArea) {
            if (getTile(pos[0], pos[1]) != targetTile) return false;
        }
        return true;
    }

    private static void fillArea(TileMap tiles, int[][] coordinates, Tile color) {
        for (int[] coordinate : coordinates) {
            tiles.setTile(coordinate[0], coordinate[1], color);
        }
    }
}
