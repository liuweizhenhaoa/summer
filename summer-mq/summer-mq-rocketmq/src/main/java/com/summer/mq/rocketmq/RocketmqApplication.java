package com.summer.mq.rocketmq;

import com.summer.mq.crawler4j.Crawler4jApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(Crawler4jApplication.class, args);
    }

}
