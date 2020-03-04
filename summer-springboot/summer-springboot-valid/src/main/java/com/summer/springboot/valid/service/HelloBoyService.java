package com.summer.springboot.valid.service;

import com.summer.springboot.valid.annotations.Boy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Slf4j
public class HelloBoyService {
    @Boy("小明")
    String name = "world";

    @PostConstruct
    public void init() {
        log.info("----------init method-----------");
    }

    public void sayHello() {
        log.info("hello, " + name);
    }

    @PreDestroy
    public void destory() {
        log.info("-----------HelloBoyService destory-------------");
    }
}
