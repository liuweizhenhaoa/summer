package com.summer.springboot.valid;

import com.summer.log.config.EnableLogAop;
import com.summer.springboot.valid.service.HelloBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @see EnableLogAop
 */

@SpringBootApplication
@EnableLogAop
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
