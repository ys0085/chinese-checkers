<<<<<<< Updated upstream
package com.tp;

/**
 * Represents a move from one position to another.
 */
public class Move {
    /**
     * The starting position of the move.
     */
    final public Position from;
    
    /**
     * The destination position of the move.
     */
    final public Position to;

    /**
     * Constructs a Move with default positions.
     * Initializes both `from` and `to` positions with default values.
     */
    public Move() {
        this.from = new Position();
        this.to = new Position();
    }

    /**
     * Constructs a Move with specified positions.
     *
     * @param from The starting position.
     * @param to The destination position.
     */
    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }
    
}
=======
package com.tp;

/**
 * Represents a move from one position to another.
 */
public class Move {
    /**
     * The starting position of the move.
     */
    final public Position from;

    /**
     * The destination position of the move.
     */
    final public Position to;

    /**
     * Constructs a Move with default positions.
     * Initializes both `from` and `to` positions with default values.
     */
    public Move() {
        this.from = new Position();
        this.to = new Position();
    }

    /**
     * Constructs a Move with specified positions.
     *
     * @param from The starting position.
     * @param to   The destination position.
     */
    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }
}
>>>>>>> Stashed changes
