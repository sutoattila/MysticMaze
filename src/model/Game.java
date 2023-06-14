package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
/*import persistence.Database;
import persistence.HighScore;*/
import res.ResourceLoader;

public class Game {

    private final HashMap<String, HashMap<Integer, GameLevel>> gameLevels;
    public GameLevel gameLevel = null;
    //public final Database database;
    public String playerName;
    
    /**
     * Sets Game
     */
    public Game() {
        gameLevels = new HashMap<>();
        //database = new Database();
        readLevels();
    }
    
    /**
     * Loads game with given GameId
     * @param gameID 
     */
    public void loadGame(GameID gameID) {
        gameLevel = new GameLevel(gameLevels.get(gameID.difficulty).get(gameID.level));
    }
    
    /**
     * Moves player to given direction
     * @param d
     * @return 
     */
    //public void printGameLevel(){ gameLevel.printLevel(); }
    public boolean step(Direction d) {
        return (gameLevel.movePlayer(d));
    }
    /**
     * Moves dragon to given direction
     * @param d
     * @return 
     */
    public boolean dragonStep(Direction d, Position dragon) {
        return (gameLevel.moveDragon(d,dragon));
    }
    
    /**
     * Decide wheter the dragon have found the player (is game over)
     * @return 
     */
    public boolean gameOver() {
        for (Position dragon  : gameLevel.dragonPositions) {
            if (Math.abs(dragon.x - gameLevel.player.x) < 2 && Math.abs(dragon.y - gameLevel.player.y) < 2) {
                return true;
            } 
        }
            return false;
    }
    public boolean playerWon() {
        return (gameLevel.player.x==gameLevel.destination.x && gameLevel.player.y==gameLevel.destination.y && !gameOver());
    }

    // ------------------------------------------------------------------------
    // Getter methods
    // ------------------------------------------------------------------------
    /*public ArrayList<HighScore> getHighScores() {
        return database.getHighScores();
    }*/
    public Collection<String> getDifficulties() {
        return gameLevels.keySet();
    }

    public Collection<Integer> getLevelsOfDifficulty(String difficulty) {
        if (!gameLevels.containsKey(difficulty)) {
            return null;
        }
        return gameLevels.get(difficulty).keySet();
    }

    public boolean isLevelLoaded() {
        return gameLevel != null;
    }

    public int getLevelRows() {
        return gameLevel.rows;
    }

    public int getLevelCols() {
        return gameLevel.cols;
    }

    public LevelItem getItem(int row, int col) {
        return gameLevel.level[row][col];
    }

    public GameID getGameID() {
        return (gameLevel != null) ? gameLevel.gameID : null;
    }

    public int getNumSteps() {
        return (gameLevel != null) ? gameLevel.getNumSteps() : 0;
    }

    public Position getPlayerPos() { // MAKE IT ~IMMUTABLE
        return new Position(gameLevel.player.x, gameLevel.player.y);
    }

    public Position getDragonPos() { // MAKE IT ~IMMUTABLE
        return new Position(gameLevel.dragon.x, gameLevel.dragon.y);
    }
    public ArrayList<Position> getdragonPositions() { // MAKE IT ~IMMUTABLE
        return gameLevel.dragonPositions;
    }

    public Position getDestinationPos() { // MAKE IT ~IMMUTABLE
        return new Position(gameLevel.destination.x, gameLevel.destination.y);
    }

    // ------------------------------------------------------------------------
    // Utility methods to load game levels from res/levels.txt resource file.
    // ------------------------------------------------------------------------
    private void readLevels() {
        //ClassLoader cl = getClass().getClassLoader();
        InputStream is;// = cl.getResourceAsStream("res/levels.txt");
        is = ResourceLoader.loadResource("res/levels.txt");
        System.out.println("----------------------");
        System.out.println("MAPS");
        try ( Scanner sc = new Scanner(is)) {
            String line = readNextLine(sc);
            ArrayList<String> gameLevelRows = new ArrayList<>();

            while (!line.isEmpty()) {
                GameID id = readGameID(line);
                if (id == null) {
                    return;
                }

                // System.out.println(id.difficulty + " " + id.id);
                gameLevelRows.clear();
                line = readNextLine(sc);
                while (!line.isEmpty() && line.trim().charAt(0) != ';') {
                    gameLevelRows.add(line);
                    line = readNextLine(sc);
                }
                addNewGameLevel(new GameLevel(gameLevelRows, id));
                System.out.println("");
                for (String gameLevelRow : gameLevelRows) {
                    System.out.println(gameLevelRow);
                }
                System.out.println("");
            }
            //if (is != null) is.close();
        } catch (Exception e) {
            System.out.println("Ajaj");
        }
        System.out.println("----------------------");
    }

    private void addNewGameLevel(GameLevel gameLevel) {
        HashMap<Integer, GameLevel> levelsOfDifficulty;
        if (gameLevels.containsKey(gameLevel.gameID.difficulty)) {
            levelsOfDifficulty = gameLevels.get(gameLevel.gameID.difficulty);
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
        } else {
            levelsOfDifficulty = new HashMap<>();
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
            gameLevels.put(gameLevel.gameID.difficulty, levelsOfDifficulty);
        }
    }

    private String readNextLine(Scanner sc) {
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()) {
            line = sc.nextLine();
        }
        return line;
    }

    private GameID readGameID(String line) {
        line = line.trim();
        if (line.isEmpty() || line.charAt(0) != ';') {
            return null;
        }
        Scanner s = new Scanner(line);
        s.next(); // skip ';'
        if (!s.hasNext()) {
            return null;
        }
        String difficulty = s.next().toUpperCase();
        if (!s.hasNextInt()) {
            return null;
        }
        int id = s.nextInt();
        return new GameID(difficulty, id);
    }
}
