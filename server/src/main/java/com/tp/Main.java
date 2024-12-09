package com.tp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            port = 54321;
        }
        Server.getInstance().setPort(port);
        System.out.println("Launching server on port " + port);
        try {
            Server.getInstance().launch();
        } catch (IOException e) {
            System.out.println("Unexpected error while launching: ");
            e.printStackTrace();
        }
    }
}