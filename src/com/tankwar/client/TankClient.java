package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther camus
 * date 2019/5/17 15:52
 */
public class TankClient extends Frame {


    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    Tank myTank = new Tank(50, 50, true, this);
    Tank enemyTank = new Tank(100, 100, false, this);
    List<Missile> missiles = new ArrayList<Missile>();


    Image offScreenImage = null;

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("missile count" + missiles.size(), 10, 50);
        g.setColor(c);

        myTank.draw(g);
        enemyTank.draw(g);

        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTank(enemyTank);

            m.draw(g);

        }
    }

    public void update(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }

        Color c = g.getColor();
        Graphics gOffScreen = offScreenImage.getGraphics();
        gOffScreen.setColor(new Color(2, 94, 33));
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);


    }

    public void lauchFrame() {
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");
        this.addWindowListener(new WindowAdapter() {//匿名类适合用在类短小、不涉及拓展、不涉及重要的业务逻辑
            public void windowClosing(WindowEvent e) {
                System.exit(0);//表示正常退出
            }

        });
        this.setResizable(false);
        this.setBackground(new Color(2, 94, 33));

        this.addKeyListener(new keyMonitor());

        this.setVisible(true);

        //什么时候启动重画线程（窗口打开以后）
        new Thread(new paintThread()).start();
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.lauchFrame();
    }

    //内部类。可以非常方便的访问包装类的方法。不方便公开的，只为包装类服务类应当定义为内部类
    private class paintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();//repaint 内部自动调用paint（先调用update 再调用paint）
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class keyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }


}
