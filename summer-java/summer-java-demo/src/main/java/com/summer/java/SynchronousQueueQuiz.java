package com.summer.java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueQuiz {
    public static void main(String[] args) throws Exception {

        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        System.out.print(queue.offer(1) + " ");
        System.out.print(queue.offer(2) + " ");
        System.out.print(queue.offer(3) + " ");
        System.out.print(queue.take() + " ");
        System.out.println(queue.size());
    }
}