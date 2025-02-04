package com.tp;

import java.util.HashMap;
import java.util.Map;

public class TileMap {
    private Map<Position, Tile> tileMap = new HashMap<>();

    public TileMap() {
    }

    public TileMap(String[] boardData) {
        for (int i = 0; i < boardData.length; i += 3) {
            int x = Integer.parseInt(boardData[i]);
            int y = Integer.parseInt(boardData[i + 1]);
            Tile tile = Tile.valueOf(boardData[i + 2]);
            tileMap.put(new Position(x, y), tile);
            System.out.println("Tile at " + x + ", " + y + " created to be " + tile);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Position p : tileMap.keySet()) {
            sb.append(p.x).append(" ").append(p.y).append(" ").append(tileMap.get(p)).append(" ");
        }
        return sb.toString();
    }

    public Tile getTile(int x, int y) {
        Position position = new Position(x, y);
        return tileMap.getOrDefault(position, Tile.INVALID);
    }

    public void setTile(int x, int y, Tile tile) {
        tileMap.put(new Position(x, y), tile);
    }

    public Map<Position, Tile> getTileMap() {
        return tileMap;
    }
}