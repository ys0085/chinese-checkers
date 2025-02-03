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
            System.out.println("Connected to server: " + serverAddress + ":" + port + " variant " + Variant.fromString(args[1]));
            Client client = Client.getInstance();
            client.setSocket(socket);
            if (args[0].toUpperCase().equals("REPLAY")){
                client.setColor("RED");
                client.setReplayMode(true);
                client.setReplayID(Integer.parseInt(args[1]));
            }
            else {
                client.setColor(args[0].toUpperCase());
                client.setVariant(Variant.fromString(args[1]));
            }
            client.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
