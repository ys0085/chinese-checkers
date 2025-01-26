package com.tp;

public enum Color { //Colors numbered like in /colors.png
    RED,
    YELLOW,
    ORANGE,
    GREEN,
    BLUE,
    PURPLE;
    public javafx.scene.paint.Color toPaintColor(){
        return switch(this){
            case RED -> javafx.scene.paint.Color.RED;
            case YELLOW -> javafx.scene.paint.Color.YELLOW;
            case ORANGE -> javafx.scene.paint.Color.ORANGE;
            case GREEN -> javafx.scene.paint.Color.GREEN;
            case BLUE -> javafx.scene.paint.Color.LIGHTBLUE;
            case PURPLE -> javafx.scene.paint.Color.PURPLE;
            default -> javafx.scene.paint.Color.LIGHTGRAY;
        };
    }
}
