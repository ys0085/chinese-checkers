package com.tp;

import java.net.Socket;
/** Main class. Entry point of the client program.
     */
public class Main {
    
    /** Main class. Entry point of the client program.
     * @param args pass cmd arguments
     * @throws Exception exeption
     */
    public static void main(String[] args) throws Exception {
        
        String serverAddress = "localhost";
        int port = 54321;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server: " + serverAddress + ":" + port );
            Client client = Client.getInstance();
            client.setSocket(socket);
            client.setVariant(Variant.fromString(args[0]));
            client.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
