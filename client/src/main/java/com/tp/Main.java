<<<<<<< Updated upstream
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
            client.setColor(args[0]);
            client.setVariant(Variant.fromString(args[1]));
            client.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
=======
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
>>>>>>> Stashed changes
