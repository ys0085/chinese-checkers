package com.tp;

import java.util.HashMap;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class GameSession {
    // The variant of the game being played (e.g., rules, type).
    public Variant variant;

    // The color representing the current player's turn.
    private Color currentTurn = Color.RED;

    // Maximum number of players allowed in this game session.
    private int playerCapacity;

    // Unique identifier for this game session.
    private String ID;

    // The game board for this session.
    private Board board;

    // A hashmap to track the players in the session, keyed by their color.
    private HashMap<Color, Player> players = new HashMap<>(6);

    // Constructor to initialize the game session with an ID, player capacity, and
    // variant.
    public GameSession(String ID, int playerCapacity, Variant variant) {
        this.variant = variant; // Set the game variant.
        this.ID = ID; // Set the session ID.
        this.playerCapacity = playerCapacity; // Set the maximum player capacity.
        this.board = new Board(variant); // Initialize the board based on the variant.

        System.out.println("Session created with ID: " + ID);
        System.out.println("Player capacity: " + playerCapacity);

        // Add bot players based on the specified player capacity.
        switch (playerCapacity) {
            case 2 -> {
                players.put(Color.RED, new BotPlayer(Color.RED));
                players.put(Color.GREEN, new BotPlayer(Color.GREEN));
            }
            case 3 -> {
                players.put(Color.RED, new BotPlayer(Color.RED));
                players.put(Color.ORANGE, new BotPlayer(Color.ORANGE));
                players.put(Color.BLUE, new BotPlayer(Color.BLUE));
            }
            case 4 -> {
                players.put(Color.RED, new BotPlayer(Color.RED));
                players.put(Color.YELLOW, new BotPlayer(Color.YELLOW));
                players.put(Color.GREEN, new BotPlayer(Color.GREEN));
                players.put(Color.BLUE, new BotPlayer(Color.BLUE));
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

    // Connects a player to the game session using a socket and an executor service.
    public void connectPlayer(Socket socket, ExecutorService pool) {
        for (Color c : players.keySet()) {
            // Check for unoccupied BotPlayer positions to replace with a real player.
            if (players.get(c) instanceof BotPlayer) {
                // Create a new player connection using the provided socket.
                PlayerConnection player = new PlayerConnection("Player", c, socket);
                players.put(c, player); // Replace the bot player with the human player.
                pool.submit(player); // Submit the player connection to the executor service.
                tryStartGame(); // Attempt to start the game if conditions are met.
                return; // Exit the method after connecting a player.
            }
        }
        // If no available positions were found, throw an exception indicating the
        // session is full.
        throw new IllegalStateException("Session is full");
    }

    /**
     * Generic getter method to return the session ID.
     * 
     * @return session ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Generic getter method to return the player capacity of the session.
     * 
     * @return session player capacity
     */
    public int getPlayerCapacity() {
        return playerCapacity;
    }

    /**
     * Generic getter method to return the game board of the session.
     * 
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    // Returns the variant of the game.
    public Variant getVariant() {
        return variant;
    }

    // Checks if all players have joined the game session before starting.
    private boolean areAllPlayersJoined() {
        // TODO: Implement condition to check if all players are joined.
        return true; // Default to true for placeholder.
    }

    // Attempts to start the game if all players have joined.
    public void tryStartGame() {
        if (!areAllPlayersJoined())
            return; // Exit if not all players are present.
        System.out.println("Game begun"); // Log game start.
        notifyBoardChange(); // Notify all players of the board state.
        nextPlayerTurn(); // Proceed to the next player's turn.
    }

    // Allows a player to leave the game session.
    public boolean leavePlayer(Color c) {
        Player p = players.remove(c); // Remove the player from the session.
        if (p == null)
            return false; // Return false if no player was found.

        // Replace the player with a new BotPlayer of the same color.
        players.put(c, new BotPlayer(c));
        return !(p instanceof BotPlayer); // Return true if the leaving player was not a bot.
    }

    // Notifies all players of changes on the game board.
    public void notifyBoardChange() {
        for (Player p : players.values()) {
            if (!(p instanceof BotPlayer)) // Only notify non-bot players.
                p.notifyBoardChange(board); // Notify the player about the board state.
        }
    }

    // Processes a player's move on the game board.
    public boolean makeMove(Player player, Move move) {
        boolean moved = board.move(move); // Attempt to move on the board.

        // Throw an exception if the move is invalid.
        if (!moved)
            throw new IllegalStateException("Invalid move");

        notifyBoardChange(); // Notify players of the board change.
        nextPlayerTurn(); // Transition to the next player's turn.
        return true; // Return true indicating the move was successful.
    }

    // Gets the current player's turn.
    public Color getCurrentTurn() {
        return currentTurn; // Return the current turn color.
    }

    // Advances to the next player's turn.
    public void nextPlayerTurn() {
        do {
            // Cycle through the colors to find the next player.
            currentTurn = Color.values()[(currentTurn.ordinal() + 1) % 6];
        } while (players.get(currentTurn) == null); // Ignore empty/removed players.

        System.out.println("Current turn: " + currentTurn); // Log whose turn it is.
        players.get(currentTurn).notifyTurn(); // Notify the current player it's their turn.
    }
}