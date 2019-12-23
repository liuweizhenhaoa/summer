package com.summer.springboot.jaxrs;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.summer.log.config.EnableLogAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @see EnableLogAop
 */

@SpringBootApplication
@EnableLogAop
@EnableSwagger2Doc
public class JaxRsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaxRsApplication.class, args);
    }

}
