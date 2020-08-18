package com.summer.sentinel.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/limit")
@Slf4j
public class LimitController {

    AtomicInteger satomicInteger = new AtomicInteger(0);
    AtomicInteger datomicInteger = new AtomicInteger(0);


    @GetMapping("/single")
    public void singleLimitTest() {
        log.info("single---------------" + satomicInteger.incrementAndGet());
    }


    @GetMapping("/distributed")
    public void distributedLimitTest() {
        log.info("distributed---------------" + datomicInteger.incrementAndGet());
    }
}
