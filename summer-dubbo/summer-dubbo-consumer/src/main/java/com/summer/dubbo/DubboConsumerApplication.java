package com.summer.dubbo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DubboConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboConsumerApplication.class).run(args);
    }

//    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:12345",timeout = 3000,retries = 3)
//    private DemoService demoService;


//    @Bean
//    public ApplicationRunner runner() {
//        return args -> log.info(demoService.sayHello("mercyblitz"));
//    }

}
