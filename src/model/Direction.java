package model;

public enum Direction {
    DOWN(0, 1), LEFT(-1, 0), UP(0, -1), RIGHT(1, 0);
    /**
     * Sets Direction
     * @param x
     * @param y 
     */
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
    public final int x, y;
}