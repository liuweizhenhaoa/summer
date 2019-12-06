package com.summer.springboot.influxdb.config;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "listener")
@Data
@ToString
@Slf4j
public class ListenerConfig {
    /**
     * 配置线程池大小
     */
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer poolQueueCapacity;
    private String threadNamePrefix;
    private Boolean visiblePool;
    /**
     * 配置kafka回调监听器
     */
    private Boolean visibleLog;
    private Integer listenerQueueSize;

}