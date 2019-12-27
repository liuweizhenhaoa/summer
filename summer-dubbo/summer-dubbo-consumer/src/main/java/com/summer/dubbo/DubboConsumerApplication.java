package com.summer.dubbo;

import com.summer.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@Slf4j
public class DubboConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboConsumerApplication.class).run(args);
    }

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private DemoService demoService;


    @Bean
    public ApplicationRunner runner() {
        return args -> log.info(demoService.sayHello("mercyblitz"));
    }

}
