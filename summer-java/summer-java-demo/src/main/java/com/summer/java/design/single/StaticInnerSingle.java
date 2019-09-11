package com.summer.java.design.single;

/**
 * 静态内部类
 */
public class StaticInnerSingle {

    private static class InnerSingle{
        private static final StaticInnerSingle staticInnerSingle = new StaticInnerSingle();
    }

    private StaticInnerSingle(){}

    public static final StaticInnerSingle getInstance() {
        return InnerSingle.staticInnerSingle;
    }
}
