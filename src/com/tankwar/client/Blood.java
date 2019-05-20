package com.tankwar.client;

import java.awt.*;
import java.util.TimerTask;

/**
 * @auther camus
 * date 2019/5/19 21:24
 */
public class Blood {
    int x,y,w,h;
    TankClient tc;

    boolean live = true;

    int step = 0;

    private int[][] pos = {
            {300,300},{300,310},{300,320},{300,330},{300,340},{300,350},{300,360},{300,370}
            ,{320,370},{320,360},{320,350},{320,340},{320,330},{320,320},{320,310},{320,300}
    };


    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Blood() {
        this.x = pos[0][0];
        this.y = pos[0][1];
        w = h = 15;
    }

    public Blood(int x, int y, int w, int h, TankClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.green);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    public void move(){
        step ++;
        if(step == pos.length){
            step =0;
        }

        x = pos[step][0];
        y = pos[step][1];

    }

    public Rectangle getRec(){
        return new Rectangle(x, y, w, h);
    }
}
