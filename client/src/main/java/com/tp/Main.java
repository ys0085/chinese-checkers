package com.tp;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main implements IHandle {
    public static void main(String[] args)throws Exception  {
        try (var socket = new Socket("localhost", 54321)) {
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
