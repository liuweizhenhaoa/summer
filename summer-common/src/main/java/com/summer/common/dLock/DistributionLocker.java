package com.summer.common.dLock;

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

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(Lock lock);
}
