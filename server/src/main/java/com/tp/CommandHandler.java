package com.tp;

import com.tp.exception.ColorOccupiedException;
import com.tp.exception.NoSuchSessionException;
import com.tp.exception.PlayerAlreadyInSessionException;
import com.tp.exception.SessionExistsException;
import com.tp.exception.UnsupportedSessionSizeException;

public interface CommandHandler {
    public static String handle(String line, Player player){
        Server server = Server.getInstance();
        String tokens[] = line.split(" ");
        String command = tokens[0].toUpperCase();
        switch (command) {
            case "CREATEROOM": //Syntax: "CREATEROOM name playerCount" name = String no spaces, playerCount is in {2,3,4,6}
                try {
                    synchronized(server){
                        server.createSession(tokens[1], Integer.parseInt(tokens[2]));
                    }
                    return "OK";
                } catch (SessionExistsException e) {
                    return "ERR";
                } catch (UnsupportedSessionSizeException e) {
                    return "ERR";
                }
            case "JOINROOM": //Syntax: "JOINROOM name color" name = String no spaces, color = String from enum Color
                try{
                    return player.joinSession(tokens[1], tokens[2].toUpperCase()) ? "OK" : "ERR";
                } catch (NoSuchSessionException e) {
                    return "ERR"; // TODO: mozesz pozmieniac te errory na rozne polecenia np ERR1 ERR2 zeby inaczej klient reagowal
                } catch (ColorOccupiedException e) {
                    return "ERR";
                } catch (PlayerAlreadyInSessionException e) {
                    return "ERR";
                }
            case "GETROOMS": //Syntax: "GETROOMS"
                synchronized(server){
                    String output = "";
                    for(GameSession s : server.getSessionList()){
                        output += s.getSessionInfo() + "\n";
                    }
                    return output + "OK";
                }
            case "LEAVEROOM":
                return player.leaveSession() ? "OK" : "ERR";  
            case "MOVE":
                return player.getCurrentSession()
                    .makeMove(player, new Move(new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])),
                                                new Position(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]))))
                    ? "OK" 
                    : "ERR";
            default:
                return "ERR";
        }
    }
}
