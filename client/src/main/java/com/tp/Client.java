package com.tp;

import java.net.Socket;

public class Client {

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

    public void setRooms(String[] rooms){

    }

    //private String sessionID;


    private Socket socket;
    private Board board = new Board();


    public void setBoard(Board b){ this.board = b; }
    public Board getBoard(){ return board; }

    private Client(){}
    public Client(Socket s) {
        this.socket = s;
        instance = this;
    }

    public void start() throws InterruptedException {
        Thread senderThread = new Thread(new Sender(socket));
        Thread receiverThread = new Thread(new Receiver(socket));

        senderThread.start();
        receiverThread.start();

        senderThread.join();
        receiverThread.join();
    }
}
    

