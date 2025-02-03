package com.tp;
// enum na mozliwe stany miejsca na planszy

<<<<<<< Updated upstream

public enum Tile { 
=======
public enum Tile {
>>>>>>> Stashed changes
    EMPTY,
    RED,
    YELLOW,
    ORANGE,
    GREEN,
    BLUE,
    PURPLE,
    INVALID;
<<<<<<< Updated upstream
    public Color toColor(){
        if(this == EMPTY || this == INVALID) return null;
        else return Color.valueOf(this.toString());
=======

    public Color toColor() {
        if (this == EMPTY || this == INVALID)
            return null;
        else
            return Color.valueOf(this.toString());
>>>>>>> Stashed changes
    }
}