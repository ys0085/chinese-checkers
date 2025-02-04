package com.tp;
// enum na mozliwe stany miejsca na planszy

public enum Tile {
    EMPTY,
    RED,
    YELLOW,
    ORANGE,
    GREEN,
    BLUE,
    PURPLE,
    INVALID;

    public Color toColor() {
        if (this == EMPTY || this == INVALID)
            return null;
        else
            return Color.valueOf(this.toString());
    }
}