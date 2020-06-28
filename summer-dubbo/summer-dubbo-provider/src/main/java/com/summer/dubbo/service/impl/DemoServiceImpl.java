package com.summer.dubbo.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import com.summer.dubbo.service.DemoService;

import lombok.extern.slf4j.Slf4j;

@Service(version = "1.0.0")
@Slf4j
public class DemoServiceImpl implements DemoService {


    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        log.info("-------------"+name);
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
