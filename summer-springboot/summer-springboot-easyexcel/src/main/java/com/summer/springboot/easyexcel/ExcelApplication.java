package com.summer.springboot.easyexcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 */
@SpringBootApplication
@EnableAsync
public class ExcelApplication {



    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ExcelApplication.class, args);
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            configurableApplicationContext.stop();
//        }));
    }

}
