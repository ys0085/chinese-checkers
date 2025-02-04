package com.tp;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@code Client} class represents a singleton client instance that handles
 * network communication and user interactions for a game.
 */
public class Client {

    /**
     * Private constructor for singleton pattern.
     * Initializes {@code yourTurn} to {@code false}, indicating it's not the player's turn initially.
     */
    private Client() {
        yourTurn = false;
    }

    /**
     * Static instance variable for the singleton {@code Client}.
     */
    private static Client instance = null;

    /**
     * Returns the single instance of the {@code Client} class, implementing the singleton design pattern.
     * Uses double-checked locking to ensure thread safety.
     *
     * @return the single instance of {@code Client}
     */
    @SuppressWarnings("DoubleCheckedLocking")
    public static Client getInstance() {
        if (instance == null) {
            synchronized (Client.class) {
                if (instance == null)
                    instance = new Client();
            }
        }
        return instance;
    }

    /**
     * Socket for network communication.
     */
    private Socket socket;

    /**
     * Sets the socket for network communication.
     *
     * @param s the socket to be assigned to the client
     */
    public void setSocket(Socket s) {
        socket = s;
    }

    /**
     * Color representing the current state of the client.
     */
    private Color color = null;

    /**
     * Sets the color property by converting the given string to a {@code Color} enum.
     * Updates the UI accordingly.
     *
     * @param color color name as a string
     */
    public void setColor(String color) {
        this.color = Color.valueOf(color);
        UIApp.updateLabel();
    }

    /**
     * Retrieves the current color of the client.
     *
     * @return the current color
     */
    public Color getColor() {
        System.out.println(color.toString());
        return color;
    }

    /**
     * Starts the client, initializing threads for sending and receiving data.
     *
     * @throws InterruptedException if thread execution is interrupted
     */
    public void start() throws InterruptedException {
        BlockingQueue<Move> uiActionQueue = new LinkedBlockingQueue<>();

        Thread senderThread = new Thread(new Sender(socket, uiActionQueue));
        Thread receiverThread = new Thread(new Receiver(socket));
        Thread uiThread = new Thread(new UIThread(uiActionQueue));

        uiThread.start();

        // Wait until the board panel is initialized
        while (UIApp.boardPanel == null) {
            Thread.sleep(100);
        }

        senderThread.start();
        receiverThread.start();

        // Wait for all threads to complete
        uiThread.join();
        senderThread.join();
        receiverThread.join();
    }

    /**
     * Indicates whether it's the player's turn.
     */
    private boolean yourTurn = false;

    /**
     * Checks if it is currently the player's turn.
     *
     * @return {@code true} if it's the player's turn, {@code false} otherwise
     */
    public boolean isYourTurn() {
        return yourTurn;
    }

    /**
     * Sets whether it is the player's turn.
     *
     * @param b {@code true} if it's the player's turn, {@code false} otherwise
     */
    public void setYourTurn(boolean b) {
        yourTurn = b;
    }

    /**
     * Color of the winning player.
     */
    private Color winningColor;

    /**
     * Retrieves the winning color.
     *
     * @return the color of the winning player
     */
    public Color getWinningColor() {
        return winningColor;
    }

    /**
     * Represents the game variant settings.
     */
    private Variant variant;

    /**
     * Sets the game variant and prints it to the console.
     *
     * @param setVar the game variant to set
     */
    public void setVariant(Variant setVar) {
        System.out.println(setVar.toString());
        variant = setVar;
    }

    /**
     * Retrieves the game variant.
     *
     * @return the current game variant
     */
    public Variant getVariant() {
        return variant;
    }
}
