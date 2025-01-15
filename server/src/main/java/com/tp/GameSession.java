package com.tp;

import java.util.HashMap;

import com.tp.exception.ColorOccupiedException;
import com.tp.exception.PlayerAlreadyInSessionException;

public class GameSession {

    //Builder design pattern

    public static class GameSessionBuilder {
        private int playerCapacity;
        private String ID;

        GameSessionBuilder(String ID, int playerCapacity){
            this.ID = ID;
            this.playerCapacity = playerCapacity;
            switch(playerCapacity){
                case 2 -> {
                    players.put(Color.RED, null);
                    players.put(Color.ORANGE, Player.MOCK_PLAYER);
                    players.put(Color.YELLOW, Player.MOCK_PLAYER);
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, Player.MOCK_PLAYER);
                    players.put(Color.PURPLE, Player.MOCK_PLAYER);
                }
                case 3 -> {
                    players.put(Color.RED, null);
                    players.put(Color.ORANGE, Player.MOCK_PLAYER);
                    players.put(Color.YELLOW, null);
                    players.put(Color.GREEN, Player.MOCK_PLAYER);
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, Player.MOCK_PLAYER);
                }
                case 4 -> {
                    players.put(Color.RED, null);
                    players.put(Color.ORANGE, null);
                    players.put(Color.YELLOW, Player.MOCK_PLAYER);
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, Player.MOCK_PLAYER);
                }
                case 6 -> {
                    players.put(Color.RED, null);
                    players.put(Color.ORANGE, null);
                    players.put(Color.YELLOW, null);
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, null);
                }
            }
        }

        private IBoard board;
        private HashMap<Color,Player> players = new HashMap<>(6);

        public GameSessionBuilder setBoard(IBoard board){
            this.board = board;
            return this;
        }

        public GameSessionBuilder setPlayers(HashMap<Color,Player> players){
            this.players = players;
            return this;
        }

        public GameSession build(){ return new GameSession(this); }
    }

    private GameSession(GameSessionBuilder builder){
        this.ID = builder.ID;
        this.playerCapacity = builder.playerCapacity;
        this.board = builder.board;
        this.players = builder.players;
    }

    private int playerCapacity;
    private String ID;
    private IBoard board;
    private HashMap<Color,Player> players = new HashMap<>(6);

    public String getID(){
        return ID;
    }

    public IBoard getBoard(){
        return board;
    }

    private Color getColor(Player player){
        for(Color c : Color.values()){
            if(players.get(c).equals(player)) return c;
        }
        return null;
    }

    public String getSessionInfo(){
        String s = ID + " " + playerCapacity;
        for(Color c : Color.values()){
            s += " ";
            s += (players.get(c) == null ? "---" : players.get(c).getName() + "@" + players.get(c).getSocket());
        }
        return s;
    }
    public boolean joinPlayer(Player player, Color color) throws ColorOccupiedException, PlayerAlreadyInSessionException {
        if(players.get(color) != null) throw new ColorOccupiedException();
        if(players.containsValue(player)) throw new PlayerAlreadyInSessionException();
        players.put(color, player);
        return true;
    }

    public boolean leavePlayer(Player player) {
        return players.remove(getColor(player)) != null;
    }

    public boolean makeMove(Player player, Move move){
        boolean moved = board.move(move);
        if(!moved) return false;
        
        for(Player p : players.values()){
            if(!p.equals(player)) p.notifyMove(move);
        }
        return true;
    }

}
