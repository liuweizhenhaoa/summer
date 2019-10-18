package com.summer.limit.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiterAnnotation {

    /**
     * limit key
     *
     * @return
     */
    String key() default "rate:limiter";

    /**
     *
     *
     * @return
     */
    long limit() default 3;

    /**
     * expire， sencond
     *
     * @return
     */
    long expire() default 30;

    /**
     * Current limiting strategy
     * true:distributed limit（redis）
     * false:single limit（guava）
     *
     * @return
     */
    boolean isSingle() default false;
}
