package com.summer.limit;

import com.summer.common.limit.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Aspect
public class RateLimterHandler {

    @Autowired
    RedisTemplate redisTemplate;

    DefaultRedisScript<Long> defaultRedisScript;

    @PostConstruct
    public void init(){
        defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimter.lua")));
        log.info("RateLimterHandler[分布式限流处理器]脚本加载完成");
    }

    @Pointcut("@annotation(com.summer.common.limit.RateLimiter)")
    public void limitPointCut() {

    }

    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint point, RateLimiter rateLimiter) throws Throwable {
        if(log.isDebugEnabled()){
            log.debug("RateLimterHandler[分布式限流处理器]开始执行限流操作");
        }

        Signature signature = point.getSignature();
        if(!(signature instanceof MethodSignature)){
            throw new IllegalArgumentException("the Annotation @RateLimter must used on method!");

        }
        /**
         * 获取注解参数
         *
         */
        //限流模块key
        String limitKey = rateLimiter.key();
        //限流阈值
        long limitTimes = rateLimiter.limit();

        //限流超时时间
        long expireTime = rateLimiter.expire();
        if(log.isDebugEnabled()){
            log.debug("RateLimterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}",limitTimes,expireTime);
        }

        /**
         * 执行Lua脚本
         */
        List<String> keyList = new ArrayList<>();
        //设置key值为注解中的值
        keyList.add(limitKey);
        /**
         * 调用脚本并执行
         */
        long result = (long) redisTemplate.execute(defaultRedisScript,keyList, expireTime, limitTimes);
        if(result == 0){
            String msg="由于超过单位时间="+expireTime+"-允许的请求次数="+limitTimes+"[触发限流]";
            log.debug(msg);
            return "false";
        }

        if(log.isDebugEnabled()){
            log.debug("RateLimterHandler[分布式限流处理器]限流执行结果-result={},请求[正常]响应", result);
        }


        return point.proceed();
    }
}
