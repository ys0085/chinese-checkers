package com.tp;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
    private final Socket socket;
    private BlockingQueue<Move> uiActionQueue;

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

    
}