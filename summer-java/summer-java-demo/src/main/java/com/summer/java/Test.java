package com.summer.java;
import	java.util.concurrent.locks.ReentrantLock;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

//        String[] D = {"i", "like", "ali", "liba", "baba", "alibaba"};
//
//        Test test = new Test();
//        test.toStr("ilikealibaba", D);
//
//
////        boolean a=(boolean)1;
//        boolean b=(false&&true);
////        float y=22.3;
//        int x=(25|125);

//        ArrayList
        LinkedHashMap map = new LinkedHashMap();
//        map.put()
//StringBuffer
//        byte a1=2,a2=4,a3;
//        short s=16;
////        a2=s;
//        a3=a1*a2;
        Selector selector = new Selector() {
            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public SelectorProvider provider() {
                return null;
            }

            @Override
            public Set<SelectionKey> keys() {
                return null;
            }

            @Override
            public Set<SelectionKey> selectedKeys() {
                return null;
            }

            @Override
            public int selectNow() throws IOException {
                return 0;
            }

            @Override
            public int select(long timeout) throws IOException {
                return 0;
            }

            @Override
            public int select() throws IOException {
                return 0;
            }

            @Override
            public Selector wakeup() {
                return null;
            }

            @Override
            public void close() throws IOException {

            }
        };

//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("foo");
//            }
//        };

        Thread t = new Thread(r);
        r.run();
        System.out.println("bar");

    }


    public void toStr(String S, String[] D) {

//        char[] chars = S.toCharArray();

        for (int i = 0; i < S.length(); i++) {

            String out = "";

            for (int j = 1; j < S.length() - i-1; j++) {
                String a1 = S.substring(i, j + 1);
                if (contains(out, D)) {
                    out = a1;
                    continue;
                }else{
                    break;
                }
            }

            if (out != "") {
                System.out.println("---" + out);
            }

        }

    }

    public boolean contains(String s, String[] D) {
        for (String str : D) {
            if (str.equals(s)) {

//                System.out.println("----"+str);
                return true;
            }
        }
        return false;
    }
}
