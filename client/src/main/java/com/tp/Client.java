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
}
    

