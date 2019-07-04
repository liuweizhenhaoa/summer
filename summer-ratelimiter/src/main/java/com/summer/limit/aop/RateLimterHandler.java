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

//    @Autowired
//    RedisTemplate redisTemplate;
//
//    DefaultRedisScript<Long> defaultRedisScript;
//
//    private ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap();
//
//    @PostConstruct
//    public void init(){
//        defaultRedisScript = new DefaultRedisScript<>();
//        defaultRedisScript.setResultType(Long.class);
//        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/rateLimter.lua")));
//        log.info("RateLimterHandler[分布式限流处理器]脚本加载完成");
//    }

    @Autowired
    RateLimiterStrategyFactory rateLimiterStrategyFactory;

    @Pointcut("@annotation(com.summer.limit.annotation.RateLimiterAnnotation)")
    public void limitPointCut() {

    }

    @Around("@annotation(rateLimiterAnnotation)")
    public Object around(ProceedingJoinPoint point, RateLimiterAnnotation rateLimiterAnnotation) throws Throwable {
        if(log.isDebugEnabled()){
            log.debug("RateLimterHandler[分布式限流处理器]开始执行限流操作");
        }

        Signature signature = point.getSignature();
        if(!(signature instanceof MethodSignature)){
            throw new IllegalArgumentException("the Annotation @RateLimter must used on method!");
        }
        if (!limitStrategy(rateLimiterAnnotation)){
            return "false";
        }
        return point.proceed();
    }


    /**
     * 限流策略
     * @param rateLimiter
     * @return
     * @throws Throwable
     */
    private boolean limitStrategy(RateLimiterAnnotation rateLimiter) throws Throwable {
        RateLimiterInterface rateLimiterInterface;
        if (rateLimiter.isSingle()) {
            rateLimiterInterface = rateLimiterStrategyFactory.getStrategy(RateLimiterInterface.RATE_LIMITER_PRE+ DistrubutedRateLimiter.RATE_LIMITER_SUF);
        }else {
            rateLimiterInterface = rateLimiterStrategyFactory.getStrategy(RateLimiterInterface.RATE_LIMITER_PRE+ SingleRateLimiter.RATE_LIMITER_SUF);
        }
        return rateLimiterInterface.limit(rateLimiter.key(),rateLimiter.limit(),rateLimiter.expire());

    }


//    /**
//     * 单体项目限流策略
//     * @param point
//     * @param rateLimiterAnnotation
//     * @return
//     * @throws Throwable
//     */
//    public Object singleLimit(ProceedingJoinPoint point, RateLimiterAnnotation rateLimiterAnnotation) throws Throwable{
//        // 每秒5000个许可
//        RateLimiter rateLimiter = rateLimiters.get(rateLimiterAnnotation.key());
//        if(rateLimiter == null){
//            rateLimiter = RateLimiter.create(rateLimiterAnnotation.limit()/rateLimiterAnnotation.expire());
//            rateLimiters.put(rateLimiterAnnotation.key(), rateLimiter);
//        }
//
//        if(!rateLimiter.tryAcquire()){
//            log.info("由于超过单位时间={}-允许的请求次数={}[触发限流]",rateLimiterAnnotation.expire(),rateLimiterAnnotation.limit());
//            return "false";
//        }
//        return point.proceed();
//    }
//
//    /**
//     * 分布式限流策略
//     * @param point
//     * @param rateLimiterAnnotation
//     * @return
//     * @throws Throwable
//     */
//    private Object distributedLimit(ProceedingJoinPoint point, RateLimiterAnnotation rateLimiterAnnotation) throws Throwable{
//        /**
//         * 获取注解参数
//         *
//         */
//        //限流模块key
//        String limitKey = rateLimiterAnnotation.key();
//        //限流阈值
//        long limitTimes = rateLimiterAnnotation.limit();
//
//        //限流超时时间
//        long expireTime = rateLimiterAnnotation.expire();
//        if(log.isDebugEnabled()){
//            log.debug("RateLimterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}",limitTimes,expireTime);
//        }
//
//        /**
//         * 执行Lua脚本
//         */
//        List<String> keyList = new ArrayList<>();
//        //设置key值为注解中的值
//        keyList.add(limitKey);
//        /**
//         * 调用脚本并执行
//         */
//        long result = (long) redisTemplate.execute(defaultRedisScript,keyList, expireTime, limitTimes);
//        if(result == 0){
//            String msg="由于超过单位时间="+expireTime+"-允许的请求次数="+limitTimes+"[触发限流]";
//            log.info(msg);
//            return "false";
//        }
//
//        log.info("RateLimterHandler[分布式限流处理器]限流执行结果-result={},请求[正常]响应", result);
////        if(log.isDebugEnabled()){
////            log.debug("RateLimterHandler[分布式限流处理器]限流执行结果-result={},请求[正常]响应", result);
////        }
//        return point.proceed();
//    }
}
