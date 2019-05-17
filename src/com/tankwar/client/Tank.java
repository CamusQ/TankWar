package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @auther camus
 * date 2019/5/17 19:32
 */
public class Tank {
    int x;
    int y;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
        g.setColor(c);

    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_RIGHT:x += 1;break;
            case KeyEvent.VK_LEFT:x -= 1;break;
            case KeyEvent.VK_UP:y -= 1;break;
            case KeyEvent.VK_DOWN:y += 1;break;
            default:break;
        }
    }


}
