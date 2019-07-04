package com.summer.limit.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiterAnnotation {

    /**
     * 限流key
     * @return
     */
    String key() default "rate:limiter";

    /**
     * 限流阈值
     * @return
     */
    long limit() default 3;

    /**
     * 过期时间，单位秒
     * @return
     */
    long expire() default 30;

    /**
     * 限流策略 true:分布式限流（采用redis）  false:单体限流（采用guava）
     *
     * @return
     */
    boolean isSingle() default false;
}
