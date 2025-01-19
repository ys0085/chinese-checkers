package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable {
    private final Socket socket;

    public Sender(Socket s) {
        this.socket = s;
        
    }

    private void handle(String command, String[] parts, PrintWriter out) {
        switch (command) {
            case "HELLO" -> {
                out.println("HELLO " + parts[1] + " " + parts[2]);
                System.out.println("Sent: HELLO " + parts[1] + " " + parts[2]);
            }
            case "MOVE" -> {
                if (parts.length == 5) {
                    out.println(String.join(" ", parts));
                    System.out.println("Sent MOVE: From " + parts[1] + ", " + parts[2] + " to " + parts[3] + ", " + parts[4]);
                } else {
                    System.out.println("Invalid MOVE format. Usage: MOVE <fromX> <fromY> <toX> <toY>");
                }
            }
            case "CREATEROOM" -> {
                if (parts.length == 3) {
                    out.println(String.join(" ", parts));
                    System.out.println("Sent CREATEROOM: Name=" + parts[1] + ", PlayerCount=" + parts[2]);
                } else {
                    System.out.println("Invalid CREATEROOM format. Usage: CREATEROOM <name> <playerCount>");
                }
            }
            case "JOINROOM" -> {
                if (parts.length == 3) {
                    out.println(String.join(" ", parts));
                    System.out.println("Sent JOINROOM: Name=" + parts[1] + ", Color=" + parts[2]);
                } else {
                    System.out.println("Invalid JOINROOM format. Usage: JOINROOM <name> <color>");
                }
            }
            case "GETROOMS" -> {
                out.println("GETROOMS");
                System.out.println("Sent: GETROOMS");
            }
            case "LEAVEROOM" -> {
                out.println("LEAVEROOM");
                System.out.println("Sent: LEAVEROOM");
            }
            default -> System.out.println("Unknown command: " + command);
        }
    }


    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Ready to send moves. Type DISCONNECT to exit.");

            while (true) {
                System.out.print("Enter command: ");
                String input = scanner.nextLine();
                String[] parts = input.split(" ");
                String command = parts[0].toUpperCase();

                if (command.equals("DISCONNECT")) {
                    out.println(command);
                    System.out.println("Disconnecting...");
                    break;
                }

                handle(command, parts, out);
            }
        } catch (IOException e) {
            System.out.println("Sender thread terminated.");
        }
    }
}