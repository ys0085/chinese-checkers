package com.tp;

public class Board { 
    private static Tile[][] tiles = new Tile[20][20];
    public boolean move(Move move){
        return true;
    }; //Docelowo move(Tile t1, Tile t2)

    //Docelowo move(Tile t1, Tile t2)
    public Board() {
        initBoard();
    }
    
    public Tile getTile(int row, int col){
        return tiles[row][col];
    }

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

        // GREEN Triangle (Left-Bottom)
        int[][] greenCoordinates = {
            {9, 1},
            {10, 1}, {10, 2},
            {11, 0}, {11, 1}, {11, 2},
            {12, 0}, {12, 1}, {12, 2}, {12, 3}
        };
        fillArea(tiles, greenCoordinates, Tile.GREEN);

        // ORANGE Triangle (Bottom)
        int[][] orangeCoordinates = {
            {13, 9},
            {13, 10}, {14, 10},
            {13, 11}, {14, 11}, {15, 11},
            {13, 12}, {14, 12}, {15, 12}, {16, 12}
        };
        fillArea(tiles, orangeCoordinates, Tile.ORANGE);

        // BLUE Triangle (Right-Top)
        int[][] blueCoordinates = {
            {4, 9},
            {4, 10}, {5, 10},
            {4, 11}, {5, 11}, {6, 11},
            {4, 12}, {5, 12}, {6, 12}, {7, 12}
        };
        fillArea(tiles, blueCoordinates, Tile.BLUE);

        // PURPLE Triangle (Right-Bottom)
        int[][] purpleCoordinates = {
            {9, 13},
            {10, 13}, {10, 14},
            {11, 13}, {11, 14}, {11, 15},
            {12, 13}, {12, 14}, {12, 15}, {12, 16}
        };
        fillArea(tiles, purpleCoordinates, Tile.PURPLE);

        // Center (Empty)
        int[][] centerCoordinates = {
            {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
            {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8}, {5, 9},
            {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 9}, {6, 10},
            {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}, {7, 8}, {7, 9}, {7,10},
            {8, 4}, {8, 5}, {8, 6}, {8, 7}, {8, 8}, {8, 9}, {8, 10}, {8, 11}, {8, 12},
            {9, 5}, {9, 6}, {9, 7}, {9, 8}, {9, 9}, {9, 10}, {9, 11}, {9, 12},
            {10, 6}, {10, 7}, {10, 8}, {10, 9}, {10, 10}, {10, 11}, {10, 12},
            {11, 7}, {11, 8}, {11, 9}, {11, 10}, {11, 11}, {11, 12},
            {12, 8}, {12, 9}, {12, 10}, {12, 11}, {12, 12}
        };
        fillArea(tiles, centerCoordinates, Tile.EMPTY);
    }

    private static void fillArea(Tile[][] tiles, int[][] coordinates, Tile color) {
        for (int[] coordinate : coordinates) {
            tiles[coordinate[0]][coordinate[1]] = color;
        }
    }
}