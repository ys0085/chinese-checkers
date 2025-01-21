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

            Client client = Client.getInstance();
            client.setSocket(socket);
            System.out.println(args[0]);
            client.setColor(args[0]);

            client.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        App.main(args);
    }
}
