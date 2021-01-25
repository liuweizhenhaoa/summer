
package com.summer.common.utils.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;


@Slf4j
public class Worker implements Runnable {

    private LinkedBlockingQueue<Runnable> queue;

    // 任务是否在执行中
    private boolean doing = false;

    /**
     * 使用有正在执行的任务
     * 
     * @return
     */
    public boolean isDoing() {
        return doing;
    }

    /**
     * 队列是否为空
     * 
     * @return
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * 队列的大小
     * 
     * @return
     */
    public int size() {
        return queue.size();
    }

    public Runnable take() throws InterruptedException {
        return queue.take();
    }

    public void put(Runnable task) throws InterruptedException {
        queue.put(task);
    }

    public Worker(int size) {
        this.queue = new LinkedBlockingQueue<>(size);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runnable task = take();
                // 正在执行任务
                doing = true;
                if (log.isDebugEnabled()) {
                    Thread thread = Thread.currentThread();
                    log.debug("----------thead:[{}] State:[{}]  doing[{}]  size:[{}]", thread.getName(),
                            thread.getState(),
                            doing,
                            size());
                }
                task.run();
            } catch (InterruptedException e) {
                log.error("ERROR:", e);
                Thread.currentThread().interrupt();
            } finally {
                // 执行任务完成
                doing = false;
            }

        }
    }
}
