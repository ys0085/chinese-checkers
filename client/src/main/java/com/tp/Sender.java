<<<<<<< Updated upstream
package com.tp;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
    private final Socket socket;
    private BlockingQueue<Move> uiActionQueue;
    
    /**
     * Main constructor
     * @param s
     * @param uiActionQueue
     */
    public Sender(Socket s, BlockingQueue<Move> uiActionQueue) {
        this.socket = s;
        this.uiActionQueue = uiActionQueue;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            out.println("HELLO " + socket.getLocalPort() + " " + Client.getInstance().getColor().toString());
            
            while (true) {
                Move move = this.uiActionQueue.take();
                out.println(String.join(" ", "MOVE", move.from.x + "", move.from.y + "", move.to.x + "", move.to.y + ""));
            }
        } catch (Exception e) {
            System.out.println("Sender thread terminated.");
        }
    }

    
=======
package com.tp;

// Import necessary classes for the program
import java.io.PrintWriter; // For writing output to the socket
import java.net.Socket; // For handling socket connections
import java.util.concurrent.BlockingQueue; // For thread-safe queue management

// Sender class implements Runnable for concurrent thread execution
public class Sender implements Runnable {
    private final Socket socket; // Socket for communication
    private BlockingQueue<Move> uiActionQueue; // Queue to hold UI action commands

    /**
     * Main constructor
     * 
     * @param s             - Socket object for network communication
     * @param uiActionQueue - Queue to manage game move actions
     */
    public Sender(Socket s, BlockingQueue<Move> uiActionQueue) {
        this.socket = s; // Initialize the socket
        this.uiActionQueue = uiActionQueue; // Initialize the action queue
    }

    // The run method where the thread execution starts
    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Send a welcome message including the local port number
            out.println("HELLO " + socket.getLocalPort());

            // Continuously process moves from the UI action queue
            while (true) {
                // Retrieve and remove the head of the queue, waiting if necessary
                Move move = this.uiActionQueue.take();
                // Send the move information in a formatted string
                out.println(
                        String.join(" ", "MOVE", move.from.x + "", move.from.y + "", move.to.x + "", move.to.y + ""));
            }
        } catch (Exception e) {
            // Handle exceptions and print a closing message upon thread termination
            System.out.println("Sender thread terminated.");
        }
    }
>>>>>>> Stashed changes
}