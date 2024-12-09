package com.tp;

import java.util.ArrayList;
import java.util.HashMap;

import com.tp.exception.ColorOccupiedException;

public class GameSession {

    GameSession(String ID, int playerCount){
        this.ID = ID;
        this.playerCapacity = playerCount;
        this.board = new Board();
        switch(playerCount){
            case 2:
                players.put(Color.RED, null);
                players.put(Color.ORANGE, Player.MOCK_PLAYER);
                players.put(Color.YELLOW, Player.MOCK_PLAYER);
                players.put(Color.GREEN, null);
                players.put(Color.BLUE, Player.MOCK_PLAYER);
                players.put(Color.PURPLE, Player.MOCK_PLAYER);
                break;
            case 3:
                players.put(Color.RED, null);
                players.put(Color.ORANGE, Player.MOCK_PLAYER);
                players.put(Color.YELLOW, null);
                players.put(Color.GREEN, Player.MOCK_PLAYER);
                players.put(Color.BLUE, null);
                players.put(Color.PURPLE, Player.MOCK_PLAYER);
                break;
            case 4:
                players.put(Color.RED, null);
                players.put(Color.ORANGE, null);
                players.put(Color.YELLOW, Player.MOCK_PLAYER);
                players.put(Color.GREEN, null);
                players.put(Color.BLUE, null);
                players.put(Color.PURPLE, Player.MOCK_PLAYER);
                break;
            case 6:
                players.put(Color.RED, null);
                players.put(Color.ORANGE, null);
                players.put(Color.YELLOW, null);
                players.put(Color.GREEN, null);
                players.put(Color.BLUE, null);
                players.put(Color.PURPLE, null);
                break;
        }
    }

    private int playerCapacity;
    private String ID;
    private Board board;
    private HashMap<Color,Player> players = new HashMap<>(6);

    public String getID(){
        

        return ID;
    }

    public String getSessionInfo(){
        String s = ID + " " + playerCapacity;
        for(Color c : Color.values()){
            s += " ";
            s += (players.get(c) == null ? "---" : players.get(c).getName() + "@" + players.get(c).getSocket());
        }
        return s;
    }
    public void joinPlayer(Player player, Color color) throws ColorOccupiedException {
        if(players.get(color) != null) throw new ColorOccupiedException();
        players.put(color, player);
    }


}
