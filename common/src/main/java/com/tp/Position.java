package com.tp;

public class Position {
    public int x, y;
    Position(){};
    Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }    
    @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
}
