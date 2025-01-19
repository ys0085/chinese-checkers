package com.tp;

public interface IHandle {
    public static String handle(String line){
        String tokens[] = line.split(" ");
        String command = tokens[0].toUpperCase();
        switch (command) {
            case "MOVED" -> {
                Client.getInstance().getBoard()
                .move(new Move(Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]), 
                Integer.parseInt(tokens[3]), 
                Integer.parseInt(tokens[4])));
            }
            default -> {
                
            }
        }
        return "";
    }
}