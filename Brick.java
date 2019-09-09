package main.java;

import java.awt.*;

public class Brick extends Rectangle {

    int x, y, w, h, type;
    private boolean isDestroyed;


    public Brick(int x_coord, int y_coord, double width, double height, int type, boolean resizing) {
        this.x = x_coord;
        this.y = y_coord;
        this.w = (int)width;
        this.h = (int)height;
        this.type = type;

        this.setBounds(x, y, w, h);

        isDestroyed = false;
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void paintBrick(Graphics g) {
        Graphics2D myG = (Graphics2D)g;
        if (!isDestroyed) {
            if (type == 1) {
                myG.setColor(Color.WHITE);
            } else if (type == 2) {
                myG.setColor(Color.PINK);
            } else if (type ==3) {
                myG.setColor(Color.RED);
            }

            myG.fill(this);
            myG.setColor(Color.GRAY);
            myG.draw(this);
        }
    }

    public void updateBricks(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.setBounds(x, y, w, h);
    }

    public void decreaseType() {
        type -= 1;
        if (type == 0) {
            isDestroyed = true;
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public int getW() {
        return w;
    }
}
