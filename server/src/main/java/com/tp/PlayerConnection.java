package com.tp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class PlayerConnection extends Player implements Runnable {
    private PrintWriter out;
    private final Socket socket;


    PlayerConnection(String name, Color color, Socket socket) {
        super(name, color);
        this.socket = socket;
    }

    @Override
    public void run() {
        notifyColor();
        try {
            var in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            boolean registered = false;
            while (!registered) {
                String colorstr = "";
                if (in.hasNextLine()) {
                    String line[] = in.nextLine().toUpperCase().split(" ");
                    if (line[0].equals("HELLO") && line.length > 1) {
                        name = line[1];
                        colorstr = line[2];
                    }
                }
                this.color = Color.valueOf(colorstr);
                out.println("HELLO " + name + " " + colorstr);
                out.println("OK");
                registered = true;
                Server.getInstance().getSession().tryStartGame();
            }

            System.out.println("Connected: " + socket + " as \"" + name + "\"");

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.toUpperCase().startsWith("DISCONNECT"))
                    break;
                out.println(CommandHandler.handle(line, this));
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error: " + socket);
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }
    }

    public void send(String message) {
        out.println(message);
    }

    @Override
    void notifyTurn()
    {
        send("YOURTURN");
    }

    @Override
    void notifyPlayerListChange(List<Player> players)
    {
        send("PLAYERS " + players.size());
        for (Player player : players)
        {
            send(player.getName() + " " + player.getColor());
        }
    }

    void notifyColor()
    {
        send("YOURCOLOR " + color);
    }

    @Override
    void notifyBoardChange(Board board)
    {
        send("BOARD " + board.toString());
    }

    @Override
    void notifyGameEnd(Color winner)
    {
        send("GAMEOVER " + winner);
    }
}
