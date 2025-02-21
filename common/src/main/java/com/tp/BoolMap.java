package com.tp;

import java.util.HashMap;
import java.util.Map;

public class BoolMap {
    private Map<Position, Boolean> boolMap = new HashMap<>();

    public boolean getValue(int x, int y) {
        return boolMap.getOrDefault(new Position(x, y), false); // Default to false
    }

    public void setValue(int x, int y, boolean value) {
        boolMap.put(new Position(x, y), value);
    }
}
