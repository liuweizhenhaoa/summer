package com.summer.dubbo.consumer.controller;


import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.summer.dubbo.service.DemoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderController {

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:12345",timeout = 3000,retries = 3)
    private DemoService demoService;

    @GetMapping("/test")
    public String addIncome1() {
        log.info("-------------------------consumer:" + System.currentTimeMillis());
        demoService.sayHello("test");
        return "good";
    }
}
