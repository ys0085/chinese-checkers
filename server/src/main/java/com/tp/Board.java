package com.tp;

public class Board { 
    private Spot[][] spots = new Spot[40][40];
    public boolean move(Move move){
        return true;
    }; //Docelowo move(Tile t1, Tile t2)
    public Spot getSpot(int row, int col){
        return spots[row][col];
    }
}