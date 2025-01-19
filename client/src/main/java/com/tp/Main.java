package com.tp;

import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        App.main(args);
        String serverAddress = "localhost";
        int port = 54321;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server: " + serverAddress + ":" + port);
            
            @SuppressWarnings("unused")
            Board board = new Board();
            
            Client client = new Client(socket);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
