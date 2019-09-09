package main.java;

import java.awt.*;

public class Paddle extends Rectangle {

    int x, y, w, h;

    public Paddle(int x_coord, int y_coord, double width, double height){
        this.x = x_coord;
        this.y = y_coord;
        this.w = (int)width;
        this.h = (int)height;

        this.setBounds(x, y, w, h);
    }

    public void paintPad(Graphics g) {
        Graphics2D myG = (Graphics2D)g;
        myG.setColor(Color.GREEN);
        myG.fill(this);
        myG.setColor(Color.GRAY);
        myG.draw(this);
    }

    public void updatePad(int x, int y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = (int)w;
        this.h = (int)h;
        this.setBounds(x, y, (int)w, (int)h);
    }

    public int getXcoord() {
        return x;
    }

    public int getYcoord() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
