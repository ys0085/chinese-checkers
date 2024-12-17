package com.tp;
import java.net.*;

public class Client {
    final Socket socket;
    final SharedBoard board;

    public Client(Socket socket, SharedBoard board) {
        this.socket = socket;
        this.board = board;
    }

    public void start() throws InterruptedException {
        Thread senderThread = new Thread(new Sender(this));
        Thread receiverThread = new Thread(new Receiver(this));

        senderThread.start();
        receiverThread.start();

        senderThread.join();
        receiverThread.join();
    }
}