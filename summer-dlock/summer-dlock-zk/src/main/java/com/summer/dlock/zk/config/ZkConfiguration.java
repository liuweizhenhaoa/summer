package com.summer.dlock.zk.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfiguration {

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(ZkProprities zkProprities) {
        return CuratorFrameworkFactory.newClient(
                zkProprities.getConnectString(),
                zkProprities.getSessionTimeoutMs(),
                zkProprities.getConnectionTimeoutMs(),
                new RetryNTimes(zkProprities.getRetryCount(), zkProprities.getElapsedTimeMs()));
    }
}
