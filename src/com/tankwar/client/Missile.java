package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @auther camus
 * date 2019/5/18 9:40
 */
public class Missile {
    private static final int XSPEED = 5;
    private static final int YSPEED = 5;

    public static final int WIDTH = 10;
    public static final int HEITH = 10;

    private static TankClient tc = null;

    private int x;
    private int y;

    private boolean live = true;

    private Tank.Direction dir;

    public boolean isLive() {
        return live;
    }

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Tank.Direction dir,TankClient tc){
        this(x, y, dir);
        this.tc = tc;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval(x , y , WIDTH, HEITH);
        g.setColor(c);

        move();
    }

    private void move(){
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
        }

        if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT){
            live = false;
            tc.missiles.remove(this);
        }
    }
}
