package com.summer.java;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        A a = new A();
        try {
            atomicInteger.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a.start();
    }

    static class A extends Thread {
        @Override
        public void run() {
            atomicInteger.notify();
            atomicInteger.lazySet(1);
            System.out.println(atomicInteger.get());
        }
    }
}