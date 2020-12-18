package com.summer.springboot.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nacos")
@Slf4j
public class OrderController {

    @NacosValue(value = "${test.value:1}", autoRefreshed = true)
    private String productId;

    @GetMapping("/get")
    public String queryOrder() {

        log.info("--------------{}",productId);
        return productId;
    }



}
