package com.summer.springboot.groovy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  //开启缓存
public class GroovyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroovyApplication.class, args);
    }

}
