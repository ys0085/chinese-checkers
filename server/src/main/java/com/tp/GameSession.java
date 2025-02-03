package com.tp;

import java.util.HashMap;
import java.util.Random;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class GameSession {

    // Builder design pattern

    public static class GameSessionBuilder {
        private int playerCapacity;
        private String ID;
        private Board board = new Board(Server.getInstance().getVariant());
        private HashMap<Color, Player> players = new HashMap<>(6);

        GameSessionBuilder(String ID, int playerCapacity) {
            this.ID = ID;
            this.playerCapacity = playerCapacity;
            switch (playerCapacity) {
                case 2 -> {
                    players.put(Color.RED, new BotPlayer(Color.RED));
                    players.put(Color.YELLOW, null);
                    players.put(Color.ORANGE, null);
                    players.put(Color.GREEN, new BotPlayer(Color.GREEN));
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, null);
                }
                case 3 -> {
                    players.put(Color.RED, new BotPlayer(Color.RED));
                    players.put(Color.YELLOW, null);
                    players.put(Color.ORANGE, new BotPlayer(Color.ORANGE));
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, new BotPlayer(Color.BLUE));
                    players.put(Color.PURPLE, null);
                }
                case 4 -> {
                    players.put(Color.RED, new BotPlayer(Color.RED));
                    players.put(Color.YELLOW, new BotPlayer(Color.YELLOW));
                    players.put(Color.ORANGE, null);
                    players.put(Color.GREEN, new BotPlayer(Color.GREEN));
                    players.put(Color.BLUE, new BotPlayer(Color.BLUE));
                    players.put(Color.PURPLE, null);
                }
                case 6 -> {
                    players.put(Color.RED, new BotPlayer(Color.RED));
                    players.put(Color.YELLOW, new BotPlayer(Color.YELLOW));
                    players.put(Color.ORANGE, new BotPlayer(Color.ORANGE));
                    players.put(Color.GREEN, new BotPlayer(Color.GREEN));
                    players.put(Color.BLUE, new BotPlayer(Color.BLUE));
                    players.put(Color.PURPLE, new BotPlayer(Color.PURPLE));
                }
            }
        }

        public GameSessionBuilder setBoard(Board board) {
            this.board = board;
            return this;
        }

        public GameSessionBuilder setPlayers(HashMap<Color, Player> players) {
            this.players = players;
            return this;
        }

        public GameSession build() {
            return new GameSession(this);
        }
    }

    private int playerCapacity;
    private String ID;
    private Board board;
    private HashMap<Color, Player> players = new HashMap<>(6);

    private GameSession(GameSessionBuilder builder) {
        this.ID = builder.ID;
        this.playerCapacity = builder.playerCapacity;
        this.board = builder.board;
        this.players = builder.players;
    }

    public void connectPlayer(Socket socket, ExecutorService pool) {
        for (Color c : Color.values()) {
            if (players.get(c) == null) {
                PlayerConnection player = new PlayerConnection("Player", c, socket);
                players.put(c, player);
                pool.submit(player);
                return;
            }
        }
        throw new IllegalStateException("Session is full");
    }

    /**
     * Generic getter
     * 
     * @return session id
     */
    public String getID() {
        return ID;
    }

    /**
     * generic getter
     * 
     * @return session player capacity
     */
    public int getPlayerCapacity() {
        return playerCapacity;
    }

    /**
     * generic getter
     * 
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    private Color getColor(PlayerConnection player) {
        for (Color c : Color.values()) {
            if (players.get(c).equals(player))
                return c;
        }
        return null;
    }

    private boolean isSessionFull() {
        for (Color c : Color.values()) {
            if (players.get(c) == null)
                return false;
        }
        return true;
    }

    public void tryStartGame() {
        if (!isSessionFull())
            return;
        notifyBoardChange();
        currentTurn = Color.values()[new Random().nextInt(6)];
        nextPlayerTurn();
    }

    public boolean leavePlayer(PlayerConnection player) {
        return players.remove(getColor(player)) != null;
    }

    public void notifyBoardChange() {
        for (Player p : players.values()) {
            if (!(p instanceof BotPlayer))
                p.notifyBoardChange(board);
        }
    }

    public boolean makeMove(PlayerConnection player, Move move) {
        boolean moved = board.move(move);
        if (!moved)
            return false;

        notifyBoardChange();
        nextPlayerTurn();
        return true;
    }

    private Color currentTurn;

    public Color getCurrentTurn() {
        return currentTurn;
    }

    public void nextPlayerTurn() {
        do {
            currentTurn = Color.values()[(currentTurn.ordinal() + 1) % 6];
        } while (players.get(currentTurn) == null);

        players.get(currentTurn).notifyTurn();
    }
}
