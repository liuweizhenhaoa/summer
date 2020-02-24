package com.summer.java.ali;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    //1.实现两个线程，使之交替打印1-100,
    //如：两个线程分别为：Printer1和Printer2,
    //最后输出结果为： Printer1 — 1 Printer2 一 2 Printer1 一 3 Printer2 一 4

    private int number =1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();


    public void print1(){

        try {
            lock.lock();
            if (number % 2 == 0){
                c1.await();
            }
            System.out.println("Printer1 — "+number);
            number++;
            c1.signal();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

    }

    public void print2(){

        try {
            lock.lock();
            if (number % 2 != 0){
                c1.await();
            }
            System.out.println("Printer2 — "+number);
            number++;
            c1.signal();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        new Thread(()->{
            for (int i = 0; i < 50; i++) {
                test1.print1();
            }

        }).start();

        new Thread(()->{
            for (int i = 0; i < 50; i++) {
                test1.print2();
            }
        }).start();
    }
}
