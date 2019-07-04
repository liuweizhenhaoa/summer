package com.summer.limit.interfaces;

public interface RateLimiterInterface {

    public static final String RATE_LIMITER_PRE="RateLimiter";

    boolean limit(String key,long limit, long expire);
}
