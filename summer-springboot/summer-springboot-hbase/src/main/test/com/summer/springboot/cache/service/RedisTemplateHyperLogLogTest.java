package com.summer.springboot.cache.service;

import com.summer.springboot.cache.CacheApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheApplication.class)
@Slf4j
public class RedisTemplateHyperLogLogTest {

    @Autowired
    RedisTemplateHyperLogLog redisTemplateHyperLogLog;

    @Test
    public void queryOrder() {
        log.info("-------start------");
        redisTemplateHyperLogLog.set();
        redisTemplateHyperLogLog.get();
        log.info("--------end-----");

    }

}
