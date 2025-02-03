package com.tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
                Socket socket = listener.accept();
                try
                {
                    session.connectPlayer(socket, pool);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
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
