/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

public class HighScore {

    private final String name;
    private final int score;
    /**
     * Sets Highscore
     * @param name
     * @param score 
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * getters and toString 
     * @return 
     */
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "HighScore{" + "name=" + name + ", score=" + score + '}';
    }

}
