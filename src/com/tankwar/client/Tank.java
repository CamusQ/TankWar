package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

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
    private boolean good;
    private boolean live = true;

    private Random r = new Random();
    private int step = new Random().nextInt(12) + 3;

    enum Direction {L, LU, R, RU, U, D, LD, RD, STOP}

    ;

    TankClient tc = null;


    private Direction dir = Direction.STOP;
    private Direction ptDir = Direction.D;

    public boolean isGood() {
        return good;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }


    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.dir = dir;
        this.tc = tc;
    }


    public void draw(Graphics g) {

        if (!isLive()) {
            if (!good)
                tc.enemyTanks.remove(this);
            if (tc.enemyTanks.size() < 6) {
                tc.enemyTanks.add(new Tank(new Random().nextInt(800), new Random().nextInt(600), false, dir, tc));
            }
            return;
        }

        Color c = g.getColor();
        if (good == true)
            g.setColor(Color.RED);
        else
            g.setColor(Color.MAGENTA);
        g.fillOval(x, y, WIDTH, HEITH);
        g.setColor(c);


        switch (ptDir) {
            case L:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x, y + Tank.HEITH / 2);
                break;
            case LU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x + Tank.WIDTH / 2, y);
                break;
            case RU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x + Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x + Tank.WIDTH, y + Tank.HEITH / 2);
                break;
            case RD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x + Tank.WIDTH, y + Tank.HEITH);
                break;
            case D:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x + Tank.WIDTH / 2, y + Tank.HEITH);
                break;
            case LD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEITH / 2, x, y + Tank.HEITH);
                break;
        }

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

        if (this.dir != Direction.STOP) {
            ptDir = dir;
        }

        if (x < 0) x = 0;
        if (y < 30) y = 30;
        if (x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if (y + Tank.HEITH > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEITH;


        if (!good) {
            if (step == 0) {
                Direction dire[] = Direction.values();
                step = r.nextInt(12) + 3;
                dir = dire[r.nextInt(dire.length)];
            }
            step--;
            if (r.nextInt(40) > 36)
                fire();
        }


    }

    public Missile fire() {
        if (!live) return null;
        int x1 = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y1 = this.y + Tank.HEITH / 2 - Missile.HEITH / 2;
        Missile m = new Missile(x1, y1, good, ptDir, this.tc);
        tc.missiles.add(m);
        return m;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
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
            case KeyEvent.VK_X:
                fire();
                break;
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

    public Rectangle getRectangle() {
        return new Rectangle(x, y, WIDTH, HEITH);
    }


}
