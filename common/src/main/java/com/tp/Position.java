package com.tp;

public class Position {
    public int x, y;

    Position() {
    };

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns string of full position
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }
        Position pos = (Position) obj;
        return pos.x == this.x && pos.y == this.y; // Corrected comparison
    }

    @Override
    public int hashCode() {
        return 31 * x + y; // Good practice to override hashCode with equals
    }
}
