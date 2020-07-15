/*
 * @(#) OrderThreadPoolExecutor.java 2020-07-07
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.common.utils.concurrent;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分片线程池（每个分片一个线程池,每个分片顺序消费）
 * 
 * @author liuwei08
 * @version 2020-07-07
 */
@NoArgsConstructor
public class ShardingThreadPoolExecutor implements ShardingExecutorService {

    // 分片的数量
    private int num;
    // 默认的线程名称前缀
    private static final String DEFAULT_THREAD_NAME_PRE = "ShardingThreadPool";
    //
    private Map<String, ExecutorService> shardingMap = new HashMap<>();

    /**
     * 通过routerKey获取对应的线程池ExecutorService
     * 
     * @param routerKey
     * @return
     */
    public ExecutorService getByKey(String routerKey) {
        return shardingMap.get(routerKey);
    }

    /**
     * 通过routerValue获取对应的线程池ExecutorService
     * 
     * @param routerValue
     * @return
     */
    public ExecutorService getByRouterValue(String routerValue) {
        return shardingMap.get(getRouter(routerValue));
    }

    /**
     * 通过routerValue获取routerKey
     * 
     * @param routerValue
     * @return
     */
    public String getRouter(String routerValue) {
        return String.valueOf(routerValue.hashCode() % num + 1);
    }

    /**
     * 获取线程名称前缀
     * 
     * @param poolName
     * @return
     */
    public String getPoolName(String poolName) {
        return StringUtils.isEmpty(poolName) ? DEFAULT_THREAD_NAME_PRE : poolName;
    }

    public ShardingThreadPoolExecutor(int num) {
        this.num = num;
        for (int i = 0; i < num; i++) {
            ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    new DefaultThreadFactory(DEFAULT_THREAD_NAME_PRE));
            shardingMap.put(String.valueOf(i), executorService);
        }
    }

    public ShardingThreadPoolExecutor(int num, String poolName) {
        this.num = num;
        for (int i = 0; i < num; i++) {
            ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    new DefaultThreadFactory(getPoolName(poolName)));
            shardingMap.put(String.valueOf(i), executorService);
        }
    }

    public ShardingThreadPoolExecutor(int num, int queueNum)
            throws InstantiationException, IllegalAccessException {
        for (int i = 0; i < num; i++) {
            ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(queueNum),
                    new DefaultThreadFactory(DEFAULT_THREAD_NAME_PRE));
            shardingMap.put(String.valueOf(i), executorService);
        }
    }

    public ShardingThreadPoolExecutor(int num, int queueNum, String poolName)
            throws InstantiationException, IllegalAccessException {
        for (int i = 0; i < num; i++) {
            ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(queueNum),
                    new DefaultThreadFactory(getPoolName(poolName)));
            shardingMap.put(String.valueOf(i), executorService);
        }
    }

    @Override
    public void shutdown() {
        shardingMap.values().forEach(t -> t.shutdown());
    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        for (ExecutorService executorService : shardingMap.values()) {
            if (executorService.isShutdown()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isTerminated() {
        for (ExecutorService executorService : shardingMap.values()) {
            if (executorService.isTerminated()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submitByRouterValue(Callable<T> task, String routerValue) {
        ExecutorService executorService = getByRouterValue(routerValue);
        return executorService.submit(task);
    }

    @Override
    public <T> Future<T> submitByRouterKey(Callable<T> task, String routerKey) {
        ExecutorService executorService = getByKey(routerKey);
        return executorService.submit(task);
    }

    @Override
    public Future<?> submitByRouterValue(Runnable task, String routerValue) {
        ExecutorService executorService = getByRouterValue(routerValue);
        return executorService.submit(task);
    }

    @Override
    public Future<?> submitByRouterKey(Runnable task, String routerKey) {
        ExecutorService executorService = getByKey(routerKey);
        return executorService.submit(task);
    }

    @Override
    public <T> Future<T> submitByRouterValue(Runnable task, T result, String routerValue) {
        ExecutorService executorService = getByRouterValue(routerValue);
        return executorService.submit(task, result);
    }

    @Override
    public <T> Future<T> submitByRouterKey(Runnable task, T result, String routerKey) {
        ExecutorService executorService = getByKey(routerKey);
        return executorService.submit(task, result);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends CallableEntry<T>> tasks) throws InterruptedException {

        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends CallableEntry<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends CallableEntry<T>> tasks)
            throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends CallableEntry<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ShardingThreadPoolExecutor executor1 = new ShardingThreadPoolExecutor(4);

        ShardingThreadPoolExecutor executor3 = new ShardingThreadPoolExecutor(4, "test");

        ShardingThreadPoolExecutor executor2 = new ShardingThreadPoolExecutor(4, 100);

        ShardingThreadPoolExecutor executor4 = new ShardingThreadPoolExecutor(4, 100, "executor4");

        for (int i = 0; i < 4; i++) {
            executor1.submitByRouterKey(new Runnable() {
                @Override
                public void run() {
                    System.out.println("executor1:---" + new Random(10).nextInt());
                }
            }, String.valueOf(i));
        }
        for (int i = 0; i < 4; i++) {
            executor2.submitByRouterKey(new Runnable() {
                @Override
                public void run() {
                    System.out.println("executor2:---" + new Random(10).nextInt());
                }
            }, String.valueOf(i));
        }
        for (int i = 0; i < 4; i++) {
            executor3.submitByRouterKey(new Runnable() {
                @Override
                public void run() {
                    System.out.println("executor2:---" + new Random(10).nextInt());
                }
            }, String.valueOf(i));
        }
        for (int i = 0; i < 4; i++) {
            executor4.submitByRouterKey(new Runnable() {
                @Override
                public void run() {
                    System.out.println("executor2:---" + new Random(10).nextInt());
                }
            }, String.valueOf(i));
        }

        System.out.println("--------------------");
    }

    @Override
    public void execute(Runnable command) {

    }
}
