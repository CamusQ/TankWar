package com.tankwar.client;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @auther camus
 * date 2019/5/17 15:52
 */
public class TankClient extends Frame {

    int x = 50;
    int y = 50;

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;


    Image offScreenImage = null;

    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y, 30, 30);//画一个实心圆
        g.setColor(c);
    }

    public void update(Graphics g) {

        if(offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }

        Color c = g.getColor();
        Graphics gOffScreen = offScreenImage.getGraphics();
        gOffScreen.setColor(Color.green);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);


    }

    public void lauchFrame(){
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");
        this.addWindowListener(new WindowAdapter() {//匿名类适合用在类短小、不涉及拓展、不涉及重要的业务逻辑
            public void windowClosing(WindowEvent e) {
                System.exit(0);//表示正常退出
            }

        });
        this.setResizable(false);
        this.setBackground(Color.green);

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
    private class paintThread implements Runnable{

        @Override
        public void run() {
            while(true){
                repaint();//repaint 内部自动调用paint（先调用update 再调用paint）
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class keyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
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

}
