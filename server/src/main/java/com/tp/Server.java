<<<<<<< Updated upstream
package com.tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    //Singleton pattern using double-checked locking

    private static Server instance = null;
    private Variant variant;
    private Server(){}

    
    /** Singleton design pattern
     * @return Server
     */
    @SuppressWarnings("DoubleCheckedLocking")
    public static Server getInstance(){
        if (instance == null){
            synchronized(Server.class){
                if(instance == null) instance = new Server();
            }
        }
        return instance;
    }

    private int port;
    private boolean running;

    
    /** generic setter
     * @param port
     */
    public void setPort(int port){
        if(!running) this.port = port;
    }

    void launch() throws IOException{
        try(ServerSocket listener = new ServerSocket(port)){
            System.out.println("Server is running on port " + port);
            ExecutorService pool = Executors.newFixedThreadPool(32);
            running = true;
            while(running){
                pool.execute(new Player(listener.accept()));
            }
        }
    }

    private GameSession session;
    public void createSession(String capacity, String mode){
        this.session = new GameSession.GameSessionBuilder("Room", Integer.parseInt(capacity)).build();
    }

    public GameSession getSession(){
        return session;
    } 
    public void setVariant(Variant setVar){
        variant = setVar;
    }
    public Variant getVariant(){
        return variant;
    }

}
=======
package com.tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // Singleton pattern variable; only one instance of Server should exist
    private static Server instance = null;
    private Variant variant; // Instance of the Variant class to define the game variant

    // Private constructor to prevent instantiation from outside the class
    private Server() {
    }

    /**
     * Method to get the single instance of the Server class (Singleton pattern)
     * 
     * @return Server - the single instance of Server
     */
    @SuppressWarnings("DoubleCheckedLocking") // Suppression for potential issues with double-checked locking
    public static Server getInstance() {
        if (instance == null) { // First check (no synchronization)
            synchronized (Server.class) { // Synchronize on the Server class
                if (instance == null) // Second check (with synchronization)
                    instance = new Server(); // Create the instance if it does not exist
            }
        }
        return instance; // Return the single instance of the Server
    }

    private int port; // Port number on which the server listens for connections
    private boolean running; // Flag indicating if the server is currently running

    /**
     * Generic setter for the port number
     * 
     * @param port - the port number to set
     */
    public void setPort(int port) {
        // Set port only if the server is not currently running
        if (!running)
            this.port = port;
    }

    /**
     * Launch the server which listens for incoming connections
     * 
     * @throws IOException - if an I/O error occurs when opening the socket
     */
    void launch() throws IOException {
        try (ServerSocket listener = new ServerSocket(port)) { // Create server socket to listen on the specified port
            System.out.println("Server is running on port " + port); // Notify that the server is running
            ExecutorService pool = Executors.newFixedThreadPool(32); // Create a pool of threads for handling client
                                                                     // sessions
            running = true; // Set the server status to running

            // Continuously accept new connections until running is set to false
            while (running) {
                Socket socket = listener.accept(); // Accept a new client connection
                try {
                    session.connectPlayer(socket, pool); // Handle player connection in the game session
                } catch (Exception e) {
                    System.out.println(e.getMessage()); // Print any exceptions during session handling
                }
            }
        }
    }

    private GameSession session; // Instance of the GameSession class to manage game sessions

    /**
     * Create a new game session
     * 
     * @param capacity - the maximum number of players in the session
     * @param mode     - the variant mode of the game
     */
    public void createSession(String capacity, String mode) {
        // Create a new GameSession with specified room name, capacity, and variant
        this.session = new GameSession("Room", Integer.parseInt(capacity), variant);
    }

    // Getter method for retrieving the current GameSession
    public GameSession getSession() {
        return session;
    }

    // Setter for the game variant
    public void setVariant(Variant setVar) {
        variant = setVar;
    }

    // Getter for the game variant
    public Variant getVariant() {
        return variant;
    }
}
>>>>>>> Stashed changes
