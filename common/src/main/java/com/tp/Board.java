package com.tp;

import java.util.ArrayList;
import java.util.List;

/**
 * Main board class that manages the game state and movement logic.
 */
public class Board {
    // Static instance of TileMap to manage the state of the game's tiles
    private static TileMap tiles = new TileMap();

    /**
     * Move-making logic
     * 
     * @param move Move object containing the source and destination positions
     * @return boolean indicating whether the move was successful
     */
    public boolean move(Move move) {
        // Extract source and destination positions from the move
        Position from = move.from;
        Position to = move.to;

        // Get the tiles at the source and destination positions
        Tile tileFrom = this.getTile(from.x, from.y);
        Tile tileTo = this.getTile(to.x, to.y);

        // Validate source and destination tiles
        // Check if the source tile is valid (not empty or invalid)
        if (tileFrom == Tile.EMPTY || tileFrom == Tile.INVALID) {
            System.out.println("Move failed: Cannot move from an empty or invalid tile at " + from);
            return false; // Move is not possible
        }
        // Check if the destination tile is empty
        if (tileTo != Tile.EMPTY) {
            System.out.println("Move failed: Destination tile is not empty at " + to);
            return false; // Move is not possible
        }

        // Get the list of valid moves from the source position
        List<Position> validMoves = getValidMoveTargets(from);
        // Check if the destination position is part of valid moves
        if (!validMoves.contains(to)) {
            System.out.println("Move failed: " + to + " is not a valid move from " + from);
            return false; // Move is not valid
        }

        // Perform the move by setting the source tile to empty and the destination tile
        // to the moved tile
        tiles.setTile(from.x, from.y, Tile.EMPTY);
        tiles.setTile(to.x, to.y, tileFrom);
        System.out.println("Move successful: " + from + " -> " + to);
        return true; // Move was successful
    }

