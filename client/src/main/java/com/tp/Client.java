package com.tp;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {

    private Client(){}

    
    private static Client instance = null;
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

    private Board board = new Board();

    public void setBoard(Board b){ this.board = b; }
    public Board getBoard(){ return board; }

    private Color color = null;

    public void setColor(String color) {
        System.out.println(color.toString());
        this.color = Color.valueOf(color);

    }
    public Color getColor(){
        System.out.println(color.toString());
        return color; }
    

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

}
    

