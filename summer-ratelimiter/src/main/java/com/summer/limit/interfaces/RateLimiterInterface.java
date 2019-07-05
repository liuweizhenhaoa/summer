package com.summer.limit.interfaces;

public interface RateLimiterInterface {


    boolean limit(String key,long limit, long expire);
}
