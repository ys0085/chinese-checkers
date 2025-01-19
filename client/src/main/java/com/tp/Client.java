package com.tp;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements IHandle {
    private Board board;

    private static Client instance = null;
    @SuppressWarnings("DoubleCheckedLocking")
    public static Client getInstance(){
        if (instance == null){
            synchronized(Client.class){
                if(instance == null) instance = new Client();
            }
        }
        return instance;
    }

    public void setBoard(Board b){ this.board = b; }
    public Board getBoard(){ return board; }

    public static void main(String[] args) throws Exception {

        App.main(args);
        
        String hostname = "localhost";
        int port = 54321;
        if(args.length > 0){
            hostname = args[0].split(":")[0];
            port = Integer.parseInt(args[0].split(":")[1]);
        }
        
        try (var socket = new Socket(hostname, port)) {
            System.out.println("Enter lines of text then Ctrl+D or Ctrl+C to quit");
            var scanner = new Scanner(System.in);
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                while(in.hasNextLine()) {
                    String line = in.nextLine();
                    System.out.println(line);
                    System.out.println(IHandle.handle(line));
                } 
            }
        }

        


    }
}
