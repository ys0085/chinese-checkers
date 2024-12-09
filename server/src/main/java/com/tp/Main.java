package com.tp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            port = 54321;
        }
        Server server = Server.getInstance();
        server.setPort(port);
        System.out.println("Launching server on port " + port);
        try {
            server.launch();
        } catch (IOException e) {
            System.out.println("Unexpected error while launching: ");
            e.printStackTrace();
        }
    }
}