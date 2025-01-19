package com.tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    //Singleton pattern using double-checked locking

    private static Server instance = null;

    private Server(){}

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
    

}
