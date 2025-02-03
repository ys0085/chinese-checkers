package com.tp;
import java.util.List;

public abstract class Player {

    protected String name;
    protected Color color;

    Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    Color getColor()
    {
        return color;
    }

    void setColor(Color color)
    {
        this.color = color;
    }

    String getName()
    {
        return name;
    }

    void setName(String name)
    {
        this.name = name;
    }

    abstract void notifyTurn();
    abstract void notifyPlayerListChange(List<Player> players);
    abstract void notifyBoardChange(Board board);
    abstract void notifyGameEnd(Color winner);

}
