package com.summer.common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;


public interface DistributionLocker {

    <T> T lock(String resourceName,
               Consumer<T> worker, T t) throws  Exception;

    <T> T lock(String resourceName,
               Consumer<T> worker, int lockTime,T t) throws Exception;

    Lock lock(String lockKey);

    Lock lock(String lockKey, int timeout);

    Lock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws Exception;

    void unlock(String lockKey) throws Exception;

    void unlock(Lock lock);
}
