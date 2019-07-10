package com.summer.dlock.lock.redisson;

import com.summer.dlock.RedissonApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={RedissonApplication.class})
public class RedissonDLockTest {

    @Test
    public void lock() {
    }
}