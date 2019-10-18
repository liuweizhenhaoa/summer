package com.summer.limit.strategy.factory;

import com.summer.common.exception.BussinessException;
import com.summer.limit.interfaces.RateLimiterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterStrategyFactory {

    public static final String RATE_LIMITER_PRE = "RateLimiter";

    @Autowired
    Map<String, RateLimiterInterface> strategys = new ConcurrentHashMap<>();


    public RateLimiterInterface getStrategy(String component) {
        RateLimiterInterface strategy = strategys.get(component);
        if (strategy == null) {
            throw new BussinessException("no RateLimiterStrategy is defined");
        }
        return strategy;
    }
}
