package com.tp;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 54321;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server: " + serverAddress + ":" + port);
            
            SharedBoard board = new SharedBoard();
            
            Client client = new Client(socket, board);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
