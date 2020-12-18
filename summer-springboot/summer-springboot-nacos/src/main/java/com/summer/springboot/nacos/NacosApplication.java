package com.summer.springboot.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
//@NacosConfigurationProperties(dataId = "${spring.application.name}-${spring.profiles.active}.yaml",
//        autoRefreshed = true)
public class NacosApplication {

    public static void main(String[] args) {
         SpringApplication.run(NacosApplication.class, args);
    }

}
