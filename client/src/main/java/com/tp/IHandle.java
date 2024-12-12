package com.tp;

public interface IHandle {
    public static String handle(String line){
        String tokens[] = line.split(" ");
        String command = tokens[0].toUpperCase();
        switch (command) {
            case "HELLO":
                return line;
            case "MOVED":
                return line;
            default:
                return "Unexpected error occured";
        }
    }
}