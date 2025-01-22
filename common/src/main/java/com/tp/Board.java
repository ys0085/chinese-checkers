package com.tp;

import java.util.ArrayList;
import java.util.List;

public class Board { 
    private static Tile[][] tiles = new Tile[20][20];

    
    /** Move-making logic
     * @param move
     * @return boolean
     */
    public boolean move(Move move) {
        Position from = move.from;
        Position to = move.to;

        // Get tiles for the source and destination positions
        Tile tileFrom = this.getTile(from.x, from.y);
        Tile tileTo = this.getTile(to.x, to.y);

        // Validate the source tile
        if (tileFrom == Tile.EMPTY || tileFrom == Tile.INVALID) {
            System.out.println("Move failed: Cannot move from an empty or invalid tile at position (" + from.x + ", " + from.y + ").");
            return false;
        }

        // Validate the destination tile
        if (tileTo != Tile.EMPTY) {
            System.out.println("Move failed: Cannot move to a non-empty tile at position (" + to.x + ", " + to.y + ").");
            return false;
        }

        // Get the list of valid moves from the source position
        List<Position> validMoves = getValidPositionsList(from);

        // Check if the destination position is in the list of valid moves
        boolean isValid = validMoves.stream().anyMatch(pos -> pos.x == to.x && pos.y == to.y);

        if (!isValid) {
            System.out.println("Move failed: Destination position (" + to.x + ", " + to.y + ") is not a valid move from position (" + from.x + ", " + from.y + ").");
            return false;
        }

        // Perform the move
        tiles[from.x][from.y] = Tile.EMPTY; // Clear the source tile
        tiles[to.x][to.y] = tileFrom;       // Set the destination tile to the moved piece
        System.out.println("Move successful: Moved tile from position (" + from.x + ", " + from.y + ") to position (" + to.x + ", " + to.y + ").");
        return true; // Move was successful
    }

    
    /** Getter for valid positions
     * @param startPosition
     * @return List<Position>
     */
    public List<Position> getValidPositionsList(Position startPosition) {
        List<Position> validPositions = new ArrayList<>();
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];

