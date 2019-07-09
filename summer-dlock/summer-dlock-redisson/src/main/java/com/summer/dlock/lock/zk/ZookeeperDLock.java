package com.summer.dlock.lock.zk;

import com.summer.common.dLock.DistributionLocker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

public class ZookeeperDLock implements DistributionLocker {
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
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return false;
    }

    @Override
    public void unlock(String lockKey) {

    }

    @Override
    public void unlock(Lock lock) {

    }
}
