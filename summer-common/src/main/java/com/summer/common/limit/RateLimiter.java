package com.summer.common.limit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     * @return
     */
    String key() default "rate:limiter";

    /**
     * 过期时间，单位秒
     * @return
     */
    long limit() default 10;

    /**
     * 过期时间，单位秒
     * @return
     */
    long expire() default 1;
}
