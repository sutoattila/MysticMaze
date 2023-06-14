package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Direction;
import model.Game;
import model.GameID;
import model.Position;
import persistence.HighScores;

public class MainWindow extends JFrame {

    private MainMenu mm;
    public final Game game;
    public Board board;
    private final JLabel gameStatLabel;
    private JLabel elapsedTimeLabel;
    public Timer timer;
    public int finishedMaps;
    public HighScores highScores;
    public String playerName;
    private int min;
    private int sec;
    /**
     * Sets up MainWindow
     * @param finishedMaps
     * @param player
     * @throws IOException 
     */
    public MainWindow(int finishedMaps, String player) throws IOException {
        try {
            highScores = new HighScores(10);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.finishedMaps = finishedMaps;
        this.playerName = player;
        System.out.println("initializing...");
        System.out.println("----------------------");
        System.out.println("Player Name: "+this.playerName);
        game = new Game();

        setTitle("Mystic Maze");
        setSize(0, 0);

        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                backToMainMenu(finishedMaps);
            }
        });
        URL url = MainWindow.class.getClassLoader().getResource("res/icon2.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);
        
        JMenu menuGame = new JMenu("Játék");menuGame.setForeground(Color.WHITE);
        JMenu menuGameLevel = new JMenu("Pálya");
        JMenu menuGameScale = new JMenu("Pályaméret");
        createGameLevelMenuItems(menuGameLevel);
        createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Vissza a főmenübe") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                timer.stop();
                try {
                    new MainMenu(MainWindow.this.finishedMaps, MainWindow.this.playerName);
                } catch (IOException ex) {
                }
            }
        });

        menuGame.add(menuGameLevel);
        menuGame.add(menuGameScale);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout(0, 10));
        gameStatLabel = new JLabel("label");
        elapsedTimeLabel = new JLabel(" Eltelt idő: 0 perc 0mp");
        add(gameStatLabel, BorderLayout.NORTH);
        add(elapsedTimeLabel, BorderLayout.CENTER);
        try {
            add(board = new Board(game), BorderLayout.SOUTH);
        } catch (IOException ex) {
        }
                game.loadGame(new GameID("EASY", 1));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke);
                if (!game.isLevelLoaded()) {
                    return;
                }
                int kk = ke.getKeyCode();
                Direction d = null;
                /*switch (kk){
                    case KeyEvent.VK_A:  d = Direction.LEFT; break;
                    case KeyEvent.VK_D: d = Direction.RIGHT; break;
                    case KeyEvent.VK_W:    d = Direction.UP; break;
                    case KeyEvent.VK_S:  d = Direction.DOWN; break;
                    case KeyEvent.VK_ESCAPE: game.loadGame(game.getGameID());
                }*/
                switch (kk) {
                    case KeyEvent.VK_LEFT:
                        d = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        d = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        d = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        d = Direction.DOWN;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        game.loadGame(game.getGameID());
                        min=0;
                        sec=0;
                        break;
                }

                System.out.println(game.getNumSteps());
                board.repaint();
                if (d != null && game.step(d)) {
                    refreshGameStatLabel();
                    System.out.print("Player position : x: " + game.getPlayerPos().x);
                    System.out.println("  y: " + game.getPlayerPos().y);
                    System.out.print("Destination position x: " + game.getDestinationPos().x);
                    System.out.println("Destination position y: " + game.getDestinationPos().y);
                    if (game.gameOver()) {
                        onGameOver();
                    } else if (game.getPlayerPos().x == game.getDestinationPos().x && game.getPlayerPos().y == game.getDestinationPos().y) {
                        timer.stop();
                        JOptionPane.showMessageDialog(MainWindow.this, "Gratulálok! Nyertél!", "Gratulálok!", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Level: " + game.getGameID().level);
                        System.out.println("Difficulty: " + game.getGameID().difficulty);
                        String diff = "";
                        System.out.println("Finished maps: " + MainWindow.this.finishedMaps);
                        if (MainWindow.this.finishedMaps < 5) {
                            diff = "EASY";
                        } else if (MainWindow.this.finishedMaps >= 5 && MainWindow.this.finishedMaps < 10) {
                            diff = "MEDIUM";
                        } else if (MainWindow.this.finishedMaps >= 10) {
                            diff = "HARD";
                        }
                        if (game.getGameID().level == (MainWindow.this.finishedMaps % 5) + 1 && diff.equals(game.getGameID().difficulty)) {
                            backToMainMenu(MainWindow.this.finishedMaps + 1);
                        } else {
                            backToMainMenu(MainWindow.this.finishedMaps);
                        }

                    }

                }
            }
        });
        //Minden pillanatban iránytváltozató sárkány                
        /*
                timer = new Timer(1000, new ActionListener() {
            
            @Override
            
            public void actionPerformed(ActionEvent ae) {
                int Min = 1;
                int Max =4;
                int kk = Min + (int)(Math.random() * ((Max - Min) + 1));

                //System.out.println(kk);
                Direction d = null;

                    switch (kk){
                    case 1:  d = Direction.LEFT; break;
                    case 2: d = Direction.RIGHT; break;
                    case 3:    d = Direction.UP; break;
                    case 4:  d = Direction.DOWN; break;
                }
                if (game.dragonStep(d)){
                    System.out.println("dragon stepped"+" x: "+game.getDragonPos().x+" y: "+game.getDragonPos().y);
                }else{
                    if(!game.dragonStep(d)){
                        d=Direction.DOWN;
                        if(!game.dragonStep(d)){
                            d=Direction.UP;
                            if(!game.dragonStep(d)){
                                d=Direction.LEFT;
                                if(!game.dragonStep(d)){
                                    d=Direction.RIGHT;
                                    boolean b = game.dragonStep(d);
                                }
                            }
                        }
                        System.out.println("dragon stepped"+" x: "+game.getDragonPos().x+" y: "+game.getDragonPos().y);
                    }
                }
                board.repaint();
             if (game.gameOver()){
                        timer.stop();
                        
                        JOptionPane.showMessageDialog(MainWindow.this, "GAME OVER", "", JOptionPane.INFORMATION_MESSAGE);
                        
                    }   
            }
        });
         */
                                int Min = 1;
            int Max = 4;
        ArrayList<Integer> initialDirections = new ArrayList<>();               
            for (int j=0; j < game.gameLevel.dragonPositions.size(); j++) {
                    int kk = Min + (int) (Math.random() * ((Max - Min) + 1));
                    initialDirections.add(kk);
                }   
                    timer = new Timer(1000, new ActionListener() {//dragonspeed


            //int kk = Min + (int) (Math.random() * ((Max - Min) + 1));
                        @Override
                        public void actionPerformed(ActionEvent ae) {                
                            sec++;
                if(sec==60){
                    sec=0;
                    min++;
                }
                elapsedTimeLabel.setText(" Eltelt idő: "+min+" perc "+sec+ "mp");
                

                            System.out.println("initialDirections"+initialDirections);
        for (int i=0; i < game.gameLevel.dragonPositions.size(); i++) {
                            Position dragonPosition=game.gameLevel.dragonPositions.get(i);
            System.out.println("aaaaaa");

                //System.out.println(kk);
                Direction d = null;
                //WASD irányítás
                /*switch (kk){
                    case KeyEvent.VK_A:  d = Direction.LEFT; break;
                    case KeyEvent.VK_D: d = Direction.RIGHT; break;
                    case KeyEvent.VK_W:    d = Direction.UP; break;
                    case KeyEvent.VK_S:  d = Direction.DOWN; break;
                    case KeyEvent.VK_ESCAPE: game.loadGame(game.getGameID());
                }*/
                //switch (kk) {
                    switch (initialDirections.get(i)) {
                    case 1:
                        d = Direction.LEFT;
                        break;
                    case 2:
                        d = Direction.RIGHT;
                        break;
                    case 3:
                        d = Direction.UP;
                        break;
                    case 4:
                        d = Direction.DOWN;
                        break;
                }
                if (game.dragonStep(d,dragonPosition)) {
                    System.out.println("dragon stepped" + " x: " + game.getDragonPos().x + " y: " + game.getDragonPos().y);
                } else {
                    while (!game.dragonStep(d,dragonPosition)) {
                        initialDirections.set(i,Min + (int) (Math.random() * ((Max - Min) + 1)));
                        switch (initialDirections.get(i)) {
                            case 1:
                                d = Direction.LEFT;
                                break;
                            case 2:
                                d = Direction.RIGHT;
                                break;
                            case 3:
                                d = Direction.UP;
                                break;
                            case 4:
                                d = Direction.DOWN;
                                break;
                        }
                    }

                    System.out.println("dragon stepped" + " x: " + game.getDragonPos().x + " y: " + game.getDragonPos().y);
                }
                board.repaint();
                if (game.gameOver()) {
                    onGameOver();
                }
            }}
        });
        

        setResizable(false);
        setLocation(-10, 0);

        board.refresh();
        pack();
        refreshGameStatLabel();
        //setVisible(true);
        System.out.println("Dragon start position " + " x: " + game.getDragonPos().x + " y: " + game.getDragonPos().y);
        //timer.start();
