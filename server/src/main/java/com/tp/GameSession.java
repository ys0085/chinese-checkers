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
                case 2:
                    players.put(PlayerColor.RED, null);
                    players.put(PlayerColor.ORANGE, Player.MOCK_PLAYER);
                    players.put(PlayerColor.YELLOW, Player.MOCK_PLAYER);
                    players.put(PlayerColor.GREEN, null);
                    players.put(PlayerColor.BLUE, Player.MOCK_PLAYER);
                    players.put(PlayerColor.PURPLE, Player.MOCK_PLAYER);
                    break;
                case 3:
                    players.put(PlayerColor.RED, null);
                    players.put(PlayerColor.ORANGE, Player.MOCK_PLAYER);
                    players.put(PlayerColor.YELLOW, null);
                    players.put(PlayerColor.GREEN, Player.MOCK_PLAYER);
                    players.put(PlayerColor.BLUE, null);
                    players.put(PlayerColor.PURPLE, Player.MOCK_PLAYER);
                    break;
                case 4:
                    players.put(PlayerColor.RED, null);
                    players.put(PlayerColor.ORANGE, null);
                    players.put(PlayerColor.YELLOW, Player.MOCK_PLAYER);
                    players.put(PlayerColor.GREEN, null);
                    players.put(PlayerColor.BLUE, null);
                    players.put(PlayerColor.PURPLE, Player.MOCK_PLAYER);
                    break;
                case 6:
                    players.put(PlayerColor.RED, null);
                    players.put(PlayerColor.ORANGE, null);
                    players.put(PlayerColor.YELLOW, null);
                    players.put(PlayerColor.GREEN, null);
                    players.put(PlayerColor.BLUE, null);
                    players.put(PlayerColor.PURPLE, null);
                    break;
            }
        }

        private SharedBoard board = new SharedBoard();
        private HashMap<PlayerColor,Player> players = new HashMap<>(6);

        public GameSessionBuilder setBoard(SharedBoard board){
            this.board = board;
            return this;
        }

        public GameSessionBuilder setPlayers(HashMap<PlayerColor,Player> players){
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
    private SharedBoard board;
    private HashMap<PlayerColor,Player> players = new HashMap<>(6);

    public String getID(){
        return ID;
    }

    public SharedBoard getBoard(){
        return board;
    }

    private PlayerColor getColor(Player player){
        for(PlayerColor c : PlayerColor.values()){
            if(players.get(c).equals(player)) return c;
        }
        return null;
    }

    public String getSessionInfo(){
        String s = ID + " " + playerCapacity;
        for(PlayerColor c : PlayerColor.values()){
            s += " ";
            s += (players.get(c) == null ? "---" : players.get(c).getName() + "@" + players.get(c).getSocket());
        }
        return s;
    }
    public boolean joinPlayer(Player player, PlayerColor color) throws ColorOccupiedException, PlayerAlreadyInSessionException {
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
            if(p != null) p.notifyMove(move);
        }
        return true;
    }

}
