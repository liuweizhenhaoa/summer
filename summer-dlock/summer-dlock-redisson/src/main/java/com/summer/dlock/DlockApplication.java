package com.summer.dlock;

import com.summer.dlock.annotation.DLockType;
import com.summer.dlock.annotation.EnableDistributionLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributionLock(DLockType.REDISSON)
public class DlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlockApplication.class, args);
    }

}
