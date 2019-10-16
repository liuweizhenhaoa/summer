package com.summer.dlock.lock.redisson;

import com.summer.mqtt.moquette.MoquetteApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={MoquetteApplication.class})
public class RedissonDLockTest {

    @Test
    public void lock() {
    }
}