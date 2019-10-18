package com.summer.limit.strategy;

import com.summer.limit.interfaces.RateLimiterInterface;
import com.summer.limit.strategy.factory.RateLimiterStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component(RateLimiterStrategyFactory.RATE_LIMITER_PRE + DistrubutedRateLimiter.RATE_LIMITER_SUF)
@Slf4j
public class DistrubutedRateLimiter implements RateLimiterInterface {

    public static final String RATE_LIMITER_SUF = "Distrubuted";


    @Autowired
    RedisTemplate redisTemplate;

    DefaultRedisScript<Long> defaultRedisScript;

    @PostConstruct
    public void init() {
        defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/rateLimter.lua")));
        log.info("RateLimterHandler[分布式限流处理器]脚本加载完成");
    }


    @Override
    public boolean limit(String key, long limit, long expire) {

        if (log.isDebugEnabled()) {
            log.debug("RateLimterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}", limit, expire);
        }

        /**
         * 执行Lua脚本
         */
        List<String> keyList = new ArrayList<>();
        //设置key值为注解中的值
        keyList.add(key);
        /**
         * 调用脚本并执行
         */
        long result = (long) redisTemplate.execute(defaultRedisScript, keyList, expire, limit);
        if (result == 0) {
            String msg = "由于超过单位时间=" + expire + "-允许的请求次数=" + limit + "[触发限流]";
            log.info(msg);
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug("RateLimterHandler[分布式限流处理器]限流执行结果-result={},请求[正常]响应", result);
        }
        return true;
    }
}
