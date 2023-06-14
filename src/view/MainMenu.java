package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import model.GameID;

public class MainMenu {
    
    public MainWindow mw;
    public JFrame y;
    public int finishedMaps;
    JLabel showFinishedMaps;
    public String PlayerName;
    /**
     * Sets up MainMenu
     * @param finishedMaps
     * @param player
     * @throws IOException 
     */
    public MainMenu(int finishedMaps, String player) throws IOException {

        y = new JFrame();

        //y.setUndecorated(true);--TOOLBAR NÉLKÜLI FRAME
        if (finishedMaps == -1) {
            this.finishedMaps = 0;
            SignIn s = new SignIn(this);

        } else {
            this.finishedMaps = finishedMaps;
            this.PlayerName = player;
            mw = new MainWindow(this.finishedMaps, this.PlayerName);
            mw.game.playerName = this.PlayerName;
            mw.setVisible(false);
            //y.setVisible(true);
            System.out.println("----------------------");
            System.out.println("Player Name:" + PlayerName);

            showFinishedMaps = new JLabel("Eddig megoldott útvesztők száma:" + this.finishedMaps, SwingConstants.CENTER);

            y.getContentPane().setLayout(new BorderLayout());

            y.getContentPane().add(showFinishedMaps, BorderLayout.NORTH);
            URL url1 = MainWindow.class.getResource("/res/icon2.png");
            y.setIconImage(Toolkit.getDefaultToolkit().getImage(url1));
            y.setLocation(100, 100);

            URL url = MainWindow.class.getResource("/res/Nevtelen.png");
            ImageIcon icon = new ImageIcon();
            try {
                icon = new ImageIcon(ImageIO.read(url));
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*String labelText ="<html>Mystic Maze<br>Part One";
            JLabel thumb = new JLabel(labelText);*/
            JLabel thumb = new JLabel();
            thumb.setIcon(icon);
            thumb.setOpaque(true);
            thumb.setIconTextGap(-488);
            thumb.setFont(new Font("Garamond", Font.BOLD, 70));
            thumb.setForeground(new Color(18, 0, 41));
            thumb.setText("Mystic Maze");
            y.getContentPane().add(thumb, BorderLayout.SOUTH);
            y.setSize(600, 670);
            y.setLocationRelativeTo(null);
            y.setResizable(false);
            JMenuBar menuBar = new JMenuBar();

            JMenu menuGame = new JMenu("Opciók");
            JMenu topTen = new JMenu("A legjobb 10 játékos");
            JMenuItem toptenList = new JMenuItem(new AbstractAction("Megjelenítés") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        System.out.println(mw.highScores.getHighScores());
                        HighScoreWindow h = new HighScoreWindow(mw.highScores.getHighScores(), y);
                        //h.setVisible(true);
                    } catch (Exception ex) {

                    }
                }
            });
            /*JMenu table=new JMenu("Haladás");
            JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Megjelenítés") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreWindow(mw.game.getHighScores(), y);
            }
            });
            table.add(menuHighScores);*/

            topTen.add(toptenList);
            JMenu newGame = new JMenu("Új játék");
            JMenu menuGameLevel = new JMenu("Pálya");

            //JMenu menuGameScale = new JMenu("Kicsinyítés");
            createGameLevelMenuItems(menuGameLevel);
            //createScaleMenuItems(menuGameScale, 0.5, 1.0, 0.5);

            JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Kilépés") {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JDialog d = new JDialog(y, "Kilépés", true);
                    d.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    d.setLayout(new FlowLayout());
                    JButton b = new JButton("Vissza a főmenübe");
                    JButton a = new JButton("Kilépés és mentés");
                    JButton c = new JButton("Kilépés mentés nélkül");
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            d.setVisible(false);
                        }
                    });
                    a.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                mw.highScores.putHighScore(PlayerName, finishedMaps);
                                System.out.println("DATABASE UPDATED");
                            } catch (Exception ex) {

                            }
                            System.exit(0);
                        }
                    });
                    c.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //d.setVisible(false);
                            System.exit(0);
                        }
                    });
                    //d.add( new JLabel ("Click button to continue.")); 
                    d.add(a);
                    d.add(c);

                    d.add(b);
                    d.setSize(400, 100);
                    d.setLocation(570, 400);
                    d.setVisible(true);
                }
            });
            newGame.add(menuGameLevel);
            menuGame.add(newGame);
            menuGame.addSeparator();
            menuGame.add(menuGameExit);
            menuBar.add(menuGame);
            menuBar.add(topTen);
            //menuBar.add(table);
            y.setJMenuBar(menuBar);
            y.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            y.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {

                    JDialog d = new JDialog(y, "Kilépés", true);
                    d.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    d.setLayout(new FlowLayout());
                    JButton b = new JButton("Vissza a főmenübe");
                    JButton a = new JButton("Kilépés és mentés");
                    JButton c = new JButton("Kilépés mentés nélkül");
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            d.setVisible(false);
                        }
                    });
                    a.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                mw.highScores.putHighScore(PlayerName, finishedMaps);
                                System.out.println("DATABASE UPDATED");
                            } catch (Exception ex) {

                            }
                            System.exit(0);
                        }
                    });
                    c.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //d.setVisible(false);
                            System.exit(0);

                        }
                    });
                    d.add(a);
                    d.add(c);
                    d.add(b);
                    d.setSize(400, 100);
                    d.setLocation(570, 400);
                    d.setVisible(true);
                }
            });
            y.setTitle("Main Menu");
            y.setVisible(true);
        }
    }
    /**
     * Sets the "Maps" menuitem
     * @param menu 
     */
    public final void createGameLevelMenuItems(JMenu menu) {
        int cnt = 0;
        for (String s : mw.game.getDifficulties()) {
            JMenu difficultyMenu = new JMenu(s);
            menu.add(difficultyMenu);
            for (Integer i : mw.game.getLevelsOfDifficulty(s)) {
                JMenuItem item = new JMenuItem(new AbstractAction("Level-" + i) {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        mw.setVisible(true);
                        mw.game.loadGame(new GameID(s, i));
                        mw.refreshGameStatLabel();
                        mw.board.refresh();
                        mw.timer.start();
                        System.out.println("MainWindow timer started");
                        mw.pack();
                        y.dispose();
                        /* Ez így van rendjén
                        game.loadGame(new GameID(s, i));
                        board.refresh();
                        refreshGameStatLabel();
                        pack();!!!!!!!!!!!!!!!!!!!!!!!!
                         */
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
}
