package com.summer.dubbo.provider.config;

import com.summer.common.idgenerate.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by codeliu on 2018/1/31.
 */
@Configuration
public class CommonConfig {

    @Value("${order.id.workerId:0}")
    private long workerId;

    @Value("${order.id.datacenterId:0}")
    private long datacenterId;

    @Bean(name = "snowflakeIdWorkerBikePath")
    public SnowflakeIdWorker snowflakeIdWorkerBikePath() {
        return new SnowflakeIdWorker(workerId,datacenterId);
    }
    



}
