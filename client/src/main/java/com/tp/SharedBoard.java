package com.tp;

public class SharedBoard {
    private Tile[][] board = new Tile[3][3];

    public SharedBoard() {
        // Initialize the board with empty spaces
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Tile.EMPTY;
            }
        }
    }

    // Thread-safe method to update the board
    public synchronized boolean move(Move m) {
        var from = m.from;
        var to = m.to;
        return false; 
    //    if(board[to.x][to.y] != Tile.EMPTY) return false;
      //  board[to.x][to.y] = board[from.x][from.y];
    //    board[from.x][from.y] = Tile.EMPTY;
      //  return true;
    }
}