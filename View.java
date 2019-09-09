package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JPanel {
    private Model myMod;

    private boolean gameStarted;
    private boolean gameWon;
    private boolean gameLost;
    private boolean gameOver;
    private boolean playerStart;

    private double speed;
    private double fps;

    private int width;
    private int height;
    private JButton startGame;
    //private JButton restartGame;

    public View(String speed, String fps, int width, int height, boolean start) {

        this.gameStarted = start;
        this.gameWon = false;
        this.gameLost = false;
        this.gameOver = false;
        this.playerStart = false;

        System.out.println(speed);
        this.speed = Double.parseDouble(speed);
        this.fps = Double.parseDouble(fps);
        this.width = width;
        this.height = height;

        this.setLayout(new BorderLayout());

        startGame = new JButton("Start Game");
        startGame.setBackground(Color.BLACK);
        startGame.setOpaque(true);
        startGame.setBorderPainted(false);
        startGame.setForeground(Color.PINK);
        startGame.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(startGame, BorderLayout.SOUTH);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameStarted();
            }
        });
    }

    public void setGameStarted() {
        gameStarted = true;
    }

    //Getters
    public boolean isPlayerStart() {
        return playerStart;
    }
    public boolean isGameOver() {
        return gameOver;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public double getSpeed() {
        return speed;
    }

    public double getFps() {
        return fps;
    }
    //Setters


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setPlayerStart() {
        //System.out.println("playerstart is true");
        this.playerStart = true;
    }

    public void initMod(Model model) {
        myMod = model;
    }


    public void paintComponent(Graphics g) {
        FontMetrics name;
        int x, y;

        Graphics2D myG = (Graphics2D)g;
        myG.setColor(Color.BLACK);
        myG.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (!gameStarted) { //game not started
            //draw the beginning Splash Screen

            //Game Title
            myG.setColor(Color.pink);
            Font txtFont = new Font("Arial", Font.BOLD, (int)(this.getHeight() * 0.08));
            myG.setFont(txtFont);
            String text = "BREAKOUT GAME";
            name = myG.getFontMetrics();
            x = (this.getWidth()/2) - (name.stringWidth(text)/2);
            y = (int)( (0.1 * this.getHeight()) + name.getHeight());
            myG.drawString(text, x, y);

            //My Name and ID
            text= "Selena Yang 20661504";
            txtFont = new Font("Arial", Font.BOLD, (int)(this.getHeight() * 0.05));
            myG.setFont(txtFont);
            name = myG.getFontMetrics();
            x = (this.getWidth()/2) - (name.stringWidth(text)/2);
            y = y + name.getHeight();
            myG.drawString(text, x, y);

            //Instructions
            text= "You can use the mouse to move the paddle.";
            txtFont = new Font("Arial", Font.PLAIN, (int)(this.getHeight() * 0.02));
            myG.setFont(txtFont);
            name = myG.getFontMetrics();
            x = this.getWidth()/2 - name.stringWidth(text)/2;
            y = y + name.getHeight();
            myG.drawString(text, x, y);
            text = "Red bricks need 3 hits, pink bricks need 2 hits, white bricks need 1 hit to break";
            x = (this.getWidth()/2) - (name.stringWidth(text)/2);
            y = y + name.getHeight();
            myG.drawString(text, x, y);
            text = "Every hit earns you 10 points. Click Start Game to start the fun!";
            x = (this.getWidth()/2) - (name.stringWidth(text)/2);
            y = y + name.getHeight();
            myG.drawString(text, x, y);

        } else if (gameStarted) { //game started
            remove(startGame);
            if (!gameOver) {
                myMod.paintScreen(g);

                Font myFont = new Font("Arial", Font.BOLD, (int)(this.getWidth() * 0.03));
                myG.setFont(myFont);
                String myScore = "Score: " + myMod.getScore();
                name = myG.getFontMetrics();
                x = (int)(0.03 * this.getWidth()) ;
                y = (this.getY() + name.getHeight());
                myG.drawString(myScore, x,y);

            } else if (gameOver && gameWon) {
                //DRAW WINNING SCREEN
                g.setColor(Color.BLACK);
                myG.fillRect(0, 0, this.getWidth(), this.getHeight());

                myG.setColor(Color.PINK);
                Font myFont = new Font("Arial", Font.BOLD, (int)(this.getHeight() * 0.06));
                myG.setFont(myFont);
                String msg = "Congratz! You win!!! Your Score is: " + myMod.getScore();
                name = myG.getFontMetrics();
                x = this.getWidth()/2 - name.stringWidth(msg)/2;
                y = this.getHeight()/2 - 2 * name.getHeight();
                myG.drawString(msg, x, y);

                myG.setColor(Color.PINK);
                myFont = new Font("Arial", Font.BOLD, (int)(this.getHeight() * 0.03));
                myG.setFont(myFont);
                msg = "Your score is: " + myMod.getScore();
                name = myG.getFontMetrics();
                x = this.getWidth()/2 - name.stringWidth(msg)/2;
                y = this.getHeight()/2 - 2 * name.getHeight();
                myG.drawString(msg, x, y);

            } else if (gameOver && gameLost) {
                //DRAW LOSING SCREEN
                g.setColor(Color.BLACK);
                myG.fillRect(0, 0, this.getWidth(), this.getHeight());

                myG.setColor(Color.PINK);
                Font myFont = new Font("Arial", Font.BOLD, (int)(this.getHeight() * 0.06));
                myG.setFont(myFont);
                String msg = "Nice try, there is always next time.";
                name = myG.getFontMetrics();
                x = this.getWidth()/2 - name.stringWidth(msg)/2;
                y = this.getHeight()/2 - 2 * name.getHeight();
                myG.drawString(msg, x,y);

                myG.setColor(Color.PINK);
                myFont = new Font("Arial", Font.BOLD, (int)(this.getHeight() * 0.03));
                myG.setFont(myFont);
                msg = "Your score is: " + myMod.getScore();
                name = myG.getFontMetrics();
                x = this.getWidth()/2 - name.stringWidth(msg)/2;
                y = this.getHeight()/2 - 2 * name.getHeight();
                myG.drawString(msg, x, y);

            }
        }
    }
}
