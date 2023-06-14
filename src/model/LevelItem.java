package model;

public enum LevelItem {
    DRAGON('$'), DESTINATION('.'), WALL('#'), EMPTY(' ');
    /**
     * Sets LevelItem
     * @param rep 
     */
    LevelItem(char rep) {
        representation = rep;
    }
    public final char representation;
}
