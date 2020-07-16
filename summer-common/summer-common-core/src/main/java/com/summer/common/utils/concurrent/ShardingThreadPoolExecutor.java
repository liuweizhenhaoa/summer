
package com.summer.common.utils.concurrent;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.hash.Hashing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分片线程池（每个分片一个线程池,每个分片顺序消费）
 *
 */
@Slf4j
@NoArgsConstructor
@Getter
public class ShardingThreadPoolExecutor implements ShardingExecutorService {

    /**
     * 分片的数量
     */
    private int num;
    /**
     * 每个work队列大小
     */
    private int size;
    /**
     * 线程池名称前缀
     */
    private String namePre;
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * 默认的线程名称前缀
     */
    private static final String DEFAULT_THREAD_NAME_PRE = "ShardingThreadPool-";
    private Map<Integer, Worker> shardingMap = new ConcurrentHashMap<>();
    private Map<Integer, Thread> shardingPoolMap = new ConcurrentHashMap<>();

    private final AtomicInteger nextId = new AtomicInteger();

    public String getNamePre() {
        return namePre;
    }

    /**
     * 通过routerKey获取对应的线程池ExecutorService
     * 
     * @param routerKey
     * @return
     */
    public Worker getByKey(String routerKey) {
        return shardingMap.get(getRouter(routerKey));
    }

    public Set<Integer> getPartitions() {
        return shardingMap.keySet();
    }

    /**
     * 通过routerKey获取分区id
     * 
     * @param routerKey
     * @return
     */
    public int getRouter(String routerKey) {
        return Math.abs(Hashing.crc32().hashBytes(routerKey.getBytes()).hashCode()) % num;
    }

    public ShardingThreadPoolExecutor(int num, int size) {
        this.num = num;
        this.size = size;
        this.namePre = DEFAULT_THREAD_NAME_PRE;
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker(size);
            shardingMap.put(i, worker);
            Thread thread = new Thread(worker, this.namePre + nextId.incrementAndGet());
            shardingPoolMap.put(i, thread);
            thread.start();

        }
    }

    public ShardingThreadPoolExecutor(int num, int size, String namePre) {
        this.num = num;
        this.size = size;
        this.namePre = namePre;
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker(size);
            shardingMap.put(i, worker);
            Thread thread = new Thread(worker, this.namePre + nextId.incrementAndGet());
            shardingPoolMap.put(i, thread);
            thread.start();

        }
    }

    /**
     * 添加任务到对应分区的queue
     *
     * @throws InterruptedException
     */
    @Override
    public void submit(Runnable runnable, String key) {
        Worker worker = getByKey(key);
        if (worker != null) {
            try {
                worker.put(runnable);
            } catch (InterruptedException e) {
                log.error("error:", e);
                Thread.currentThread().interrupt();
            }
        } else {
            log.error("error: key[{}] not found", key);
        }
    }

    /**
     * 添加任务到对应分区的queue
     *
     * @throws InterruptedException
     */
    @Override
    public void submit(Runnable runnable, Integer partition) throws InterruptedException {
        Worker worker = shardingMap.get(partition);
        if (worker != null) {
            worker.put(runnable);
        } else {
            log.error("error: partition[{}] not found", partition);
        }
    }

    @Override
    public boolean isTermination() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Map.Entry<Integer, Thread> entry : shardingPoolMap.entrySet()) {
                Integer sharding = entry.getKey();
                Thread thread = entry.getValue();
                if (log.isDebugEnabled()) {
                    log.debug("thead:[{}] State:[{}]  doing[{}]  size:[{}]", thread.getName(), thread.getState(),
                            shardingMap.get(sharding).isDoing(),
                            shardingMap.get(sharding).size());
                }
                // 当前线程状态为WAITING 当前在执行的任务 并且 Work的queue为空
                if (Thread.State.WAITING == thread.getState() && !shardingMap.get(sharding).isDoing()
                        && shardingMap.get(sharding).isEmpty()) {
                    continue;
                }
                return false;
            }
            return true;
        } finally {
            mainLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ShardingThreadPoolExecutor executor = new ShardingThreadPoolExecutor(4, 100);
        Runtime.getRuntime().addShutdownHook(new ClearWork(executor));

        for (int i = 0; i < 4; i++) {
            executor.submit(() -> log.info("executor1:---" + new Random(10).nextInt()), String.valueOf(i));
        }

        Thread.sleep(1000);
        log.info("--------");

    }
}