        // Define neighbor directions for even and odd rows
        int[][] evenRowDirections = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}};
        int[][] oddRowDirections = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}};

        // Define jump directions for even and odd rows
        int[][] evenRowJumpOverDirections = {{-1, -1}, {1, -1}, {0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int[][] evenRowJumpToDirections   = {{-2, -1}, {2, -1}, {0, -2}, {0, 2}, {-2, 1}, {2, 1}};
        int[][] oddRowJumpOverDirections  = {{-1,  0}, {1,  0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}};
        int[][] oddRowJumpToDirections    = {{-2, -1}, {2, -1}, {0, -2}, {0, 2}, {-2, 1}, {2, 1}};

        System.out.println("---------- exploring for " + startPosition.toString());
        exploreMoves(startPosition.x, startPosition.y, validPositions, visited,
                     evenRowDirections, oddRowDirections,
                     evenRowJumpOverDirections, evenRowJumpToDirections,
                     oddRowJumpOverDirections, oddRowJumpToDirections,
                     false); // Initial exploration is not a hop

        return validPositions;
    }

    private void exploreMoves(int x, int y, List<Position> validPositions, boolean[][] visited,
                              int[][] evenRowDirections, int[][] oddRowDirections,
                              int[][] evenRowJumpOverDirections, int[][] evenRowJumpToDirections,
                              int[][] oddRowJumpOverDirections, int[][] oddRowJumpToDirections,
                              boolean isHop) {
        if (!isWithinBounds(x, y)) {
            System.out.println("Skipping position: (" + x + ", " + y + ") (out of bounds)");
            return;
        }

        if (visited[x][y]) {
            System.out.println("Skipping position: (" + x + ", " + y + ") (already visited)");
            return;
        }

        System.out.println("Exploring position: (" + x + ", " + y + ") (isHop = " + isHop + ")");

        // Mark the current position as visited
        visited[x][y] = true;

        // Select directions for single-step and jump moves based on row type
        int[][] singleStepDirections = (x % 2 == 0) ? evenRowDirections : oddRowDirections;
        int[][] jumpOverDirections = (x % 2 == 0) ? evenRowJumpOverDirections : oddRowJumpOverDirections;
        int[][] jumpToDirections = (x % 2 == 0) ? evenRowJumpToDirections : oddRowJumpToDirections;

        // Explore neighbors (single-step moves) if not a hop
        if (!isHop) {
            exploreNeighbors(x, y, validPositions, singleStepDirections);
        }

        // Explore jumps (multi-hop moves)
        exploreJumps(x, y, validPositions, visited, jumpOverDirections, jumpToDirections,
                     evenRowDirections, oddRowDirections,
                     evenRowJumpOverDirections, evenRowJumpToDirections,
                     oddRowJumpOverDirections, oddRowJumpToDirections);

        // Clear visited state after all paths are explored
        visited[x][y] = false;
    }

    private void exploreNeighbors(int x, int y, List<Position> validPositions, int[][] directions) {
        for (int[] direction : directions) {
            int neighborX = x + direction[0];
            int neighborY = y + direction[1];

            if (isWithinBounds(neighborX, neighborY) && tiles[neighborX][neighborY] == Tile.EMPTY) {
                Position validPosition = new Position(neighborX, neighborY);
                if (!validPositions.contains(validPosition)) {
                    validPositions.add(validPosition);
                    System.out.println("Valid Position (Single Step): " + validPosition);
                }
            }
        }
    }

    private void exploreJumps(int x, int y, List<Position> validPositions, boolean[][] visited,
                              int[][] jumpOverDirections, int[][] jumpToDirections,
                              int[][] evenRowDirections, int[][] oddRowDirections,
                              int[][] evenRowJumpOverDirections, int[][] evenRowJumpToDirections,
                              int[][] oddRowJumpOverDirections, int[][] oddRowJumpToDirections) {
        int[][] currentJumpOverDirections = (x % 2 == 0) ? evenRowJumpOverDirections : oddRowJumpOverDirections;
        int[][] currentJumpToDirections = (x % 2 == 0) ? evenRowJumpToDirections : oddRowJumpToDirections;

        for (int i = 0; i < currentJumpOverDirections.length; i++) {
            int jumpOverX = x + currentJumpOverDirections[i][0];
            int jumpOverY = y + currentJumpOverDirections[i][1];
            int jumpToX = x + currentJumpToDirections[i][0];
            int jumpToY = y + currentJumpToDirections[i][1];

            if (isWithinBounds(jumpOverX, jumpOverY) && isWithinBounds(jumpToX, jumpToY)) {
                if (tiles[jumpOverX][jumpOverY] != Tile.EMPTY && tiles[jumpOverX][jumpOverY] != Tile.INVALID &&
                    tiles[jumpToX][jumpToY] == Tile.EMPTY) {
                    Position validJumpPosition = new Position(jumpToX, jumpToY);
                    if (!validPositions.contains(validJumpPosition)) {
                        validPositions.add(validJumpPosition);
                        System.out.println("Valid Position (Jump): " + validJumpPosition);

                        // Recursively explore from the new jump position, marking it as a hop
                        exploreMoves(jumpToX, jumpToY, validPositions, visited,
                                     evenRowDirections, oddRowDirections,
                                     evenRowJumpOverDirections, evenRowJumpToDirections,
                                     oddRowJumpOverDirections, oddRowJumpToDirections,
                                     true); // Pass true to indicate it's a hop
                    }
                }
            }
        }
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length && tiles[x][y] != Tile.INVALID;
    }

    public Board() {
        initBoard();
    }
    
    /**
     * Getter for specific tile
     * @param row
     * @param col
     * @return tile
     */
    public Tile getTile(int row, int col){
        return tiles[row][col];
    }

    /**
     * Board initialization
     */
    public static void initBoard() {
        setAllTilesToInvalid(tiles);      // Step 1: Set all tiles to INVALID
        fillBoard(tiles);        // Step 2: Hardcode all tiles
    }

    private static void setAllTilesToInvalid(Tile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = Tile.INVALID;
            }
        }
    }

    private static void fillBoard(Tile[][] tiles) {
        // RED Triangle (Top)
        int[][] redCoordinates = {
            {0, 6},
            {1, 5}, {1, 6},
            {2, 5}, {2, 6}, {2, 7},
            {3, 4}, {3, 5}, {3, 6}, {3, 7}
        };
        fillArea(tiles, redCoordinates, Tile.RED);

        // YELLOW Triangle (Left-Top)
        int[][] yellowCoordinates = {
            {4, 0}, {5, 0},
            {4, 1}, {5, 1}, {6, 1}, {7, 1},
            {4, 2}, {5, 2}, {6, 2},
            {4, 3}
        };
        fillArea(tiles, yellowCoordinates, Tile.YELLOW);

        // ORANGE Triangle (Bottom)
        int[][] orangeCoordinates = {
            {9, 1},
            {10, 1}, {10, 2},
            {11, 0}, {11, 1}, {11, 2},
            {12, 0}, {12, 1}, {12, 2}, {12, 3}
        };
        fillArea(tiles, orangeCoordinates, Tile.ORANGE);

        // GREEN Triangle (Left-Bottom)
        int[][] greenCoordinates = {
            {13, 4},
            {13, 5}, {14, 5}, {15, 5},
            {13, 6}, {14, 6}, {15, 6}, {16, 6},
            {13, 7}, {14, 7}
        };
        fillArea(tiles, greenCoordinates, Tile.GREEN);

        // BLUE Triangle (Right-Top)
        int[][] blueCoordinates = {
            {9, 10},
            {10, 10}, {10, 11},
            {11, 9}, {11, 10}, {11, 11},
            {12, 9}, {12, 10}, {12, 11}, {12, 12}
        };
        fillArea(tiles, blueCoordinates, Tile.BLUE);

        // PURPLE Triangle (Right-Bottom)
        int[][] purpleCoordinates = {
            {4, 9}, {5, 9},
            {4, 10}, {5, 10}, {6, 10}, {7, 10},
            {4, 11}, {5, 11}, {6, 11},
            {4, 12}
        };
        fillArea(tiles, purpleCoordinates, Tile.PURPLE);
        

        // Center (Empty)
        int[][] centerCoordinates = {
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
        fillArea(tiles, centerCoordinates, Tile.EMPTY);
    }

    private static void fillArea(Tile[][] tiles, int[][] coordinates, Tile color) {
        for (int[] coordinate : coordinates) {
            tiles[coordinate[0]][coordinate[1]] = color;
        }
    }
}