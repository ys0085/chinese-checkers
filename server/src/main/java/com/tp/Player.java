package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.tp.exception.ColorOccupiedException;
import com.tp.exception.NoSuchSessionException;
import com.tp.exception.PlayerAlreadyInSessionException;


public class Player implements Runnable {
    public static Player MOCK_PLAYER = new Player(null);

    private String defaultName = "default_name";
    private PrintWriter out;

    private Server server = Server.getInstance();
    private Socket socket;
    private String name;
    
    private GameSession currentSession;

    Player(Socket socket) {
        this.socket = socket;
        this.name = defaultName;
    }

    public String getName(){
        return name;
    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run() {
        try {
            var in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            if(in.hasNextLine()) {
                String line[] = in.nextLine().toUpperCase().split(" ");
                if(line[0].equals("HELLO") && line.length > 1){
                    name = line[1];
                }
                else name = defaultName;
            }
            out.println("HELLO " + name);
            out.println("OK");
            System.out.println("Connected: " + socket + " as \"" + name + "\"");

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if(line.toUpperCase().startsWith("DISCONNECT")) break;
                out.println(CommandHandler.handle(line, this));
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error: " + socket);
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }
    }

    public GameSession getCurrentSession(){
        return currentSession;
    }

    public boolean joinSession(String sessionID, String color) 
    throws NoSuchSessionException, ColorOccupiedException, PlayerAlreadyInSessionException {
        if(currentSession != null) throw new PlayerAlreadyInSessionException();
        ArrayList<GameSession> sessions = server.getSessionList();
        GameSession session = null;
        for(GameSession s : sessions){
            if(s.getID().equals(sessionID)) session = s;
        }
        if (session == null) throw new NoSuchSessionException();
        synchronized(session){
            if(session.joinPlayer(this, Color.valueOf(color))) currentSession = session;
        }
        return currentSession != null;
    }

    public boolean leaveSession(){
        if(currentSession.leavePlayer(this)){
            currentSession = null;
            return true;
        }
        else return false;
    }

    public void notifyMove(Move move){
        out.println("MOVE " + move.x1 + " " + move.y1 + " " + move.x2 + " " + move.y2);
    }
}
