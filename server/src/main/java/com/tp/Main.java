package com.tp;

import java.io.IOException;

public class Main {
    
    /** Entry point for Server program
     * @param args
     */
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = 54321;
        }
        Server server = Server.getInstance();
        server.setPort(port);
        server.createSession(args[1], args[2]);
        System.out.println("Launching server on port " + port);
        try {
            server.launch();
        } catch (IOException e) {
            System.out.println("Unexpected error while launching: \n" + e.getMessage());
            
        }
    }
}