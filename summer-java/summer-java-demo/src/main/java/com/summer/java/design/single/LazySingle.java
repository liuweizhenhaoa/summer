package com.summer.java.design.single;

/**
 * liuwei
 * 懒汉模式
 * 线程不安全
 */
public class LazySingle {

    private static  LazySingle singleton;

    private LazySingle() {

    }

    public static LazySingle getInstance() {
        if (singleton == null) {
            singleton = new LazySingle();
        }
        return singleton;
    }



}
