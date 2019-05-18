package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @auther camus
 * date 2019/5/17 19:32
 */
public class Tank {
    private int x, y;
    private static final int XSPEED = 2;
    private static final int YSPEED = 2;

    public static final int WIDTH = 30;
    public static final int HEITH = 30;

    private boolean bL = false, bR = false, bU = false, bD = false;

    TankClient tc = null;


    enum Direction {L, LU, R, RU, U, D, LD, RD, STOP};

    private Direction dir = Direction.STOP;


    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x,int y,TankClient tc){
        this(x,y);
        this.tc = tc;
    }


    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEITH);
        g.setColor(c);

        move();


    }

    public void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
    }

    public Missile fire(){
        int x1 = this.x + Tank.WIDTH /2 - Missile.WIDTH/2;
        int y1 = this.y + Tank.HEITH /2 - Missile.HEITH/2;
        Missile m = new Missile(x1, y1, dir);
        return m;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_X:
                tc.m = fire();
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            default:
                break;
        }
        locateDirection();

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            default:
                break;
        }
        locateDirection();
    }

    public void locateDirection() {
        if (bL && !bR && !bU && !bD) dir = Direction.L;
        else if (bL && !bR && bU && !bD) dir = Direction.LU;
        else if (bL && !bR && !bU && bD) dir = Direction.LD;
        else if (!bL && bR && !bU && !bD) dir = Direction.R;
        else if (!bL && bR && bU && !bD) dir = Direction.RU;
        else if (!bL && bR && !bU && bD) dir = Direction.RD;
        else if (!bL && !bR && bU && !bD) dir = Direction.U;
        else if (!bL && !bR && !bU && bD) dir = Direction.D;
        else dir = Direction.STOP;
    }


}
