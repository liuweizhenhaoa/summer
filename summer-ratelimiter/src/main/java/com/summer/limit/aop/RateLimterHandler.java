package com.summer.limit.aop;

import com.summer.limit.annotation.RateLimiterAnnotation;
import com.summer.limit.interfaces.RateLimiterInterface;
import com.summer.limit.strategy.DistrubutedRateLimiter;
import com.summer.limit.strategy.SingleRateLimiter;
import com.summer.limit.strategy.factory.RateLimiterStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class RateLimterHandler {

    @Autowired
    RateLimiterStrategyFactory rateLimiterStrategyFactory;

    @Pointcut("@annotation(com.summer.limit.annotation.RateLimiterAnnotation)")
    public void limitPointCut() {
        if (log.isDebugEnabled()) {
            log.debug("----------------------------------------------");
        }
    }

    @Around("@annotation(rateLimiterAnnotation)")
    public Object around(ProceedingJoinPoint point, RateLimiterAnnotation rateLimiterAnnotation) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("RateLimterHandler[分布式限流处理器]开始执行限流操作");
        }

        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("the Annotation @RateLimiterAnnotation must used on method!");
        }
        if (!limitStrategy(rateLimiterAnnotation)) {
            return "false";
        }
        return point.proceed();
    }


    /**
     * 限流策略
     *
     * @param rateLimiter
     * @return
     * @throws Throwable
     */
    private boolean limitStrategy(RateLimiterAnnotation rateLimiter) {
        RateLimiterInterface rateLimiterInterface;
        if (rateLimiter.isSingle()) {
            rateLimiterInterface = rateLimiterStrategyFactory.getStrategy(RateLimiterStrategyFactory.RATE_LIMITER_PRE + DistrubutedRateLimiter.RATE_LIMITER_SUF);
        } else {
            rateLimiterInterface = rateLimiterStrategyFactory.getStrategy(RateLimiterStrategyFactory.RATE_LIMITER_PRE + SingleRateLimiter.RATE_LIMITER_SUF);
        }
        return rateLimiterInterface.limit(rateLimiter.key(), rateLimiter.limit(), rateLimiter.expire());

    }

}
