package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Receiver implements Runnable {
    private final Socket socket;
    private final SharedBoard board;

    public Receiver(Client client) {
        this.socket = client.socket;
        this.board = client.board;
    }

    private void handle(String command, String[] parts) {

        switch (command) {
            case "HELLO":
                System.out.println("Server says: HELLO");
                break;
            case "MOVE":
                if (parts.length == 5) {

                    board.move(new Move());
                    System.out.println("Move applied");
                } else {
                    System.out.println("Invalid MOVE command format");
                }
                break;
            case "OK":
                System.out.println("Server acknowledged: OK");
                break;
            case "ERR":
                System.out.println("An error occured");
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }
    
    @Override
    public void run() {
        try (Scanner in = new Scanner(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            if (in.hasNextLine()) {
                String[] line = in.nextLine().toUpperCase().split(" ");
                handle(line[0], line);
            }

            while (in.hasNextLine()) {
                String message = in.nextLine();
                String[] parts = message.split(" ");
                handle(parts[0], parts);
            }

        } catch (IOException e) {
            System.out.println("Receiver thread terminated.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Closed connection: " + socket);
        }
    }
}