    /**
     * Gets a list of valid positions a tile can move to.
     * 
     * @param startPosition The starting position
     * @return List of valid positions
     */
    public List<Position> getValidMoveTargets(Position startPosition) {
        List<Position> validPositions = new ArrayList<>(); // List to hold valid move targets
        BoolMap visited = new BoolMap(); // Map to track visited positions to avoid cycles

        // Define jump directions in a 2D array
        int[][] jumpDirections = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 0, 1 }, { -1, 1 }, { -1, 0 } };

        // Explore possible moves from the starting position
        exploreMoves(startPosition.x, startPosition.y, validPositions, visited, jumpDirections, false);

        System.out.println("Valid moves from " + startPosition + ":");
        // Print valid positions
        for (Position pos : validPositions) {
            System.out.println(pos.toString());
        }
        return validPositions; // Return the list of valid positions
    }

    private void exploreMoves(int x, int y, List<Position> validPositions, BoolMap visited, int[][] jumpDirections,
            boolean isHop) {
        // If the current position has already been visited, return to avoid cycles
        if (visited.getValue(x, y))
            return;

        visited.setValue(x, y, true); // Mark position as visited
        // Explore neighboring positions unless it's a hop move
        if (!isHop)
            exploreNeighbors(x, y, validPositions, jumpDirections);
        // Explore jump moves
        exploreJumps(x, y, validPositions, visited, jumpDirections);
        visited.setValue(x, y, false); // Mark position as unvisited for future explorations
    }

    private void exploreNeighbors(int x, int y, List<Position> validPositions, int[][] directions) {
        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1]; // Calculate new position based on direction
            if (tiles.getTile(nx, ny) == Tile.EMPTY) { // Check if the new position is empty
                Position pos = new Position(nx, ny);
                System.out.println(pos.toString());
                // Add the position to valid positions if it's not already included
                if (!validPositions.contains(pos)) {
                    System.out.println(pos.toString());
                    validPositions.add(pos);
                }
            }
        }
    }

    private void exploreJumps(int x, int y, List<Position> validPositions, BoolMap visited, int[][] directions) {
        for (int[] dir : directions) {
            // Calculate over and target positions for jumping
            int overX = x + dir[0], overY = y + dir[1];
            int toX = x + 2 * dir[0], toY = y + 2 * dir[1];

            // Get the tiles for the over and target positions
            Tile tileOver = tiles.getTile(overX, overY);
            Tile tileTo = tiles.getTile(toX, toY);

            // Check if jumping over a valid tile to an empty tile
            if (tileOver == Tile.EMPTY || tileOver == Tile.INVALID)
                continue; // Cannot jump over an empty or invalid tile
            if (tileTo != Tile.EMPTY)
                continue; // Cannot land on a non-empty tile

            Position pos = new Position(toX, toY);
            // If the target position is not already valid, add it and explore further
            if (!validPositions.contains(pos)) {
                validPositions.add(pos);
                exploreMoves(toX, toY, validPositions, visited, directions, true); // Recursively explore jumps
            }
        }
    }

    /**
     * Initializes the board based on the selected game variant.
     * 
     * @param variant The game variant
     */
    public Board(Variant variant) {
        System.out.println("Creating board with variant " + variant);
        initBoard(variant); // Initialize the board with the given variant
        System.out.println(variant != null ? variant.toString() : "Variant is null");
    }

    public Board(String[] boardData) {
        tiles = new TileMap(boardData); // Initialize the board with the provided data
    }

    @Override
    public String toString() {
        return tiles.toString(); // Return string representation of the tile map
    }

    /**
     * Retrieves the tile at the specified position.
     * 
     * @param row Row index
     * @param col Column index
     * @return The tile at the given position
     */
    public Tile getTile(int row, int col) {
        return tiles.getTile(row, col); // Return the tile at specified coordinates
    }

    private static void initBoard(Variant variant) {
        System.out.println("Initializing board with variant " + variant);
        // Fill the board with the corresponding tiles for each color based on the
        // variant
        fillTiles(tiles, variant, Tile.BLUE, listBlue(variant));
        fillTiles(tiles, variant, Tile.YELLOW, listYellow(variant));
        fillTiles(tiles, variant, Tile.GREEN, listGreen(variant));
        fillTiles(tiles, variant, Tile.RED, listRed(variant));
        fillTiles(tiles, variant, Tile.ORANGE, listOrange(variant));
        fillTiles(tiles, variant, Tile.PURPLE, listPurple(variant));
        fillTiles(tiles, variant, Tile.EMPTY, listEmpty(variant));
    }

    private static void fillTiles(TileMap _tiles_, Variant variant, Tile _tile_, List<Position> _positions_) {
        // Set each position in the provided list to the specified tile
        for (Position p : _positions_) {
            _tiles_.setTile(p.x, p.y, _tile_);
        }
    }

    // Methods to list empty positions based on the current game variant
    private static List<Position> listEmpty(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = -5; i < 5; i++) {
                    for (int j = -5; j < 5; j++) {
                        // Calculate positions for the classic variant
                        if (Math.max(Math.abs(i), Math.abs(j)) <= 4) {
                            if (i + j <= 4 && -i - j <= 4) {
                                positions.add(new Position(i, j));
                            }
                        }
                    }
                }
                return positions;

            case ONEVONE:
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        // Calculate positions for the one-on-one variant
                        if (Math.max(Math.abs(i), Math.abs(j)) <= 5) {
                            if (i + j <= 5 && -i - j <= 5) {
                                positions.add(new Position(i, j));
                            }
                        }
                    }
                }
                return positions;

            default:
                return null; // Handle default case
        }
    }

    // Listing positions for each tile color per game variant
    private static List<Position> listOrange(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = -5; i < 5; i++) {
                    for (int j = -5; j < 5; j++) {
                        // Determine positions for the orange tiles in the classic variant
                        if (Math.max(Math.abs(i), Math.abs(j)) <= 4) {
                            if (-i - j > 4) {
                                positions.add(new Position(i, j));
                            }
                        }
                    }
                }
                return positions;

            case ONEVONE:
                return positions; // Return an empty list for the one-on-one variant

            default:
                return null; // Handle default case
        }
    }

    private static List<Position> listPurple(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = -5; i < 5; i++) {
                    for (int j = -5; j < 5; j++) {
                        // Determine positions for the purple tiles in the classic variant
                        if (Math.max(Math.abs(i), Math.abs(j)) <= 4) {
                            if (-i - j > 4) {
                                positions.add(new Position(-i, -j));
                            }
                        }
                    }
                }
                return positions;

            case ONEVONE:
                return positions; // Return an empty list for the one-on-one variant

            default:
                return null; // Handle default case
        }
    }

    // Method to list other colored positions for each variant
    private static List<Position> listBlue(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = 5; i <= 8; i++) {
                    for (int j = -4; j < 0; j++) {
                        if (Math.max(Math.abs(i), Math.abs(j)) > 4) {
                            if (i + j < 5) {
                                positions.add(new Position(i, j));
                            }
                        }
                    }
                }
                return positions;

            case ONEVONE:
                return positions; // Return an empty list for the one-on-one variant

            default:
                return null; // Handle default case
        }
    }

    private static List<Position> listYellow(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = 5; i <= 8; i++) {
                    for (int j = -4; j < 0; j++) {
                        if (Math.max(Math.abs(i), Math.abs(j)) > 4) {
                            if (i + j < 5) {
                                positions.add(new Position(-i, -j));
                            }
                        }
                    }
                }
                return positions;

            case ONEVONE:
                return positions; // Return an empty list for the one-on-one variant

            default:
                return null; // Handle default case
        }
    }

    private static List<Position> listGreen(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = 1; i <= 4; i++) {
                    for (int j = -8; j <= -5; j++) {
                        if (i + j >= -4) {
                            positions.add(new Position(i, j));
                        }
                    }
                }
                return positions;

            case ONEVONE:
                for (int i = 1; i <= 5; i++) {
                    for (int j = -10; j <= -6; j++) {
                        if (i + j >= -5) {
                            positions.add(new Position(i, j));
                        }
                    }
                }
                return positions;

            default:
                return null; // Handle default case
        }
    }

    private static List<Position> listRed(Variant variant) {
        List<Position> positions = new ArrayList<>();
        switch (variant) {
            case CLASSIC:
                for (int i = 1; i <= 4; i++) {
                    for (int j = -8; j <= -5; j++) {
                        if (i + j >= -4) {
                            positions.add(new Position(-i, -j)); // Red positions are negated
                        }
                    }
                }
                return positions;

            case ONEVONE:
                for (int i = 1; i <= 5; i++) {
                    for (int j = -10; j <= -6; j++) {
                        if (i + j >= -5) {
                            positions.add(new Position(-i, -j)); // Red positions for one-on-one
                        }
                    }
                }
                return positions;

            default:
                return null; // Handle default case
        }
    }

    /**
     * Retrieves the positions of all pawns for the specified color.
     * 
     * @param c The color of the pawns to retrieve
     * @return List of positions occupied by the player's pawns
     */
    public List<Position> getPlayerPawnPositions(Color c) {
        List<Position> positions = new ArrayList<>();
        System.out.println("Getting pawns for " + c);
        // Iterate over the tiles to find the positions of pawns of the specified color
        for (Position p : tiles.getTileMap().keySet()) {
            if (tiles.getTile(p.x, p.y) == Tile.valueOf(c.toString())) // Match tile color
                positions.add(p); // Add matching position to the list
        }
        return positions; // Return list of pawn positions
    }

    /**
     * Retrieves the color of the target opponent based on the given color.
     * 
     * @param c The color of the player
     * @return The color of the opposing target
     */
    public Color getTargetColor(Color c) {
        switch (c) {
            case RED:
                return Color.GREEN; // Opponent color for RED
            case GREEN:
                return Color.RED; // Opponent color for GREEN
            case BLUE:
                return Color.YELLOW; // Opponent color for BLUE
            case YELLOW:
                return Color.BLUE; // Opponent color for YELLOW
            case ORANGE:
                return Color.PURPLE; // Opponent color for ORANGE
            case PURPLE:
                return Color.ORANGE; // Opponent color for PURPLE
            default:
                return null; // Handle default case
        }
    }

    /**
     * Retrieves the possible target positions for a player color based on the game
     * variant.
     * 
     * @param c The color of the player
     * @param v The game variant
     * @return List of target positions for the specified color
     */
    public List<Position> getTargetPositions(Color c, Variant v) {
        switch (c) {
            case RED:
                return listRed(v);
            case GREEN:
                return listGreen(v);
            case BLUE:
                return listBlue(v);
            case YELLOW:
                return listYellow(v);
            case ORANGE:
                return listOrange(v);
            case PURPLE:
                return listPurple(v);
            default:
                return null; // Handle default case
        }
    }

    /**
     * Calculates the distance between two positions.
     * 
     * @param p1 The first position
     * @param p2 The second position
     * @return The distance between p1 and p2
     */
    public int getDistance(Position p1, Position p2) {
        int xSteps = (p1.x - p2.x); // Difference in x coordinates
        int ySteps = (p1.y - p2.y); // Difference in y coordinates
        if (xSteps == ySteps && xSteps == 0)
            return 0; // Both positions are the same
        if (xSteps == ySteps && (xSteps != 0 || ySteps != 0))
            return Math.max(Math.abs(ySteps), Math.abs(xSteps)) + 1; // Diagonal move
        if (xSteps > 0 && ySteps > 0)
            return xSteps + ySteps; // Positive quadrant
        if (xSteps < 0 && ySteps < 0)
            return -xSteps - ySteps; // Negative quadrant
        return Math.max(Math.abs(ySteps), Math.abs(xSteps)); // General case
    }
}