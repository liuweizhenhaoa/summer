package com.summer.mq.webcollector;

import com.summer.mq.nutch.NutchApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutchApplication.class, args);
    }

}
