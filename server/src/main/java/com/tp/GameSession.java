package com.tp;

import java.util.ArrayList;

public class GameSession {

    GameSession(String ID){
        this.ID = ID;
        this.board = new Board();
    }

    private String ID;
    private Board board;
    private ArrayList<Player> players;

    public String getID(){
        return ID;
    }


}
