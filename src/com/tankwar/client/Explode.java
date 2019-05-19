package com.tankwar.client;

import java.awt.*;

/**
 * @auther camus
 * date 2019/5/19 10:38
 */
public class Explode {
    int x,y;
    private boolean live = true;

    int[] diameter = {4,7,12,18,26,35,47,30,16,8};
    int step = 0;

    TankClient tc;

    public boolean isLive() {
        return live;
    }

    public Explode(int x, int y, TankClient tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){

        if(!live){
            tc.explodes.remove(this);
            return;
        }

        if(step == diameter.length){
            step = 0;
            live = false;
            return;
        }

        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, diameter[step],diameter[step]);
        g.setColor(c);

        step++;

    }

}
