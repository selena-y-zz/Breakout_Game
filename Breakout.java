package main.java;

import javax.swing.*;
import java.awt.*;

public class Breakout extends JFrame {

    //Constants
    public final int MIN_WIN_WIDTH = 600;
    public final int MIN_WIN_HEIGHT = 600;
    public final int MAX_WIN_WIDTH = 750;
    public final int MAX_WIN_HEIGHT = 750;
    //Constants

    public Breakout() {
        setTitle("Breakout Game");
        setLayout(new BorderLayout());

        View gameView = new View("1", "25", MIN_WIN_WIDTH, MIN_WIN_HEIGHT, false); //initial view width and height are 600
        this.add(gameView, BorderLayout.CENTER);
        this.pack();

        Model myMod = new Model(gameView);
        gameView.initMod(myMod);
        this.addKeyListener(myMod);
        this.addMouseListener(myMod);
        this.addComponentListener(myMod);
        this.addMouseMotionListener(myMod);
        setFocusable(true);
        myMod.runGame();

        this.setPreferredSize(new Dimension(MIN_WIN_WIDTH, MIN_WIN_HEIGHT));
        this.setMinimumSize(new Dimension(MIN_WIN_WIDTH, MIN_WIN_HEIGHT));
        this.setMaximumSize(new Dimension(MAX_WIN_WIDTH, MAX_WIN_HEIGHT));


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
    public Breakout(String s, String fps) {
        setTitle("Breakout Game");
        setLayout(new BorderLayout());

        View gameView = new View(s, fps, MIN_WIN_WIDTH, MIN_WIN_HEIGHT, false); //initial view width and height are 800
        this.add(gameView, BorderLayout.CENTER);
        this.pack();

        Model myMod = new Model(gameView);
        gameView.initMod(myMod);
        this.addKeyListener(myMod);
        this.addMouseListener(myMod);
        this.addComponentListener(myMod);
        this.addMouseMotionListener(myMod);
        setFocusable(true);
        myMod.runGame();

        this.setPreferredSize(new Dimension(MIN_WIN_WIDTH, MIN_WIN_HEIGHT));
        this.setMinimumSize(new Dimension(MIN_WIN_WIDTH, MIN_WIN_HEIGHT));
        this.setMaximumSize(new Dimension(MAX_WIN_WIDTH, MAX_WIN_HEIGHT));


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
