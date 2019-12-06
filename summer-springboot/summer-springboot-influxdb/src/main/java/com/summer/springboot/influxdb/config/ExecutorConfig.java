package com.summer.springboot.influxdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorConfig.class);
    @Resource
    private ListenerConfig listenerConfig;

    @Bean
    public Executor executor() {
        LOGGER.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor(listenerConfig.getVisiblePool());
        //配置核心线程数
        executor.setCorePoolSize(listenerConfig.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(listenerConfig.getMaxPoolSize());
        //配置队列大小
        executor.setQueueCapacity(listenerConfig.getPoolQueueCapacity());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(listenerConfig.getThreadNamePrefix());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}