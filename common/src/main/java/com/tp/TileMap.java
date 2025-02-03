package com.tp;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class TileMap {
    private Map<Point, Tile> tileMap = new HashMap<>();

    public Tile getTile(int x, int y) {
        return tileMap.getOrDefault(new Point(x, y), null); // Return null if tile doesn't exist
    }

    public void setTile(int x, int y, Tile tile) {
        tileMap.put(new Point(x, y), tile);
    }

}