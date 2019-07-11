package com.summer.dlock.zk.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author codeliu
 */
@Configuration
public class ZkConfiguration {

    @Autowired
    ZkProprities ZkProprities;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient(
                ZkProprities.getConnectString(),
                ZkProprities.getSessionTimeoutMs(),
                ZkProprities.getConnectionTimeoutMs(),
                new RetryNTimes(ZkProprities.getRetryCount(), ZkProprities.getElapsedTimeMs()));
    }
}
