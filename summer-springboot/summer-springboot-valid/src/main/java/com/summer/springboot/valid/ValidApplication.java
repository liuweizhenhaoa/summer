package com.summer.springboot.valid;

import com.summer.log.config.EnableLogAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @see EnableLogAop
 */

@SpringBootApplication
@EnableLogAop
public class ValidApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidApplication.class, args);

    }

}
