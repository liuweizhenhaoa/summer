package com.summer.sharding.common.util;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;

/**
 * userId生成器
 *
 * @author cluo
 * @date 2018/08/02
 * @copyright(c) gome inc Gome Co.,LTD
 */
public class IdGenerateUtil {

    private IdGenerateUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final DefaultKeyGenerator KEYGENERATOR = new DefaultKeyGenerator();
    private static Integer id = 0;

    public static synchronized Integer getUserId() {
        id++;
        return id;
    }

    public static long getSnowId() {
        return KEYGENERATOR.generateKey().longValue();
    }

}
