/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author Sütő Attila
 */
public class SignIn extends javax.swing.JFrame {

    private final MainMenu m;

    /**
     * Creates new form SignIn
     *
     * @param m
     */
    public SignIn(MainMenu m) {
        this.m = m;
        initComponents();
        URL url1 = MainWindow.class.getResource("/res/icon2.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url1));
        //toFront();
        //requestFocus();
        setAlwaysOnTop(true);
        m.y.setVisible(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField = new javax.swing.JTextField();
        OK_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Belépés");
        setBackground(new java.awt.Color(217, 198, 159));

        jLabel2.setFont(new java.awt.Font("Garamond", 0, 24)); // NOI18N
        jLabel2.setText("Név:");

        jTextField.setFont(new java.awt.Font("Garamond", 0, 24)); // NOI18N
        jTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldActionPerformed(evt);
            }
        });

        OK_button.setFont(new java.awt.Font("Garamond", 0, 18)); // NOI18N
        OK_button.setText("OK");
        OK_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(OK_button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OK_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldActionPerformed
    /**
     * Sets up MainMenu and disposes this
     * @param evt 
     */
    private void OK_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OK_buttonActionPerformed
        m.PlayerName = jTextField.getText();
        try {
            m.mw = new MainWindow(m.finishedMaps, m.PlayerName);
        } catch (Exception e) {
            System.out.println("aa");
        }
        m.mw.setVisible(false);
        System.out.println("Finished maps:"+m.finishedMaps);
        m.showFinishedMaps = new JLabel("Eddig megoldott útvesztők száma:" + m.finishedMaps, SwingConstants.CENTER);

        m.y.getContentPane().setLayout(new BorderLayout());

        m.y.getContentPane().add(m.showFinishedMaps, BorderLayout.NORTH);
        URL url1 = MainWindow.class.getResource("/res/icon2.png");
        m.y.setIconImage(Toolkit.getDefaultToolkit().getImage(url1));
        m.y.setLocation(100, 100);

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
        //thumb.setIconTextGap(-470);
        /*thumb.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 70));
                    thumb.setForeground(new Color(18,97,41));*/
        thumb.setIconTextGap(-488);
        thumb.setFont(new Font("Garamond", Font.BOLD, 70));
        thumb.setForeground(new Color(18, 0, 41));
        thumb.setText("Mystic Maze");
        m.y.getContentPane().add(thumb, BorderLayout.SOUTH);
        m.y.setSize(600, 670);
        m.y.setLocationRelativeTo(null);
        m.y.setResizable(false);
        JMenuBar menuBar = new JMenuBar();

        JMenu menuGame = new JMenu("Opciók");
        JMenu topTen = new JMenu("A legjobb 10 játékos");
        JMenuItem toptenList = new JMenuItem(new AbstractAction("Megjelenítés") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("DATABASE: "+m.mw.highScores.getHighScores());
                    HighScoreWindow h = new HighScoreWindow(m.mw.highScores.getHighScores(), m.y);
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
        m.createGameLevelMenuItems(menuGameLevel);
        //createScaleMenuItems(menuGameScale, 0.5, 1.0, 0.5);

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Kilépés") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog(m.y, "Kilépés", true);
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
                            m.mw.highScores.putHighScore(m.PlayerName, m.finishedMaps);
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

        newGame.add(menuGameLevel);
        menuGame.add(newGame);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        menuBar.add(topTen);
        //menuBar.add(table);
        m.y.setJMenuBar(menuBar);
        m.y.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        m.y.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JDialog d = new JDialog(m.y, "Kilépés", true);
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
                            m.mw.highScores.putHighScore(m.PlayerName, m.finishedMaps);
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
        m.y.setTitle("Main Menu");
        this.dispose();
        m.y.setVisible(true);
        System.out.println("Player Name:" + m.PlayerName);
        System.out.println("MainWindow :RUNNING (timer stopped)");
        System.out.println("----------------------");
    }//GEN-LAST:event_OK_buttonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OK_button;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField;
    // End of variables declaration//GEN-END:variables
}