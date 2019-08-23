package com.summer.dlock.zk.lock;

import com.summer.dlock.zk.ZkApplication;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZkApplication.class)
public class ZookeeperDLockTest {

    @Autowired
    ZookeeperDlock distributionLocker;

    @Autowired
    CuratorFramework zkClient;

    @Test
    public void lock() throws Exception {

        InterProcessMutex lock = new InterProcessMutex(zkClient, "/my/path");

        System.out.println("----------"+distributionLocker.toString());
        System.out.println("-------"+String.valueOf(distributionLocker.tryLock("/my/path", TimeUnit.SECONDS, 5000, lock)));
        distributionLocker.unlock(lock);
        System.out.println("-------"+String.valueOf(distributionLocker.tryLock("/my/path", TimeUnit.SECONDS, 5000, lock)));

    }

    @Test
    public void lock1() {
    }
}