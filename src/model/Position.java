package model;

public class Position {

    public int x, y;
    
    /**
     * Sets Position
     * @param x
     * @param y 
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the positions neighbour in given direction 
     * @param d
     * @return 
     */
    public Position translate(Direction d) {
        return new Position(x + d.x, y + d.y);
    }
}
