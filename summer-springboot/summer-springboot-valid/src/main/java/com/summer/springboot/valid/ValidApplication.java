package com.summer.springboot.valid;

import com.summer.log.config.EnableRequestLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRequestLog
public class ValidApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidApplication.class, args);
    }

}
