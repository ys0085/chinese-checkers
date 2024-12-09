package com.tp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tp.exception.SessionExistsException;

public class Server {

    //Singleton pattern using double-checked locking

    private static Server instance;

    private Server(){}

    public static Server getInstance(){
        if (instance == null){
            synchronized(Server.instance){
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

    private ArrayList<GameSession> sessions;

    public void createSession(String sessionID) throws SessionExistsException {
        for(GameSession s : sessions){
            if(s.getID().equals(sessionID)){
                throw new SessionExistsException();
            }
        }
        GameSession session = new GameSession(sessionID);
        
    };

}
