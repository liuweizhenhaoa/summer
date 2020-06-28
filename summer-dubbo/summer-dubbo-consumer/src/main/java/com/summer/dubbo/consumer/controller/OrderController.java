package com.summer.dubbo.consumer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.summer.dubbo.service.DemoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderController {
    @Autowired
    DemoService demoService;

    @GetMapping("/test")
    public String addIncome1() {
        log.info("-------------------------consumer:" + System.currentTimeMillis());
        demoService.sayHello("test");
        return "good";
    }
}
