package com.tankwar.client;

import java.awt.*;

/**
 * @auther camus
 * date 2019/5/17 15:52
 */
public class TankClient extends Frame {

    public void lauchFrame(){
        this.setLocation(400, 300);
        this.setSize(800, 600);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.lauchFrame();
    }

}
