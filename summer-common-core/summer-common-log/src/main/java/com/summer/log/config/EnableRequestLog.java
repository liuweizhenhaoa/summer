package com.summer.log.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FilterConfig.class)
public @interface EnableRequestLog {
    boolean flag() default true;
}
