package com.tankwar.client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @auther camus
 * date 2019/5/17 15:52
 */
public class TankClient extends Frame {

    int x = 50;
    int y = 50;

    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y, 30, 30);//画一个实心圆
        g.setColor(c);

        y += 1;

    }

    public void lauchFrame(){
        this.setLocation(400, 100);
        this.setSize(800, 600);
        this.setTitle("TankWar");
        this.addWindowListener(new WindowAdapter() {//匿名类适合用在类短小、不涉及拓展、不涉及重要的业务逻辑
            public void windowClosing(WindowEvent e) {
                System.exit(0);//表示正常退出
            }

        });
        this.setResizable(false);
        this.setBackground(Color.green);
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
                repaint();//repaint 内部自动调用paint
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
