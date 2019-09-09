package main.java;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {

    private int ballX;
    private int ballY;
    private int rad;


    public Ball(double rad, double ballX, double ballY) {
        this.ballX = (int)ballX;
        this.ballY = (int)ballY;
        this.rad = (int)rad;
    }

    public void paintBall(Graphics g){
        Graphics2D myG = (Graphics2D)g;
        myG.setColor(Color.YELLOW);
        myG.fill(this);
        myG.setColor(Color.GRAY);
        myG.draw(this);
    }

    public void updateBall (double rad, double ballX, double ballY) {
        this.ballX = (int)ballX;
        this.ballY = (int)ballY;
        this.rad = (int)rad;

        this.setFrame(this.ballX, this.ballY, this.rad*2, this.rad*2);

    }

    public int getBallX() {
        return ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public int getRad() {
        return rad;
    }
}
