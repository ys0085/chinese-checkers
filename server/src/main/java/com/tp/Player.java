package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.tp.exception.NoSuchSessionException;
import com.tp.exception.SessionExistsException;
import com.tp.exception.ColorOccupiedException;


public class Player implements Runnable {
    public static Player MOCK_PLAYER = new Player(null);

    private String defaultName = "default_name";

    private Server server = Server.getInstance();
    private Socket socket;
    private String name;
    
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
            var out = new PrintWriter(socket.getOutputStream(), true);

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
                String tokens[] = in.nextLine().split(" ");
                String command = tokens[0].toUpperCase();
                if(command.equals("DISCONNECT")) break;
                switch (command) { // No need to check if tokens[i] exists, assume thats done by the client
                    case "CREATEROOM": //Syntax: "CREATEROOM name playerCount" name = String no spaces, playerCount is in {2,3,4,6}
                        try {
                            synchronized(server){
                                server.createSession(tokens[1], Integer.parseInt(tokens[2]));
                            }
                            out.println("OK");
                        } catch (SessionExistsException e) {
                            out.println("ERR");
                        }
                        break;
                    case "JOINROOM": //Syntax: "JOINROOM name color" name = String no spaces, color = String from enum Color
                        try{
                            joinSession(tokens[1], tokens[2].toUpperCase());
                            out.println("OK");
                        } catch (NoSuchSessionException e) {
                            out.println("ERR");
                        } catch (ColorOccupiedException e) {
                            out.println("ERR");
                        }
                        break;
                    case "GETROOMS": //Syntax: "GETROOMS"
                        synchronized(server){
                            for(GameSession s : server.getSessionList()){
                                out.println(s.getSessionInfo());
                            }
                            out.println("OK");
                        }
                    default:
                        break;
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error:" + socket);
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }
    }

    private void joinSession(String sessionID, String color) throws NoSuchSessionException, ColorOccupiedException {
        ArrayList<GameSession> sessions = server.getSessionList();
        GameSession session = null;
        for(GameSession s : sessions){
            if(s.getID().equals(sessionID)) session = s;
        }
        if (session == null) throw new NoSuchSessionException();
        synchronized(session){
            session.joinPlayer(this, Color.valueOf(color));
        }
    }





}
