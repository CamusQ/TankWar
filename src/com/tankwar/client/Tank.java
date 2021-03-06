package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.List;

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



    TankClient tc = null;

    private int OldX;
    private int OldY;

    private Direction dire[] = Direction.values();

    private Direction dir = Direction.STOP;
    private Direction ptDir = Direction.D;

    private bloodBar bb = new bloodBar();

    private int life = 100;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

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
        OldX = x;
        OldY = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.dir = dir;
        this.tc = tc;
    }


    public void draw(Graphics g) {
        if(!good && this.getRectangle().intersects(tc.w1.getRec()) || !good && this.getRectangle().intersects(tc.w2.getRec())){
            this.live = false;
        }

        if (!isLive()) {
            if (!good)
                tc.enemyTanks.remove(this);
//            if (tc.enemyTanks.size() < 6) {
//                tc.enemyTanks.add(new Tank(new Random().nextInt(800), new Random().nextInt(600), false, dir, tc));
//            }
            return;
        }

        Color c = g.getColor();
        if (good == true) {
            g.setColor(Color.RED);
            bb.draw(g);
            g.fillOval(x, y, WIDTH, HEITH);
        } else
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
        OldX = x;
        OldY = y;

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
                step = r.nextInt(12) + 3;
                dir = dire[r.nextInt(dire.length)];
            }
            step--;
            if (r.nextInt(40) > 36)
                fire();
        }


    }

    public void eat() {
        if (this.live && tc.blood.isLive() && this.getRectangle().intersects(tc.blood.getRec())) {
            this.life = 100;
            tc.blood.setLive(false);
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

    public Missile fire(Direction dir) {
        if (!live) return null;
        int x1 = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y1 = this.y + Tank.HEITH / 2 - Missile.HEITH / 2;
        Missile m = new Missile(x1, y1, good, dir, this.tc);
        tc.missiles.add(m);
        return m;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_F2 :
                if(this.good){
                    this.live = true;
                    this.life = 100;
                }
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
            case KeyEvent.VK_A:
                superFire();
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

    public void stay() {
        x = OldX;
        y = OldY;
    }

    public boolean collidesWithWall(Wall wall) {
        if (this.live && this.getRectangle().intersects(wall.getRec())) {
            this.stay();
            return true;
        }
        return false;
    }


    public boolean collidesWithTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            if (this != tanks.get(i)) {
                if (this.live && tanks.get(i).live && this.getRectangle().intersects(tanks.get(i).getRectangle())) {
                    this.stay();
                    return true;
                }
            }
        }
        return false;
    }

    public void superFire() {
        for (int i = 0; i < 8; i++) {
            fire(dire[i]);
        }
    }

    public class bloodBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.green);
            int w = WIDTH * life / 100;
            g.drawRect(x, y - 10, w, 10);
            g.fillRect(x, y - 10, w, 10);
            g.setColor(c);
        }
    }

}
