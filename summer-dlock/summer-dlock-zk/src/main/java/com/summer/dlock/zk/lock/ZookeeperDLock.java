package com.summer.dlock.zk.lock;

import com.summer.common.lock.DistributionLocker;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

@Component
@Slf4j
public class ZookeeperDLock implements DistributionLocker {


    @Autowired
    CuratorFramework zkClient;

    @Override
    public <T> T lock(String resourceName, Consumer<T> worker, T t) throws Exception {
        return null;
    }

    @Override
    public <T> T lock(String resourceName, Consumer<T> worker, int lockTime, T t) throws Exception {
        return null;
    }

    @Override
    public Lock lock(String lockKey) {
        return null;
    }

    @Override
    public Lock lock(String lockKey, int timeout) {
        return null;
    }

    @Override
    public Lock lock(String lockKey, TimeUnit unit, int timeout) {
        return null;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(zkClient, lockKey);

        return lock.acquire(waitTime, unit);
    }

    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, InterProcessMutex lock) throws Exception {

        return lock.acquire(waitTime, unit);
    }

    public void unlock(InterProcessMutex lock) throws Exception {
        lock.release();
    }

    @Override
    public void unlock(String lockKey) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(zkClient, lockKey);
        lock.release();
    }

    @Override
    public void unlock(Lock lock) {
        lock.unlock();
    }
}
