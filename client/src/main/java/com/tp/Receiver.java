<<<<<<< Updated upstream
package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Receiver implements Runnable {
    private final Socket socket;

    public Receiver(Socket s) {
        this.socket = s;
    }

    
    /** Method handling server responses
     * @param command
     * @param parts
     */
    private void handle(String command, String[] parts) {

        switch (command) {
            case "MOVE" -> {
                if (parts.length == 5) {
                    boolean moved = UIApp.boardPanel.move(
                            new Move( new Position(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
                                    new Position(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]))));
                    System.out.println(moved ? ("Move applied: " + String.join(" ", parts)) : "Couldnt apply move");
                } else {
                    System.out.println("Invalid MOVE command format");
                }
            }
            case "YOURTURN" -> {
                Client.getInstance().setYourTurn(true);
            }
        }
    }
    @Override
    public void run(){
        try (Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            if (in.hasNextLine()) {
                String[] line = in.nextLine().toUpperCase().split(" ");
                if(!line[0].equals("HELLO")) {
                    throw new WrongColorException();
                }
            }
            

            while (in.hasNextLine()) {
                String message = in.nextLine();
                String[] parts = message.split(" ");
                handle(parts[0], parts);
            }

        } catch (IOException e) {
            System.out.println("Receiver thread terminated.");
        } catch (WrongColorException e) {
            System.out.println("Wrong color. Reciever thread terminated.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Closed connection: " + socket);
        }
    }
}
=======
package com.tp;

// Required imports
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import javax.management.RuntimeErrorException;

// Class Receiver implements Runnable to allow it to be executed in a separate thread
public class Receiver implements Runnable {
    private final Socket socket; // Socket for communication with the server

    // Constructor initializes the receiver with a given socket
    public Receiver(Socket s) {
        this.socket = s;
    }

    /**
     * Method handling server responses based on the command received
     * 
     * @param command The command indicating the type of message received
     * @param parts   The parts of the message following the command
     */
    private void handle(String command, String[] parts) {
        // Switch statement to process different commands from the server
        switch (command) {
            case "YOURTURN" -> {
                // Set the player's turn status to true
                Client.getInstance().setYourTurn(true);
            }

            case "BOARD" -> {
                // Update the game board with the new state received from the server
                UIApp.boardPanel.setBoard(new Board(parts));
            }

            case "YOURCOLOR" -> {
                // Set the client's color based on the server response
                Client.getInstance().setColor(parts[0]);
            }

            case "PLAYERS" -> {
                // TODO: Implement functionality if the list of players is needed
            }

            case "GAMEOVER" -> {
                // TODO: Implement functionality to display a game over message
            }
        }
    }

    // The run method that will be executed when the thread starts
    @Override
    public void run() {
        try (
                // Scanner for reading input from the socket
                Scanner in = new Scanner(socket.getInputStream());
                // PrintWriter for sending output to the socket
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Wait for the initial server message
            while (!in.hasNextLine())
                ;

            // Process the first line and expect it to be "HELLO" followed by color
            String[] line = in.nextLine().toUpperCase().split(" ");
            if (!line[0].equals("HELLO")) {
                throw new RuntimeErrorException(null, "Did not receive HELLO message");
            }
            // Set the client's color based on the server's response
            Client.getInstance().setColor(line[1]);

            // Continuously read messages from the server
            while (in.hasNextLine()) {
                // Read the next line of message
                String message = in.nextLine();
                // Split the message into command and parts
                String[] parts = message.split(" ");
                // Handle the command with its corresponding parts
                handle(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
            }

        } catch (IOException e) {
            // Handle exception when IO operations fail
            System.out.println("Receiver thread terminated.");
        } finally {
            // Ensure the socket is closed when done
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Log the closure of the socket connection
            System.out.println("Closed connection: " + socket);
        }
    }
}
>>>>>>> Stashed changes
