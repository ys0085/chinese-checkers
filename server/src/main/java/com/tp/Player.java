package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.tp.exception.SessionExistsException;

public class Player implements Runnable{
    private Socket socket;

    Player(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Connected: " + socket);
        try {
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);
            while (in.hasNextLine()) {
                String tokens[] = in.nextLine().toUpperCase().split(" ");
                String command = tokens[0];
                if(command.equals("DISCONNECT")) break;
                switch (command) { // No need to check for 
                    case "CREATEROOM":
                        try {
                            Server.getInstance().createSession(tokens[1]);
                        } catch (SessionExistsException e) {

                        }
                        break;
                
                    default:
                        break;
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error:" + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }
    }

}
