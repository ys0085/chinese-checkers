package com.tp;

public interface IHandle {
    public static String handle(String line){
        String tokens[] = line.split(" ");
        String command = tokens[0].toUpperCase();
        return switch (command) {
            case "HELLO" -> line;
            case "MOVED" -> line;
            default -> "Unexpected error occured";
        };
    }
}