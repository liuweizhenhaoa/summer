
package com.summer.common.utils.concurrent;

/**
 * 分区线程池接口
 * 
 */
public interface ShardingExecutorService {

    /**
     * 根据分区key添加任务到对应的分区
     * 
     * @param runnable
     * @param key 分区key
     * @throws InterruptedException
     */
    void submit(Runnable runnable, String key) throws InterruptedException;

    /**
     * 根据分区编号添加任务到对应的分区
     *
     * @param runnable
     * @param partition 分区
     * @throws InterruptedException
     */
    void submit(Runnable runnable, Integer partition) throws InterruptedException;

    /**
     * 线程池是否终止
     * 
     * @return
     * @throws InterruptedException
     */
    boolean isTermination() throws InterruptedException;
}
