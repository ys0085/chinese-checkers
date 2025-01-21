package com.tp;

import java.net.Socket;

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

    public void setColor(String color) { this.color = Color.valueOf(color); }
    public Color getColor(){ return color; }
    

    public void start() throws InterruptedException {
        Thread senderThread = new Thread(new Sender(socket));
        Thread receiverThread = new Thread(new Receiver(socket));

        senderThread.start();
        receiverThread.start();

        senderThread.join();
        receiverThread.join();

    }

}
    

