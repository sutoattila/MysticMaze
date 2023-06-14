package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.Game;
import model.LevelItem;
import model.Position;
import res.ResourceLoader;

public class Board extends JPanel {

    private Game game;
    private final Image dragon, fog, destination, player, wall, empty, dead_player;
    private double scale;
    private int scaled_size;
    private final int tile_size = 40;
    
    /**
     * Sets up board
     * @param g
     * @throws IOException 
     */
    public Board(Game g) throws IOException {
        /*setBorder(BorderFactory.createMatteBorder(
                                   -1, -1, -1, -1, icon));*/
        game = g;
        scale = 2.0;
        scaled_size = (int) (scale * tile_size);
        dragon = ResourceLoader.loadImage("res/dragon2.png");
        fog = ResourceLoader.loadImage("res/fog.png");
        destination = ResourceLoader.loadImage("res/doors.png");
        player = ResourceLoader.loadImage("res/p2.png");
        wall = ResourceLoader.loadImage("res/wall.png");
        empty = ResourceLoader.loadImage("res/empty.png");
        dead_player = ResourceLoader.loadImage("res/player_dead2.png");
    }

    /**
     * Resizes the board
     * @param scale
     * @return 
     */
    public boolean setScale(double scale) {
        this.scale = scale;
        scaled_size = (int) (scale * tile_size);
        return refresh();
    }
    /**
     * Repaints the board with the set scale
     * @return 
     */
    public boolean refresh() {
        if (!game.isLevelLoaded()) {
            return false;
        }
        Dimension dim = new Dimension(game.getLevelCols() * scaled_size, game.getLevelRows() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }

    /**
     * Paints the tiles of map
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (!game.isLevelLoaded()) {
            return;
        }
        Graphics2D gr = (Graphics2D) g;
        int w = game.getLevelCols();
        int h = game.getLevelRows();
        Position p = game.getPlayerPos();
        //Position d = game.getDragonPos();
        ArrayList<Position> dragonpositions = game.getdragonPositions();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Image img = null;
                LevelItem li = game.getItem(y, x);
                switch (li) {
                    case DESTINATION:
                        img = destination;
                        break;
                    case WALL:
                        img = wall;
                        break;
                    case EMPTY:
                        img = empty;
                        break;
                }
                
                if (img == null) {
                    continue;
                }
                for (Position d : dragonpositions) {
                    if (d.x == x && d.y == y) {
                        img = dragon;
                    }
                }
                if (p.x == x && p.y == y && game.gameOver()) {
                    img = dead_player;
                }
                if (p.x == x && p.y == y && !game.gameOver()) {
                    img = player;
                }
                if (p.x == x && p.y == y && game.playerWon()) {
                    img = destination;
                }
                if (!(Math.abs(x - p.x) < 4 && Math.abs(y - p.y) < 4)) {
                    //KÖD!
                    //img=fog;
                }

                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
    }

}
