<<<<<<< Updated upstream
package com.tp;

public class Position {
    public int x, y;
    Position(){};
    Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }    
}
=======
package com.tp;

public class Position {
    public int x, y;
    Position(){};
    Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }    
    
    /** Returns string of full position
     * @return String
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
>>>>>>> Stashed changes
