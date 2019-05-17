package com.tankwar.client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @auther camus
 * date 2019/5/17 15:52
 */
public class TankClient extends Frame {

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
        this.setVisible(true);
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.lauchFrame();
    }

}
