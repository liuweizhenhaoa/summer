package com.summer.springboot.influxdb;

import com.summer.log.config.EnableLogAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @see EnableLogAop
 */

@SpringBootApplication
@EnableLogAop
public class InfluxdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfluxdbApplication.class, args);
    }

}
