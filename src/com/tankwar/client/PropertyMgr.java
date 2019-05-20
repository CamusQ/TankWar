package com.tankwar.client;

import java.io.IOException;
import java.util.Properties;

/**
 * @auther camus
 * date 2019/5/20 18:07
 */
public class PropertyMgr {
    static Properties property = new Properties();

    static{
        try {
            property.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return property.getProperty(key);
    }
}
