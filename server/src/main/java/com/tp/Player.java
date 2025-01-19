package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Player implements Runnable {
    public static Player MOCK_PLAYER = new Player(null);

    private String defaultName = "default_name";
    private PrintWriter out;

    //private final Server server = Server.getInstance();
    private final Socket socket;
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
            out = new PrintWriter(socket.getOutputStream(), true);
            boolean registered = false;
            while(!registered){
                String color = "";
                if(in.hasNextLine()) {
                    String line[] = in.nextLine().toUpperCase().split(" ");
                    if(line[0].equals("HELLO") && line.length > 1){
                        name = line[1];
                        color = line[2];
                    }
                    else name = defaultName;
                }
                
                try {
                    Server.getInstance().getSession().joinPlayer(this, Color.valueOf(color));
                    out.println("HELLO " + name + " " + color);
                    out.println("OK");
                    registered = true;
                } catch (Exception e) {
                    out.println("ERR");
                }
        }
            
            
            
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


    public void notifyMove(Move move){
        out.println("MOVE " + move.from.x + " " + move.from.y + " " + move.to.x + " " + move.to.y);
    }

    public void notifyJoin(Player p, Color c){
        out.println("JOINED " + p.getName() + " " + c.name());
    }
}
