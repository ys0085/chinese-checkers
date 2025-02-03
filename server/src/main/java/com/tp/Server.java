package com.tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

public class Server {

    //Singleton pattern using double-checked locking

    private static Server instance = null;
    private Variant variant;
    private Server(){}

    @Autowired
    private ReplayRepository replayRepository;

    private ReplayService replayService = new ReplayService(replayRepository);
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

    void launch() throws IOException {
        
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
    public void createSession(String capacity){
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


    public void saveReplay(Replay r){
        replayService.saveReplay(r);
    }

}
