package com.summer.mq.crawler4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Crawler4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Crawler4jApplication.class, args);
    }

}
