<<<<<<< Updated upstream
package com.tp;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {

    private Client(){
        yourTurn = false;
    }

    
    private static Client instance = null;
    /** Singleton design pattern
     * @return Client
     */
    @SuppressWarnings("DoubleCheckedLocking")
    public static Client getInstance(){
        if (instance == null){
            synchronized(Client.class){
                if(instance == null) instance = new Client();
            }
        }
        return instance;
    }


    private Socket socket;

    public void setSocket(Socket s){ socket = s; }

    private Color color = null;

    
    /** generic setter
     * @param color
     */
    public void setColor(String color) {
        System.out.println(color);
        this.color = Color.valueOf(color);

    }
    /** generic getter
     * @return color
     */
    public Color getColor(){
        System.out.println(color.toString());
        return color; 
    }
    
    /**
     * Client start
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        BlockingQueue<Move> uiActionQueue = new LinkedBlockingQueue<>();

        Thread senderThread = new Thread(new Sender(socket, uiActionQueue));
        Thread receiverThread = new Thread(new Receiver(socket));
        Thread uiThread = new Thread(new UIThread(uiActionQueue));

        uiThread.start();
        senderThread.start();
        receiverThread.start();

        uiThread.join();
        senderThread.join();
        receiverThread.join();
    }

    private boolean yourTurn;
    public boolean isYourTurn(){
        return yourTurn;
    }
    public void setYourTurn(boolean b){ 
        yourTurn = b; 
    }

    private Color winningColor;
    public Color getWinningColor(){ return winningColor; }
    public void checkWin(Board b){ winningColor = b.checkForWin(); }
    private Variant variant;
    public void setVariant(Variant setVar){
        System.out.println(setVar.toString());
        variant = setVar;
    }
    public Variant getVariant(){
        return variant;
    }
=======
package com.tp;

import java.net.Socket; // Importing the Socket class for network communication
import java.util.concurrent.BlockingQueue; // Importing BlockingQueue for thread-safe queue operations
import java.util.concurrent.LinkedBlockingQueue; // Importing LinkedBlockingQueue for queue implementation

public class Client {

    // Private constructor for singleton pattern
    private Client() {
        // Initialize yourTurn to false indicating it's not the player's turn initially
        yourTurn = false;
    }

    // Static instance variable for the singleton Client
    private static Client instance = null;

    /**
     * Singleton design pattern implementation.
     * Returns the single instance of the Client class.
     * 
     * @return Client instance
     */
    @SuppressWarnings("DoubleCheckedLocking") // Suppresses warnings about the double-checked locking idiom
    public static Client getInstance() {
        if (instance == null) { // If no instance exists
            synchronized (Client.class) { // Synchronize on the Client class
                if (instance == null)
                    instance = new Client(); // Create new instance if still null
            }
        }
        return instance; // Return the single instance
    }

    private Socket socket; // Socket for network communication

    // Method to set the socket
    public void setSocket(Socket s) {
        socket = s; // Assign the provided socket to the instance variable
    }

    private Color color = null; // Color representing some state of the client

    /**
     * Setter for the color property.
     * Converts the given string to a Color enum and updates the UI.
     * 
     * @param color Color name as a string
     */
    public void setColor(String color) {
        this.color = Color.valueOf(color); // Convert string to Color enum
        UIApp.updateLabel(); // Update the UI to reflect the changed color
    }

    /**
     * Getter for the color property.
     * 
     * @return current color of the client
     */
    public Color getColor() {
        System.out.println(color.toString()); // Print the color to the console
        return color; // Return the current color
    }

    /**
     * Method to start the client, initializing threads for sending and receiving
     * data.
     * 
     * @throws InterruptedException if thread execution is interrupted
     */
    public void start() throws InterruptedException {
        BlockingQueue<Move> uiActionQueue = new LinkedBlockingQueue<>(); // Initialize UI action queue

        // Create threads for sender, receiver, and UI handling
        Thread senderThread = new Thread(new Sender(socket, uiActionQueue));
        Thread receiverThread = new Thread(new Receiver(socket));
        Thread uiThread = new Thread(new UIThread(uiActionQueue));

        uiThread.start(); // Start the UI thread

        // Wait for the board panel to be initialized
        while (UIApp.boardPanel == null) {
            Thread.sleep(100); // Sleep for a short duration before checking again
        }

        senderThread.start(); // Start the sender thread
        receiverThread.start(); // Start the receiver thread

        // Wait for all threads to finish execution
        uiThread.join();
        senderThread.join();
        receiverThread.join();
    }

    private boolean yourTurn = false; // Indicates if it's the player's turn

    /**
     * Checks if it's the current user's turn.
     * 
     * @return true if it's the user's turn, otherwise false
     */
    public boolean isYourTurn() {
        return yourTurn; // Return the state of yourTurn
    }

    // Setter to update if it's the player's turn
    public void setYourTurn(boolean b) {
        yourTurn = b; // Update the yourTurn field
    }

    private Color winningColor; // Color of the winning player

    /**
     * Getter to retrieve the winning color.
     * 
     * @return Color of the winning player
     */
    public Color getWinningColor() {
        return winningColor; // Return the winning color
    }

    private Variant variant; // Variant representing game settings

    // Setter for the game variant
    public void setVariant(Variant setVar) {
        System.out.println(setVar.toString()); // Print the variant to the console
        variant = setVar; // Set the provided variant
    }

    // Getter for the game variant
    public Variant getVariant() {
        return variant; // Return the current variant
    }
>>>>>>> Stashed changes
}