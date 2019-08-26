package com.summer.java.design.single;

/**
 * 枚举单例
 */
public enum EnumSingle {
    INSTANCE;
    private Single single;

    EnumSingle(){
        single = new Single();
    }

    public Single getInstance() {
        return single;
    }
}


class Single{

}