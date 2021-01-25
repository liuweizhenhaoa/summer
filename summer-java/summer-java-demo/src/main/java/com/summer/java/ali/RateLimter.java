package com.summer.java.ali;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuwei
 */
public class RateLimter {

    private static final int LIMTER = 10;

    //缓存请求次数
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
    //缓存过期时间
    private final ConcurrentHashMap<String, Long> deadLineMap = new ConcurrentHashMap<>();

    /**
     * @param key 键
     * @param expireTime 过期时间 毫秒
     * @return boolean
     */
    public synchronized boolean limter(String key, long expireTime) {
        Integer count = map.get(key);
        Long time = System.currentTimeMillis();
        if (count != null) {
            //过期,重新计数，及存放新的过期截止时间
            if (deadLineMap.get(key) < time) {
                map.put(key, 1);
                deadLineMap.put(key, time + expireTime);
            } else {
                if (count < LIMTER) {
                    map.put(key, count + 1);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            map.put(key, 1);
            deadLineMap.put(key, time + expireTime);
        }
        return true;
    }


}
  