package com.tp;

public class Board { 
    private Tile[][] tiles = new Tile[40][40];
    public boolean move(Move move){
        return true;
    }; //Docelowo move(Tile t1, Tile t2)
    public Tile getTile(int row, int col){
        return tiles[row][col];
    }
}