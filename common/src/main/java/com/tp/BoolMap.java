package com.tp;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class BoolMap {
    private Map<Point, Boolean> boolMap = new HashMap<>();

    public boolean getValue(int x, int y) {
        return boolMap.getOrDefault(new Point(x, y), false); // Default to false
    }

    public void setValue(int x, int y, boolean value) {
        boolMap.put(new Point(x, y), value);
    }
}
