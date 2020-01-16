package com.summer.springboot.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuwei
 */
@Service
@Slf4j
public class RedisTemplateHyperLogLog {


    @Resource
    private RedisTemplate<String, String> rt;

    public void flushDb() {
        rt.execute(new RedisCallback<Object>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public void set() {

        HyperLogLogOperations<String, String> vo = rt.opsForHyperLogLog();

        vo.add("book", "a", "b", "c");
        vo.add("bag", "a", "e", "d");
        vo.add("del", "f", "g", "h");

        for (int i = 0; i < 10000*1000; i++) {
            vo.add("book", String.valueOf(i));
            vo.add("bag", String.valueOf(i));
        }

    }

    public void get() {
        HyperLogLogOperations<String, String> vo = rt.opsForHyperLogLog();
        log.info(String.valueOf(vo.size("book")));
        log.info(String.valueOf(vo.size("bag")));
        log.info(String.valueOf(vo.size("del")));
        log.info(String.valueOf(vo.size("book", "bag", "del")));
        log.info(vo.toString());
        vo.delete("del");
        log.info(vo.toString());
        log.info(String.valueOf(vo.size("book", "bag", "del")));
        vo.union("total", "book", "bag", "del");
    }


}
