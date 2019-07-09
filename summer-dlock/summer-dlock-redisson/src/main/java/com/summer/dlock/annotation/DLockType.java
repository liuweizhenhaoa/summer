package com.summer.dlock.annotation;

public enum DLockType {
    /**
     * 基于redisson的分布式锁
     */
    REDISSON,
    /**
     * 基于zk的分布式锁
     */
    ZOOKEEPER
}
