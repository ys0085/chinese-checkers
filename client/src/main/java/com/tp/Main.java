package com.tp;

import java.net.Socket;

public class Main {
    
    /** Main class. Entry point of the client program.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        
        String serverAddress = "localhost";
        int port = 54321;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server: " + serverAddress + ":" + port);
            
            @SuppressWarnings("unused")
            Board board = new Board();

            Client client = Client.getInstance();
            client.setSocket(socket);
            client.setColor(args[0]);
            client.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
