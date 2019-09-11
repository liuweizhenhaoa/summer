package com.summer.java.design.single;
import	java.util.HashMap;

/**
 *
 */
public class DoubleCheckSingle {

    private static volatile DoubleCheckSingle single;

    private DoubleCheckSingle(){}

    public static DoubleCheckSingle getInstance(){
        if (single == null) {
            synchronized (DoubleCheckSingle.class){
                if (single == null) {
                    single = new DoubleCheckSingle();
                }
            }
        }
        return single;
    }
}
