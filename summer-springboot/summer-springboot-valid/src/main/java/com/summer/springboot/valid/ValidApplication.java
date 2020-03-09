package com.summer.springboot.valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 */

@SpringBootApplication
@EnableAsync
public class ValidApplication {

//    final ApplicationContext applicationContext;
//
//    @Autowired
//    HelloBoyService helloBoyService;

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ValidApplication.class, args);
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            configurableApplicationContext.stop();
//        }));
    }

}
