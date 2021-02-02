package com.summer.grpc.consumer.tp.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class GreeterServiceTest extends AbstractControllerTest {

    @Autowired
    private GreeterService greeterService;
    @Test
    public void testGreet() {
        greeterService.greet("aaaaaaaaaaa");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}