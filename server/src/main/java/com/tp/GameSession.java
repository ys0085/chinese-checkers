package com.tp;

import java.util.HashMap;
import java.util.Random;

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
                    players.put(Color.YELLOW, Player.MOCK_PLAYER);
                    players.put(Color.ORANGE, Player.MOCK_PLAYER);
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, Player.MOCK_PLAYER);
                    players.put(Color.PURPLE, Player.MOCK_PLAYER);
                }
                case 3 -> {
                    players.put(Color.RED, null);
                    players.put(Color.YELLOW, Player.MOCK_PLAYER);
                    players.put(Color.ORANGE, null);
                    players.put(Color.GREEN, Player.MOCK_PLAYER);
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, Player.MOCK_PLAYER);
                }
                case 4 -> {
                    players.put(Color.RED, null);
                    players.put(Color.YELLOW, null);
                    players.put(Color.ORANGE, Player.MOCK_PLAYER);
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, Player.MOCK_PLAYER);
                }
                case 6 -> {
                    players.put(Color.RED, null);
                    players.put(Color.YELLOW, null);
                    players.put(Color.ORANGE, null);
                    players.put(Color.GREEN, null);
                    players.put(Color.BLUE, null);
                    players.put(Color.PURPLE, null);
                }
            }
        }

        private Board board = new Board(Server.getInstance().getVariant());
        private HashMap<Color,Player> players = new HashMap<>(6);

        public GameSessionBuilder setBoard(Board board){
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
    private Board board;
    private HashMap<Color,Player> players = new HashMap<>(6);

    
    /** Generic getter
     * @return session id
     */
    public String getID(){
        return ID;
    }

    
    /** generic getter
     * @return session player capacity
     */
    public int getPlayerCapacity(){
        return playerCapacity;
    }

    /** generic getter
     * @return board 
     */
    public Board getBoard(){
        return board;
    }

    private Color getColor(Player player){
        for(Color c : Color.values()){
            if(players.get(c).equals(player)) return c;
        }
        return null;
    }

    public boolean joinPlayer(Player player, Color color) throws ColorOccupiedException, PlayerAlreadyInSessionException {
        if(players.get(color) != null) throw new ColorOccupiedException();
        if(players.containsValue(player)) throw new PlayerAlreadyInSessionException();
        players.put(color, player);
        return true;
    }

    private boolean isSessionFull(){
        for(Color c : Color.values()){
            if (players.get(c) == null) return false;
        }
        return true;
    }

    public void tryStartGame(){
        if(!isSessionFull()) return;
        currentTurn = Color.values()[new Random().nextInt(6)];
        nextPlayerTurn();
    }

    public boolean leavePlayer(Player player) {
        return players.remove(getColor(player)) != null;
    }

    public boolean makeMove(Player player, Move move){
        boolean moved = board.move(move);
        if(!moved) return false;
        
        for(Player p : players.values()){
            if(!p.equals(player) && !p.equals(Player.MOCK_PLAYER)) p.notifyMove(move);
        }
        nextPlayerTurn();
        return true;
    }

    private Color currentTurn;
    public Color getCurrentTurn(){
        return currentTurn;
    }
    public void nextPlayerTurn(){
        do{
            currentTurn = Color.values()[(currentTurn.ordinal() + 1) % 6];
        } while(players.get(currentTurn).equals(Player.MOCK_PLAYER));

        players.get(currentTurn).notifyTurn();
    }
}
