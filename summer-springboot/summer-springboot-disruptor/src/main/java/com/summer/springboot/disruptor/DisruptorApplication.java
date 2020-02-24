package com.summer.springboot.disruptor;

import com.summer.log.config.EnableLogAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @see EnableLogAop
 */

@SpringBootApplication
@EnableLogAop
public class DisruptorApplication {


    public static void main(String[] args) {
        SpringApplication.run(DisruptorApplication.class, args);
    }

}
