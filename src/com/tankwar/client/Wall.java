package com.tankwar.client;

import java.awt.*;

/**
 * @auther camus
 * date 2019/5/19 17:02
 */
public class Wall {
    int x, y, w, h;
    private TankClient tc;

    public Wall(int x, int y, int w, int h, TankClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, w, h);
    }

}
