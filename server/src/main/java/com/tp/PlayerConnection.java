package com.tp;

import java.io.IOException; // Importing IOException for handling input/output errors
import java.io.PrintWriter; // Importing PrintWriter to send messages to the player
import java.net.Socket; // Importing Socket for network communication
import java.util.Arrays; // Importing Arrays for operations on arrays
import java.util.List; // Importing List for handling collections of players
import java.util.Scanner; // Importing Scanner for reading input from the player

// Class representing a connection to a player, extending the Player class and implementing Runnable
public class PlayerConnection extends Player implements Runnable {
    private PrintWriter out; // PrintWriter for outputting messages to the player
    private Scanner in; // Scanner for reading messages from the player
    private final Socket socket; // Socket for the connection to the player

    // Constructor to initialize the player connection with name, color, and socket
    PlayerConnection(String name, Color color, Socket socket) {
        super(name, color); // Call to the parent constructor to initialize the player
        this.socket = socket; // Assigning the incoming socket to the class variable

        // Try block to initialize input and output streams for the socket
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true); // Output stream for sending messages
            this.in = new Scanner(socket.getInputStream()); // Input stream for receiving messages
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of an IOException
        }

        System.out.println("Connected to: " + socket); // Log the connection
        send("HELLO " + color); // Send a welcome message with the player's color
    }

    // Method to read a message from the player, convert it to uppercase, and split
    // it into parts
    String[] getMessage() {
        return in.nextLine().toUpperCase().split(" ");
    }

    /**
     * Method handling server responses based on the command received from the
     * player
     * 
     * @param command The command indicating the action to perform
     * @param parts   An array of parts following the command (command arguments)
     */
    private void handle(String command, String[] parts) {
        // Switch case to handle different commands
        switch (command) {
            case "MOVE" -> {
                // Create a Move object from the parsed positions
                Move move = new Move(
                        new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])),
                        new Position(Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                // Make the move using the server's session handler
                Server.getInstance().getSession().makeMove(this, move);
            }
        }
    }

    // Method that runs when the thread is started
    @Override
    public void run() {
        // Continue receiving messages while there are more lines
        while (in.hasNextLine()) {
            String message = in.nextLine(); // Read the incoming message
            String[] parts = message.split(" "); // Split the message into command and parts
            handle(parts[0], Arrays.copyOfRange(parts, 1, parts.length)); // Handle the command
        }

        // Close the socket connection when done
        try {
            socket.close(); // Attempt to close the socket connection
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if closing fails
        }
        System.out.println("Closed connection: " + socket); // Log the closure of the connection
    }

    // Method to send a message to the player
    public void send(String message) {
        out.println(message); // Write the message to the output stream
    }

    // Notify the player that it's their turn
    @Override
    void notifyTurn() {
        send("YOURTURN"); // Send a message indicating it's their turn to play
    }

    // Notify the player about changes to the player list in the game
    @Override
    void notifyPlayerListChange(List<Player> players) {
        send("PLAYERS " + players.size()); // Send the number of players
        for (Player player : players) {
            send(player.getName() + " " + player.getColor()); // Send each player's name and color
        }
    }

    // Notify the player of their assigned color
    void notifyColor() {
        send("YOURCOLOR " + color); // Send the color assigned to the player
    }

    // Notify the player of any changes to the game board
    @Override
    void notifyBoardChange(Board board) {
        send("BOARD " + board.toString()); // Send the current state of the board
        System.out.println("Sent board to " + name + " : " + board.toString()); // Log the sent board state
    }

    // Notify the player of the game's end and the winner
    @Override
    void notifyGameEnd(Color winner) {
        send("GAMEOVER " + winner); // Send the winner's color
    }
}