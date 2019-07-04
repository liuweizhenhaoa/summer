package com.summer.limit.controller;

import com.summer.common.limit.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/limit")
@Slf4j
public class LimitController {

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @RateLimiter
    @GetMapping("/test")
    public void limitTest(){
        log.info("---------------"+atomicInteger.incrementAndGet());
    }
}