//setLocationRelativeTo(null);
setExtendedState(MainWindow.MAXIMIZED_HORIZ-40);
//setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
getRootPane().setBorder(BorderFactory.createMatteBorder(5, 10, 0, 10, Color.BLACK));
    }
    
    /**
     * Updates the gameStatLabel with finished maps 
     */
    public final void refreshGameStatLabel() {
        String s = " Lépések száma: " + game.getNumSteps();
        gameStatLabel.setText(s);
    }
    
    /**
     * Sets the "Maps" menuitem
     * @param menu 
     */
    public void createGameLevelMenuItems(JMenu menu) {
        int cnt = 0;
        for (String s : game.getDifficulties()) {
            JMenu difficultyMenu = new JMenu(s);
            menu.add(difficultyMenu);
            for (Integer i : game.getLevelsOfDifficulty(s)) {
                JMenuItem item = new JMenuItem(new AbstractAction("Level-" + i) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.loadGame(new GameID(s, i));
                        board.refresh();
                        refreshGameStatLabel();
                        pack();
                        min=0;
                        sec=0;
                    }
                });
                if (i + cnt > finishedMaps + 1) {
                    item.setEnabled(false);
                }
                difficultyMenu.add(item);
            }
            cnt += 5;
        }
    }

    /**
     * Escorts the user back to Main Menu
     * @param finishedMaps 
     */
    private void backToMainMenu(int finishedMaps) {
        try {
            new MainMenu(finishedMaps, this.playerName);
            this.dispose();
        } catch (IOException ex) {
        }
    }
    
    /**
     * Stops the timer and pops up "Congratulations" message dialog and escorts back to Main Menu after
     */
    private void onGameOver() {
        timer.stop();
        ///////////////////Mentés game over esetén is
        /*try {
            highScores.putHighScore(playerName,finishedMaps);
            System.out.println(highScores.getHighScores());
        } catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        JOptionPane.showMessageDialog(MainWindow.this, "GAME OVER", "", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("GAME OVER");
        backToMainMenu(finishedMaps);
    }

    /**
     * Sets the "Map size" menuitem
     * @param menu
     * @param from
     * @param to
     * @param by 
     */
    private void createScaleMenuItems(JMenu menu, double from, double to, double by) {
        while (from <= to) {
            final double scale = from;
            String str = "";
            if (from == 1.0) {
                str = "Miniatűr";
            }
            if (from == 1.5) {
                str = "Kicsi";
            }
            if (from == 2.0) {
                str = "Alapértelmezett";
            }
            final String str1=str;
            JMenuItem item = new JMenuItem(new AbstractAction(str) {
                

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.setScale(scale)) {
                        pack();
                if(!str1.equals("Alapértelmezett"))
                    MainWindow.this.setLocationRelativeTo(null);
                    }else MainWindow.this.setLocation(-10, 0);
                }
            });
            menu.add(item);

            if (from == to) {
                break;
            }
            from += by;
            if (from > to) {
                from = to;
            }
        }
    }
    /**
     * Starts program execution by calling constructor of Main Menu
     * @param args 
     */
    public static void main(String[] args) {
        try {
            //LOW BUDGET KINÉZET
            /*UIDefaults uiDefaults = UIManager.getDefaults();
            uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(Color.gray));
            uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.white));
            JFrame.setDefaultLookAndFeelDecorated(true);*/
            //new MainWindow();
            new MainMenu(-1, "wwwww");
            //java -Dfile.encoding=UTF8 -jar MazeGame.jar futtatás cmd-ből

        } catch (IOException ex) {
        }
    }
}
