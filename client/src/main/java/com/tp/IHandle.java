package com.tp;

public interface IHandle {
    public static String handle(String line){
        String tokens[] = line.split(" ");
        String command = tokens[0].toUpperCase();
        switch (command) {
            case "HELLO": //Syntax: "CREATEROOM name playerCount" name = String no spaces, playerCount is in {2,3,4,6}
                return line;
            default:
                return "Unexpected error occured";
        }
    }
}