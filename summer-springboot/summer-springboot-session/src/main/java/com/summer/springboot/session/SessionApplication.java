package com.summer.springboot.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCaching  //开启缓存
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class, args);
    }

}
