package main.java;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Model implements MouseMotionListener, MouseListener, ComponentListener, KeyListener {
    View view;

    private Paddle myPad;
    private Ball myBall;
    private ArrayList<Brick> myBricks;
    private int score;

    int dx = 2;
    int dy = 2;

    public Model (View view) {
        this.view = view;


        //beg: Paddle Create
        int paddleW = (int)(view.getWidth() * 0.1);
        int paddleH = (int)(view.getHeight() * 0.02);
        myPad = new Paddle(view.getWidth()/2 - paddleW/2, view.getHeight() - 3 * paddleH, paddleW, paddleH);
        //end: Paddle Create


        //beg: Ball Create
        double radius = paddleW / 6; // diameter is 2/3 of width of paddle
        //System.out.println("ball radius is: " + radius);
        double BallX = myPad.getXcoord() + paddleW / 3;
        //System.out.println("ball x coord is: " + BallX);
        double BallY = myPad.getYcoord() - 2 * radius;
        //System.out.println("ball y coord is: " + BallY);
        myBall = new Ball(radius, BallX, BallY);
        //end: Ball Create

        myBricks = bricksSetup(view, false);
        score = 0;


    }

    private ArrayList<Brick> bricksSetup(View v, boolean resizing) {
        ArrayList<Brick> temp = new ArrayList<Brick>();
        double topLeftX = v.getWidth() * 0.1;
        double topLeftY = v.getHeight() * 0.1;


        double brickW = (v.getWidth() - 2 * topLeftX) / 6; //bricks' width
        double brickH = 0.04 * v.getHeight(); //bricks' height

        int startX = (int)topLeftX; //starting x-coord of brick
        int startY = (int)topLeftY; //starting y-coord of brick

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 6; ++j) {
                Random rand = new Random();
                int num = rand.nextInt(3); //3 random levels
                Brick brick;
                if (num == 0) {
                    brick = new Brick(startX, startY, brickW, brickH, 1, resizing);
                    temp.add(brick);
                } else if (num == 1) {
                    brick = new Brick(startX, startY, brickW, brickH,2, resizing);
                    temp.add(brick);
                } else if (num == 2) {
                    brick = new Brick(startX, startY, brickW, brickH,3, resizing);
                    temp.add(brick);
                }
                startX = startX + (int)brickW; //update x-coord of brick
            }
            startX = (int)topLeftX;
            startY = startY + (int)brickH;
        }
        return temp;
    }

    public void runGame() {
        Timer viewTimer = new Timer();
        TimerTask task = new TimerTask()  {
            @Override
            public void run() {
                view.repaint();
            }
        };
        System.out.println("FPS: " + view.getFps());
        viewTimer.schedule(task, 0, (int)(1000/view.getFps()));


        Timer ballTimer = new Timer();
        TimerTask ballTask = new TimerTask()  {
            //long frameCount = 0;
            @Override
            public void run() {
                if (!view.isGameOver()) {
                    if (view.isPlayerStart()) {
                        handle_animation();
                    }
                }
            }
        };
        if (view.getSpeed() == 1) {
            ballTimer.scheduleAtFixedRate(ballTask, 0, 15);
        } else if (view.getSpeed() == 2) {
            ballTimer.scheduleAtFixedRate(ballTask, 0, 10);
        } else if (view.getSpeed() == 3) {
            ballTimer.scheduleAtFixedRate(ballTask, 0, 5);
        }

    }

    public void paintScreen(Graphics g) {

        myBall.paintBall(g);
        myPad.paintPad(g);
        for (Brick b : myBricks) {
            b.paintBrick(g);
        }
    }

    public void decideDirectFromPad() {
        if ((myBall.getBallX() + (2*myBall.getRad())) < (myPad.getXcoord() + (myPad.getW()/4))) { //ball on left 1/4 of pad
            if (dx < 0) {
                dy *= -1;
            } else if (dx > 0) {
                dx *= -1;
                dy *= -1;
            }
        } else if (myBall.getBallX() > (myPad.getXcoord() + myPad.getW() - (myPad.getW()/4))) { //ball on right 1/4 of pad
            if (dx < 0) {
                dx *= -1;
                dy *= -1;
            } else if (dx > 0) {
                dy *= -1;
            }
        } else { //ball on center 1/2 of pad
            dx *= 1;
            dy *= -1;
        }
    }

    public void decideDirectFromBrick(Brick b, String side) {
        if ((myBall.getBallX() + (2*myBall.getRad())) < (b.getX() + (b.getW()/4))) { //ball on left 1/4 of pad

            if (dx > 0) {
                dy *= -1;
            } else if (dx < 0) {
                dx *= -1;
                dy *= -1;
            }
        } else if (myBall.getBallX() > (b.getX() + b.getW() - (b.getW()/4))) { //ball on right 1/4 of pad
            if (dx > 0) {
                dx *= -1;
                dy *= -1;
            } else {
                dy *= -1;
            }
        } else { //ball on center 1/2 of pad
            dx *= 1;
            dy *= -1;
        }
    }

    public boolean checkAllBricks() {
        for (Brick b : myBricks) {
            if (!b.getIsDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public void handle_animation() {

        if (myBall.getBallY() + 2*myBall.getRad() > myPad.getYcoord() + myPad.getH()) { //ball misses the pad
            view.setGameLost(true);
            view.setGameOver(true);
        }

        // if we hit the edge of the window, change direction
        if (myBall.getBallX() < 0 || myBall.getBallX() > (view.getSize().width - myBall.getRad()*2)) { dx *= -1; }
        if (myBall.getBallY() < 0 || myBall.getBallY() > (view.getSize().height - myBall.getRad()*2)) { dy *= -1; }

        //if we hit the paddle
        if (myBall.intersects(myPad)) {
            decideDirectFromPad();
        }

        //if we hit bricks
        for (Brick b : myBricks) {
            if (!b.getIsDestroyed()) {
                if (myBall.intersects(b)) {
                    if (myBall.getBallY() > b.getY()) { //collide from bottom
                        decideDirectFromBrick(b, "bottom");
                        b.decreaseType();
                        score += 10;
                    } else if (myBall.getBallY() + (myBall.getRad()*2) < b.getY()) { //collide from top of brick
                        decideDirectFromBrick(b, "top");
                        b.decreaseType();
                        score += 10;
                    } else if (myBall.getBallX() > (b.getX() + b.getW())) { //collide from right
                        dx *= -1;
                        //dy *= -1;
                        b.decreaseType();
                        score += 10;
                    } else if (myBall.getBallX() + (2*myBall.getRad()) < b.getX()) { //collide from left
                        dx *= -1;
                        //dy *= -1;
                        b.decreaseType();
                        score += 10;
                    }
                }
            }
        }

        // move the ball
        myBall.updateBall(myBall.getRad(), myBall.getBallX() + dx, myBall.getBallY() + dy);

        boolean allBricksDestroyed = checkAllBricks();
        if (allBricksDestroyed) {
            view.setGameOver(true);
            view.setGameWon(true);
        }

        // force painting
        view.repaint();
    }

    public int getScore() {
        return score;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Component win = e.getComponent();
        view.setWidth((int)win.getSize().getWidth());
        view.setHeight((int)win.getSize().getHeight());

        myBricks = bricksSetup(view, true);

        int paddleW = (int)(view.getWidth() * 0.1);
        int paddleH = (int)(view.getHeight() * 0.02);
        myPad.updatePad(view.getWidth()/2 - paddleW/2, view.getHeight() - 3 * paddleH, paddleW, paddleH);

        double radius = paddleW / 6; // diameter is 2/3 of width of paddle
        double BallX = myPad.getXcoord() + paddleW / 3;
        double BallY = myPad.getYcoord() - 2*radius;
        myBall.updateBall(radius, BallX, BallY);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x_pos = e.getX();

        int newX = x_pos - myPad.getW()/2;
        if (newX > 0 && newX + myPad.getW() < view.getWidth()) {
            myPad.updatePad(newX, myPad.getYcoord(), myPad.getW(), myPad.getH());
        }

        if (!view.isPlayerStart()) {
            double radius = myPad.getW() / 6; // diameter is 2/3 of width of paddle
            double BallX = myPad.getXcoord() + myPad.getW() / 3;
            double BallY = myPad.getYcoord() - 2 * radius;
            myBall.updateBall(radius, BallX, BallY);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (!view.isPlayerStart()) { //player has not started the game
            view.setPlayerStart();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) { //moves pad to left
            if (myPad.getXcoord() > 0) {
                myPad.updatePad(myPad.getXcoord() - 5, myPad.getYcoord(), myPad.getW(), myPad.getH());
            }
            if (!view.isPlayerStart()) { //if player has not started
                double radius = myPad.getW() / 6; // diameter is 2/3 of width of paddle
                double BallX = myPad.getXcoord() + myPad.getW() / 3;
                double BallY = myPad.getYcoord() - 2 * radius;
                myBall.updateBall(radius, BallX, BallY);
            }

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //moves pad to right
            if (myPad.getXcoord() + myPad.getW() < view.getWidth()) {
                myPad.updatePad(myPad.getXcoord() + 5, myPad.getYcoord(), myPad.getW(), myPad.getH());
            }

            if (!view.isPlayerStart()) { //if player has not started
                double radius = myPad.getW() / 6; // diameter is 2/3 of width of paddle
                double BallX = myPad.getXcoord() + myPad.getW() / 3;
                double BallY = myPad.getYcoord() - 2 * radius;
                myBall.updateBall(radius, BallX, BallY);
            }
        }
    }


    //##################################### Do not need

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}


    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
