package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Receiver implements Runnable {
    private final Socket socket;

    public Receiver(Socket s) {
        this.socket = s;
    }

    private void handle(String command, String[] parts) {

        switch (command) {
            case "HELLO" -> System.out.println("Server acknowledged " + parts[1] + " as " + parts[2]);
            case "MOVE" -> {
                if (parts.length == 5) {
                    boolean moved = Client.getInstance().getBoard().move(
                            new Move( new Position(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
                                    new Position(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]))));
                    System.out.println(moved ? ("Move applied: " + String.join(" ", parts)) : "Couldnt apply move");
                } else {
                    System.out.println("Invalid MOVE command format");
                }
            }
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
