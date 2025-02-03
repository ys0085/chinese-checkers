package com.tp;
import java.util.List;

public class BotPlayer extends Player {
    private Color color;
    private Board board;

    public BotPlayer(Color color) {
        super("Bot", color);
        this.color = color;
    }

    @Override
    public void notifyTurn() {
        // TODO: Implement bot logic
    }

    @Override
    public void notifyPlayerListChange(List<Player> players) {
        // Do nothing
    }

    @Override
    public void notifyBoardChange(Board board) {
        this.board = board;
    }

    @Override
    public void notifyGameEnd(Color winner) {
        // Do nothing
    }
}
