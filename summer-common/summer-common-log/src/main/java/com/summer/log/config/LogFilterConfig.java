package com.summer.log.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Filter配置
 *
 */
@Configuration
public class LogFilterConfig {

    @Bean
    public FilterRegistrationBean loggerMDCFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoggerMDCFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public LogAspect getLogAspect() {

        return new LogAspect();
    }
}
