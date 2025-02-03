package com.tp;

public interface CommandHandler {
    public static String handle(String line, PlayerConnection player){
        Server server = Server.getInstance();
        String tokens[] = line.split(" ");
        String command = tokens[0].toUpperCase();
        switch (command) {
            case "MOVE" -> {
                return server.getSession().makeMove(player,
                new Move(new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])),
                new Position(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])))) ? "OK" : "ERR";
            }
            default -> {
                return "ERR";
            }
        }
    }
}
