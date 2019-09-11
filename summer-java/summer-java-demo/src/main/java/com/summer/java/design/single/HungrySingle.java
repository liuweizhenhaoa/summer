package com.summer.java.design.single;

/**
 * 饿汉
 */
public class HungrySingle {

    private static HungrySingle singleton = new HungrySingle();

    private HungrySingle(){

    }

    public static HungrySingle getInstance(){
        return singleton;
    }
}
