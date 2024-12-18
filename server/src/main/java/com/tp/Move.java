package com.tp;

public class Move {
    final public Position from, to;
    Move(){
        this.from = new Position();
        this.to = new Position();
    }
    Move(Position from, Position to){
        this.from = from;
        this.to = to;
    }
    public String toString(){
        return "MOVE " + from.x + " " + from.y + " " + to.x + " " + to.y;
    }
}
