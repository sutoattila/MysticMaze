package model;

import java.util.ArrayList;

public class GameLevel {

    public final GameID gameID;
    public final int rows, cols;
    public final LevelItem[][] level;
    public Position player = new Position(0, 0);
    public ArrayList<Position> dragonPositions;
    public Position dragon = new Position(0, 0);
    public Position destination = new Position(0, 0);
    public int numSteps;
    
    /**
     * Sets GameLevel 
     * @param gameLevelRows
     * @param gameID 
     */
    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID) {
        this.dragonPositions = new ArrayList<>();
        this.gameID = gameID;
        int c = 0;
        for (String s : gameLevelRows) {
            if (s.length() > c) {
                c = s.length();//kiválasztjuk a leghosszabb sort
            }
        }
        rows = gameLevelRows.size(); //a pálya magassága
        cols = c;//a pálya szélessége
        level = new LevelItem[rows][cols];//mátrix inicializálása
        numSteps = 0;

        for (int i = 0; i < rows; i++) {
            String s = gameLevelRows.get(i);
            for (int j = 0; j < s.length(); j++) {
                switch (s.charAt(j)) {
                    case '&':
                        player = new Position(j, i);
                        //System.out.println("kezdőpozícó: " + j + ", " + i);
                        level[i][j] = LevelItem.EMPTY;
                        break;
                    case '#':
                        level[i][j] = LevelItem.WALL;
                        break;
                    case '.':
                        destination = new Position(j, i);
                        //System.out.println(i + " " + j);
                        level[i][j] = LevelItem.DESTINATION;
                        break;
                    case '$':
                        dragonPositions.add(new Position(j, i));
                        dragon = new Position(j, i);
                        level[i][j] = LevelItem.EMPTY;

                        break;
                    default:
                        level[i][j] = LevelItem.EMPTY;
                        break;
                }
            }
            for (int j = s.length(); j < cols; j++) {
                level[i][j] = LevelItem.EMPTY; //ha nem ugyan olyan hosszúak a sorok,
                //a pálya széleségénél rövidebb sorok végeit üres mezővel töltjük ki
            }
        }
    }
    /**
     * Sets GameLevel 
     * @param gl 
     */
    public GameLevel(GameLevel gl) {
        dragonPositions=gl.dragonPositions;
        gameID = gl.gameID;
        rows = gl.rows;
        cols = gl.cols;
        numSteps = gl.numSteps;
        level = new LevelItem[rows][cols];
        player = new Position(gl.player.x, gl.player.y);
        dragon = new Position(gl.dragon.x, gl.dragon.y);
        destination = new Position(gl.destination.x, gl.destination.y);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
    }
    /**
     * Decides wheter given position is in the bounds of map
     * @param p
     * @return 
     */
    public boolean isValidPosition(Position p) {
        return (p.x >= 0 && p.y >= 0 && p.x < cols && p.y < rows);
    }
    /**
     * Decides wheter given position is free
     * @param p
     * @return 
     */
    public boolean isFree(Position p) {
        if (!isValidPosition(p)) {
            return false;
        }
        LevelItem li = level[p.y][p.x];
        return (li == LevelItem.EMPTY || li == LevelItem.DESTINATION);
    }
    /**
     * Moves player to given direction
     * @param d
     * @return 
     */
    public boolean movePlayer(Direction d) {
        Position curr = player;
        Position next = curr.translate(d);
        //System.out.println(isFree(next));
        if (isFree(next)) {
            player = next;
            numSteps++;
            System.out.println("NumSteps:"+numSteps);
            return true;
        }
        return false;
    }
        /**
     * Moves dragon to given direction
     * @param d
     * @param dr
     * @return 
     */
    public boolean moveDragon(Direction d, Position dr) {
        Position curr = dr;
        int indexCurr = dragonPositions.indexOf(curr);
        Position next = curr.translate(d);
        if (isFree(next)) {
            dr = next;
            dragonPositions.set(indexCurr, dr);
            return true;
        }
        return false;
    }
    /**
     * getter for numSteps
     * @return 
     */
    public int getNumSteps() {
        return numSteps;
    }

}
