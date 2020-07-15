package com.summer.springboot.easyexcel.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelloBoyService {

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
