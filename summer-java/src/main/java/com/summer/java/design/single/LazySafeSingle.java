package com.summer.java.design.single;

/**
 * 懒汉
 * 线程安全
 */
public class LazySafeSingle {

    private static LazySafeSingle singleton;

    private LazySafeSingle(){

    }

    public static synchronized LazySafeSingle getInstance() {
        if (singleton == null) {
            singleton = new LazySafeSingle();
        }
        return singleton;
    }
}
