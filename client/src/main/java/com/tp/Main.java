package com.tp;

import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        
        String serverAddress = "localhost";
        int port = 54321;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server: " + serverAddress + ":" + port);
            
            @SuppressWarnings("unused")
            Board board = new Board();
            
            Thread uiThread = new Thread(new UIThread(args));
            uiThread.start();

<<<<<<< Updated upstream
            Client client = Client.getInstance();
            client.setSocket(socket);
            client.setColor(args[0]);
=======
            Client client = new Client(socket);
            client.setColor(args[0].toUpperCase());
>>>>>>> Stashed changes

            client.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
