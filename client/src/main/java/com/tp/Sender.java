<<<<<<< Updated upstream
package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable {
    private final Socket socket;
    private final SharedBoard board;

    public Sender(Client client) {
        this.socket = client.socket;
        this.board = client.board;
    }

    private void handle(String command, String[] parts, PrintWriter out) {
        switch (command) {
            case "HELLO":
                out.println("HELLO " + parts[1]);
                System.out.println("Sent: HELLO " + parts[1]);
                break;
            case "MOVE":
                if (parts.length == 5) {
                    out.println(String.join(" ", parts));
                    System.out.println("Sent MOVE: From " + parts[1] + ", " + parts[2] + " to " + parts[3] + ", " + parts[4]);
                } else {
                    System.out.println("Invalid MOVE format. Usage: MOVE <fromX> <fromY> <toX> <toY>");
                }
                break;
            case "CREATEROOM":
                if (parts.length == 3) {
                    out.println(String.join(" ", parts));
                    System.out.println("Sent CREATEROOM: Name=" + parts[1] + ", PlayerCount=" + parts[2]);
                } else {
                    System.out.println("Invalid CREATEROOM format. Usage: CREATEROOM <name> <playerCount>");
                }
                break;
            case "JOINROOM":
                if (parts.length == 3) {
                    out.println(String.join(" ", parts));
                    System.out.println("Sent JOINROOM: Name=" + parts[1] + ", Color=" + parts[2]);
                } else {
                    System.out.println("Invalid JOINROOM format. Usage: JOINROOM <name> <color>");
                }
                break;
            case "GETROOMS":
                out.println("GETROOMS");
                System.out.println("Sent: GETROOMS");
                break;
            case "LEAVEROOM":
                out.println("LEAVEROOM");
                System.out.println("Sent: LEAVEROOM");
                break;
            default:
                System.out.println("Unknown command: " + command);
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
=======
package com.tp;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
    private final Socket socket;
    private BlockingQueue<Move> uiActionQueue;
    
    /**
     * Main constructor
     * @param s
     * @param uiActionQueue
     */
    public Sender(Socket s, BlockingQueue<Move> uiActionQueue) {
        this.socket = s;
        this.uiActionQueue = uiActionQueue;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            out.println("HELLO " + socket.getLocalPort() + " " + Client.getInstance().getColor().toString());
            
            while (true) {
                Move move = this.uiActionQueue.take();
                out.println(String.join(" ", "MOVE", move.from.x + "", move.from.y + "", move.to.x + "", move.to.y + ""));
            }
        } catch (Exception e) {
            System.out.println("Sender thread terminated.");
        }
    }

    
}
>>>>>>> Stashed changes
