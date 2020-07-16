package com.summer.common.utils.concurrent;


import lombok.extern.slf4j.Slf4j;

/**
 * ShardingThreadPoolExecutor 清理线程
 *
 */
@Slf4j
public class ClearWork extends Thread {
    private ShardingThreadPoolExecutor shardingThreadPoolExecutor;

    public ClearWork(ShardingThreadPoolExecutor shardingThreadPoolExecutor) {
        this.shardingThreadPoolExecutor = shardingThreadPoolExecutor;
    }

    @Override
    public void run() {
        log.info("ShardingThreadPoolExecutor---------------start---------------------");

        try {
            while (!shardingThreadPoolExecutor.isTermination()) {// 每隔0.5s轮询一次，可以在这里输出线程池在关闭过程中的行为日志
                log.info("ShardingThreadPoolExecutor---------------ing---------------------");
                Thread.sleep(500);
            }
            log.info("ShardingThreadPoolExecutor---------------end---------------------");
        } catch (Exception e) {
            log.error("在等待main进程执行结束的过程中发生异常:", e);
        }
    }
}
