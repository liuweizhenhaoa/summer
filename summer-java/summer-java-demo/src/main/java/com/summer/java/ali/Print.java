package com.summer.java.ali;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程有序打印出0~100的所有自然数(使用线程池编码）
 *
 * @author liuwei
 */
public class Print {

    private static final int LIMIT = 100;

    private static final AtomicInteger init = new AtomicInteger(0);

    private static final int THREAD_COUNT = 3;

    static class PrintThread implements Runnable {
        int model;

        public PrintThread(int model) {
            this.model = model;
        }

        @Override
        public void run() {
            while (init.get() <= LIMIT) {
                if (init.get() % THREAD_COUNT == model && init.get() <= LIMIT) {
                    System.out.println("thread" + model + ": " + init.get());
                    init.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new PrintThread(0));
        executor.submit(new PrintThread(1));
        executor.submit(new PrintThread(2));
    }
}
