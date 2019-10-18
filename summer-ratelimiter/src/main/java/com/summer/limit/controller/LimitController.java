package com.summer.limit.controller;

import com.summer.limit.annotation.RateLimiterAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/limit")
@Slf4j
public class LimitController {

    AtomicInteger satomicInteger = new AtomicInteger(0);
    AtomicInteger datomicInteger = new AtomicInteger(0);


    @RateLimiterAnnotation(limit = 10, expire = 10, isSingle = false)
    @GetMapping("/single")
    public void singleLimitTest() {
        log.info("single---------------" + satomicInteger.incrementAndGet());
    }


    @RateLimiterAnnotation(isSingle = true)
    @GetMapping("/distributed")
    public void distributedLimitTest() {
        log.info("distributed---------------" + datomicInteger.incrementAndGet());
    }
}
